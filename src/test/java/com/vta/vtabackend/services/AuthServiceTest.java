package com.vta.vtabackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vta.vtabackend.components.PBKDF2Encoder;
import com.vta.vtabackend.documents.UserDetails;
import com.vta.vtabackend.documents.UserVerificationCode;
import com.vta.vtabackend.dto.LoginWithEmailRequest;
import com.vta.vtabackend.dto.OTPRequest;
import com.vta.vtabackend.dto.RegisterWithEmailRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.ApiException;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.repositories.UserVerificationCodesRepository;
import com.vta.vtabackend.utils.JWTUtil;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthService.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockBean
    private JWTUtil jWTUtil;

    @MockBean
    private MailService mailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserVerificationCodesRepository userVerificationCodesRepository;

    @Test
    void testRegisterWithEmail() throws CustomException {

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);
        PBKDF2Encoder passwordEncoder = new PBKDF2Encoder();
        MailService mailService = new MailService(new JavaMailSenderImpl());
        UserVerificationCodesRepository userVerificationCodesRepository = mock(UserVerificationCodesRepository.class);
        AuthService authService = new AuthService(userRepository, passwordEncoder, mailService,
                userVerificationCodesRepository, new JWTUtil());

        // Act and Assert
        assertThrows(CustomException.class,
                () -> authService.registerWithEmail(new RegisterWithEmailRequest("Name", "jane.doe@example.org", "password")));
        verify(userRepository).existsByEmail(eq("jane.doe@example.org"));
    }

    @Test
    void testVerifyOtp() throws ApiException {

        // Arrange
        UserVerificationCode userVerificationCode = new UserVerificationCode();
        userVerificationCode.setCode("Code");
        userVerificationCode.setContactMedium("Contact Medium");
        userVerificationCode.setExpireTime(1L);
        userVerificationCode.setId("42");
        userVerificationCode.setUsed(true);
        Optional<UserVerificationCode> ofResult = Optional.of(userVerificationCode);
        UserVerificationCodesRepository userVerificationCodesRepository = mock(UserVerificationCodesRepository.class);
        when(userVerificationCodesRepository.findByCode(Mockito.<String>any())).thenReturn(ofResult);
        UserRepository userRepository = mock(UserRepository.class);
        PBKDF2Encoder passwordEncoder = new PBKDF2Encoder();
        MailService mailService = new MailService(new JavaMailSenderImpl());
        AuthService authService = new AuthService(userRepository, passwordEncoder, mailService,
                userVerificationCodesRepository, new JWTUtil());

        UserDetails user = new UserDetails();
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setMobile("Mobile");
        user.setName("Name");
        user.setPassword("password");
        user.setRole(Role.USER);
        user.setVerified(true);

        // Act and Assert
        assertThrows(CustomException.class, () -> authService.verifyOtp(user, "Source", "Otp"));
        verify(userVerificationCodesRepository).findByCode(eq("Otp"));
    }

    @Test
    void testVerifyEmailOtp() throws ApiException {

        // Arrange
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("jane.doe@example.org");
        userDetails.setId("42");
        userDetails.setMobile("Mobile");
        userDetails.setName("Name");
        userDetails.setPassword("password");
        userDetails.setRole(Role.USER);
        userDetails.setVerified(true);
        Optional<UserDetails> ofResult = Optional.of(userDetails);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        UserVerificationCode userVerificationCode = new UserVerificationCode();
        userVerificationCode.setCode("Code");
        userVerificationCode.setContactMedium("Contact Medium");
        userVerificationCode.setExpireTime(1L);
        userVerificationCode.setId("42");
        userVerificationCode.setUsed(true);
        Optional<UserVerificationCode> ofResult2 = Optional.of(userVerificationCode);
        UserVerificationCodesRepository userVerificationCodesRepository = mock(UserVerificationCodesRepository.class);
        when(userVerificationCodesRepository.findByCode(Mockito.<String>any())).thenReturn(ofResult2);
        PBKDF2Encoder passwordEncoder = new PBKDF2Encoder();
        MailService mailService = new MailService(new JavaMailSenderImpl());

        // Act and Assert
        assertThrows(CustomException.class, () -> (new AuthService(userRepository, passwordEncoder, mailService,
                userVerificationCodesRepository, new JWTUtil())).verifyEmailOtp("jane.doe@example.org", "Otp"));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        verify(userVerificationCodesRepository).findByCode(eq("Otp"));
    }

    @Test
    void testGetVerifyCode() {

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByEmail(Mockito.<String>any())).thenThrow(new CustomException("User not found"));
        PBKDF2Encoder passwordEncoder = new PBKDF2Encoder();
        MailService mailService = new MailService(new JavaMailSenderImpl());
        UserVerificationCodesRepository userVerificationCodesRepository = mock(UserVerificationCodesRepository.class);
        AuthService authService = new AuthService(userRepository, passwordEncoder, mailService,
                userVerificationCodesRepository, new JWTUtil());

        // Act and Assert
        assertThrows(CustomException.class, () -> authService.getVerifyCode(new OTPRequest("jane.doe@example.org")));
        verify(userRepository).existsByEmail(eq("jane.doe@example.org"));
    }

    @Test
    void testLoginWithEmail() throws ApiException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Optional<UserDetails> emptyResult = Optional.empty();
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        PBKDF2Encoder passwordEncoder = new PBKDF2Encoder();
        MailService mailService = new MailService(new JavaMailSenderImpl());
        UserVerificationCodesRepository userVerificationCodesRepository = mock(UserVerificationCodesRepository.class);
        AuthService authService = new AuthService(userRepository, passwordEncoder, mailService,
                userVerificationCodesRepository, new JWTUtil());

        // Act and Assert
        assertThrows(CustomException.class,
                () -> authService.loginWithEmail(new LoginWithEmailRequest("jane.doe@example.org", "iloveyou")));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
    }

    @Test
    void testAdminlogin() throws ApiException {
        // Arrange
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(true);

        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("jane.doe@example.org");
        userDetails.setId("42");
        userDetails.setMobile("Mobile");
        userDetails.setName("Name");
        userDetails.setPassword("iloveyou");
        userDetails.setRole(Role.USER);
        userDetails.setVerified(true);
        Optional<UserDetails> ofResult = Optional.of(userDetails);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(CustomException.class,
                () -> authService.adminlogin(new LoginWithEmailRequest("jane.doe@example.org", "iloveyou")));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        verify(passwordEncoder).matches(isA(CharSequence.class), eq("iloveyou"));
    }
}