package com.example.email_validation.auth;

import com.example.email_validation.email.EmailService;
import com.example.email_validation.email.EmailTemplate;
import com.example.email_validation.role.RoleRepository;
import com.example.email_validation.user.Token;
import com.example.email_validation.user.TokenRepository;
import com.example.email_validation.user.User;
import com.example.email_validation.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequestDto request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        var user = User.builder()
                .firstname(request.firstName()).lastname(request.lastName())
                .email(request.email()).password(passwordEncoder.encode(request.password()))
                .accountLocked(false).enabled(false)
                .roles(List.of(userRole)).build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    public void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation");

    }

    private String generateAndSaveActivationToken(User user){
        String generatedToken = generateActivationCode(6);

        var token = Token.builder()
                .token(generatedToken)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }


}
