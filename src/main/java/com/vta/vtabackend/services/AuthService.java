package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.documents.UserVerificationCode;
import com.vta.vtabackend.dto.AuthResponse;
import com.vta.vtabackend.dto.LoginWithEmailRequest;
import com.vta.vtabackend.dto.OTPRequest;
import com.vta.vtabackend.dto.RegisterWithEmailRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.repositories.UserVerificationCodesRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import com.vta.vtabackend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final MailService mailService;
    private final UserVerificationCodesRepository userVerificationCodesRepository;
    private final TokenService tokenService;

    @Transactional
    public String registerWithEmail(RegisterWithEmailRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (exists) {
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.USER_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.USER_NOT_FOUND.getCode());
        } else {
            UserVerificationCode verificationCode = saveUserDetails(request.getEmail(), null, request.getName(), request.getPassword(), request.getRole());

            mailService.sendOtp(verificationCode.getCode(), request.getEmail());

            return "Verification code sent to your email";
        }
    }

    private UserVerificationCode saveUserDetails(String email, String mobile, String name, String password, Role
                                                 role){
        Users user = Users.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .mobile(mobile)
                .role(role)
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

    public AuthResponse verifyOtp(Users user, String source, String otp) {

        UserVerificationCode code = userVerificationCodesRepository.findByCode(otp)
                .orElseThrow(() -> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.VERIFICATION_CODE_NOT_FOUND.getMessage(),
                        ErrorStatusCodes.VERIFICATION_CODE_NOT_FOUND.getCode()));

        if (!code.isActive()) {
            throw new VTAException(VTAException.Type.VALIDATION_ERROR,
                    ErrorStatusCodes.VERIFICATION_CODE_EXPIRED.getMessage(),
                    ErrorStatusCodes.VERIFICATION_CODE_EXPIRED.getCode());
        }

        if (!code.getContactMedium().equals(source)) {
            throw new VTAException(VTAException.Type.EXTERNAL_SYSTEM_ERROR,
                    ErrorStatusCodes.EMAIL_DOES_NOT_MATCH.getMessage(),
                    ErrorStatusCodes.EMAIL_DOES_NOT_MATCH.getCode());
        }

        user.setVerified(true);
        userRepository.save(user);

        userVerificationCodesRepository.delete(code);

        return new AuthResponse(tokenService.generateToken(user), user.getRole().toString());
    }

    public AuthResponse verifyEmailOtp(String email, String otp) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.USER_NOT_FOUND.getMessage(),
                        ErrorStatusCodes.USER_NOT_FOUND.getCode()));
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
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.USER_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.USER_NOT_FOUND.getCode());
        }
    }

    public AuthResponse loginWithEmail(LoginWithEmailRequest request) {
        Users users = userRepository.findByEmail(request.email()).orElseThrow(() ->
                new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.USER_NOT_FOUND.getMessage(),
                        ErrorStatusCodes.USER_NOT_FOUND.getCode()));

        if (!passwordEncoder.matches(request.password(), users.getPassword())) {
            throw new VTAException(VTAException.Type.VALIDATION_ERROR,
                    ErrorStatusCodes.INCORRECT_PASSWORD.getMessage(),
                    ErrorStatusCodes.INCORRECT_PASSWORD.getCode());
        }
        if (!users.isVerified()) {
            throw new VTAException(VTAException.Type.VALIDATION_ERROR,
                    ErrorStatusCodes.USER_NOT_VERIFIED.getMessage(),
                    ErrorStatusCodes.USER_NOT_VERIFIED.getCode());
        }
        return new AuthResponse(tokenService.generateToken(users), users.getRole().toString());
    }

    public AuthResponse adminlogin(LoginWithEmailRequest request) {
        Users users = userRepository.findByEmail(request.email()).orElseThrow(() ->
                new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.USER_NOT_FOUND.getMessage(),
                        ErrorStatusCodes.USER_NOT_FOUND.getCode()));

        if (!passwordEncoder.matches(request.password(), users.getPassword())) {
            throw new VTAException(VTAException.Type.VALIDATION_ERROR,
                    ErrorStatusCodes.INCORRECT_PASSWORD.getMessage(),
                    ErrorStatusCodes.INCORRECT_PASSWORD.getCode());
        }

        if(users.getRole()!=Role.ADMIN) {
            throw new VTAException(VTAException.Type.VALIDATION_ERROR,
                    ErrorStatusCodes.YOU_ARE_NOT_ADMIN.getMessage(),
                    ErrorStatusCodes.YOU_ARE_NOT_ADMIN.getCode());
        }
        return new AuthResponse(tokenService.generateToken(users), users.getRole().toString());
    }

    public String getCount() {
        List<Users> users = userRepository.findAll();
        long count = users.stream().filter(user -> user.getRole() == Role.USER).count();
        return Long.toString(count);
    }

    public Users loadAccountByEmail(String userEmail) {
        return userRepository.getByEmail(userEmail);
    }
}

