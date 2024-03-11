package com.vta.app.services;

import com.cloudimpl.error.core.ErrorBuilder;
import com.vta.app.documents.UserDetails;
import com.vta.app.documents.UserVerificationCode;
import com.vta.app.dto.AuthResponse;
import com.vta.app.dto.GenericResponse;
import com.vta.app.dto.LoginWithEmailRequest;
import com.vta.app.dto.RegisterWithEmailRequest;
import com.vta.app.enums.Role;
import com.vta.app.error.ApiException;
import com.vta.app.repositories.UserRepository;
import com.vta.app.repositories.UserVerificationCodesRepository;
import com.vta.app.utils.JWTUtil;
import com.vta.app.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
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
    public Mono<GenericResponse> registerWithEmail(RegisterWithEmailRequest request) {
        return userRepository.existsByEmail(request.getEmail())
                .filter(e -> !e)
                .switchIfEmpty(Mono.error(() -> ApiException.EMAIL_ALREADY_EXIST(err -> err.setEmail(request.getEmail()))))
                .flatMap(e -> saveUserDetails(request.getEmail(), null, request.getName(), request.getPassword()))
                .doOnNext(verificationCode -> mailService.sendOtp(verificationCode.getCode(), request.getEmail()))
                .map(r -> GenericResponse.SUCCESS());
    }

    private Mono<UserVerificationCode> saveUserDetails(String email, String mobile, String name, String password) {
        UserDetails user = UserDetails.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .mobile(mobile)
                .role(Role.USER)
                .password(passwordEncoder.encode(password))
                .build();
        UserVerificationCode verificationCode = new UserVerificationCode(UUID.randomUUID().toString(),
                StringUtils.getRandomNumbers(6), email != null ? email : mobile,
                System.currentTimeMillis() + (5 * 60 * 1000));
        return userRepository.save(user)
                .flatMap(userDetails -> userVerificationCodesRepository.save(verificationCode));
    }

    public Mono<AuthResponse> verifyOtp(UserDetails user, String source, String otp) {
        return userVerificationCodesRepository.findByContactMedium(source)
                .switchIfEmpty(Mono.error(() -> ApiException.VERIFICATION_CODE_NOT_FOUND(ErrorBuilder::build)))
                .filter(UserVerificationCode::isActive)
                .switchIfEmpty(Mono.error(() -> ApiException.VERIFICATION_CODE_EXPIRED(ErrorBuilder::build)))
                .filter(code -> code.getCode().equals(otp))
                .switchIfEmpty(Mono.error(() -> ApiException.VERIFICATION_CODE_DOES_NOT_MATCH(ErrorBuilder::build)))
                .flatMap(code -> userVerificationCodesRepository.delete(code).thenReturn(user))
                .doOnNext(userDetails -> userDetails.setVerified(true))
                .flatMap(userRepository::save)
                .map(r -> new AuthResponse(jwtUtil.generateToken(user)));
    }


    public Mono<AuthResponse> verifyEmailOtp(String email, String otp) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(() -> ApiException.USER_NOT_FOUND(err -> err.setUsername(email))))
                .flatMap(user -> verifyOtp(user, email, otp));
    }

    public Mono<AuthResponse> loginWithEmail(LoginWithEmailRequest request) {
        return userRepository.findByEmail(request.email())
                .filter(userDetails -> passwordEncoder.encode(request.password()).equals(userDetails.getPassword()))
                .switchIfEmpty(Mono.error(() -> ApiException.USER_NOT_FOUND(err -> err.setUsername(request.email()))))
                .filter(UserDetails::isVerified)
                .switchIfEmpty(Mono.error(() -> ApiException.USER_NOT_VERIFIED(err -> err.setUsername(request.email()))))
                .map(userDetails -> new AuthResponse(jwtUtil.generateToken(userDetails)));
    }

    public Mono<AuthResponse> loginAdmin(LoginWithEmailRequest request) {
        return userRepository.findByEmail(request.email())
                .filter(user -> user.getRole().equals(Role.ADMIN))
                .switchIfEmpty(Mono.error(() -> ApiException.USER_NOT_FOUND(err -> err.setUsername(request.email()))))
                .map(jwtUtil::generateToken)
                .map(AuthResponse::new);
    }
}
