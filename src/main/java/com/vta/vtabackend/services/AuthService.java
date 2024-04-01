package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.UserDetails;
import com.vta.vtabackend.documents.UserVerificationCode;
import com.vta.vtabackend.dto.AuthResponse;
import com.vta.vtabackend.dto.LoginWithEmailRequest;
import com.vta.vtabackend.dto.OTPRequest;
import com.vta.vtabackend.dto.RegisterWithEmailRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.ApiException;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.repositories.UserVerificationCodesRepository;
import com.vta.vtabackend.utils.JWTUtil;
import com.vta.vtabackend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final UserVerificationCodesRepository userVerificationCodesRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public String registerWithEmail(RegisterWithEmailRequest request) throws CustomException {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (exists) {
            throw new CustomException("Email already exists: " + request.getEmail());
        } else {
            UserVerificationCode verificationCode = saveUserDetails(request.getEmail(), null, request.getName(), request.getPassword());

            mailService.sendOtp(verificationCode.getCode(), request.getEmail());

            return "Verification code sent to your email";
        }
    }

    private UserVerificationCode saveUserDetails(String email, String mobile, String name, String password) {
        UserDetails user = UserDetails.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .mobile(mobile)
                .role(Role.USER)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);

        UserVerificationCode verificationCode = generateCode(email, mobile);

        return userVerificationCodesRepository.save(verificationCode);
    }

    private UserVerificationCode generateCode (String email, String mobile) {
        return new UserVerificationCode(UUID.randomUUID().toString(),
                StringUtils.getRandomNumbers(6), email != null ? email : mobile,
                System.currentTimeMillis() + (5 * 60 * 1000));
    }
    public AuthResponse verifyOtp(UserDetails user, String source, String otp) throws ApiException {

        UserVerificationCode code = userVerificationCodesRepository.findByCode(otp)
                .orElseThrow(() -> new CustomException("Verification code not found"));

        if (!code.isActive()) {
            throw new CustomException("Verification code is expired");
        }

        if (!code.getContactMedium().equals(source)) {
            throw new CustomException("Email does not match");
        }

        user.setVerified(true);
        userRepository.save(user);

        userVerificationCodesRepository.delete(code);

        return new AuthResponse(jwtUtil.generateToken(user));
    }

    public AuthResponse verifyEmailOtp(String email, String otp) throws ApiException {
        UserDetails user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));
        return verifyOtp(user, email, otp);
    }

    public String getVerifyCode(OTPRequest request) {
        boolean exists = userRepository.existsByEmail(request.email());
        if (exists) {
            UserVerificationCode code = generateCode(request.email(), null);
            mailService.sendOtp(code.getCode(), request.email());
            userVerificationCodesRepository.save(code);
            return "Verification code sent to your email";

        } else {
            throw new CustomException("User not found");
        }
    }

    public AuthResponse loginWithEmail(LoginWithEmailRequest request) throws ApiException {
        UserDetails userDetails = userRepository.findByEmail(request.email()).orElseThrow(() ->
                new CustomException("User not found with email: " + request.email()));
        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new CustomException("Incorrect password for email: " + request.email());
        }
        if (!userDetails.isVerified()) {
            throw new CustomException("User not verified: " + request.email());
        }
        return new AuthResponse(jwtUtil.generateToken(userDetails));
    }

    public AuthResponse adminlogin(LoginWithEmailRequest request) throws ApiException {
        UserDetails userDetails = userRepository.findByEmail(request.email()).orElseThrow(() ->
                new CustomException("User not found with email: " + request.email()));

        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new CustomException("Incorrect password for email: " + request.email());
        }
        if(userDetails.getRole()!=Role.ADMIN) {
            throw new CustomException("Your are not a admin user");
        }
        return new AuthResponse(jwtUtil.generateToken(userDetails));
    }

}

