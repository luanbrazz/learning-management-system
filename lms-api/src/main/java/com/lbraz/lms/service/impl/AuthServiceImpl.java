package com.lbraz.lms.service.impl;

import com.lbraz.lms.dto.LoginRequest;
import com.lbraz.lms.dto.LoginResponse;
import com.lbraz.lms.entity.User;
import com.lbraz.lms.enums.Role;
import com.lbraz.lms.exception.InvalidCredentialsException;
import com.lbraz.lms.repository.UserRepository;
import com.lbraz.lms.security.JwtService;
import com.lbraz.lms.service.AuthService;
import com.lbraz.lms.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException(MessageUtil.get("error.credentials.invalid"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String jwtToken = jwtService.generateToken(userDetails);

        String userRole = userDetails.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse(null);

            User user = userRepository.findByUsernameWithStudent(request.username())
                .orElseThrow(() -> new InvalidCredentialsException(MessageUtil.get("error.user.notFound")));

        String fullName;
        UUID studentId = null;

        if (!Role.ROLE_ADMIN.getName().equals(userRole) && user.getStudent() != null) {
            fullName = user.getStudent().getFirstName() + " " + user.getStudent().getLastName();
            studentId = user.getStudent().getId();
        } else {
            fullName = MessageUtil.get("user.administrator");
        }

        return new LoginResponse(
                jwtToken,
                userRole,
                fullName,
                user.getId(),
                studentId
        );
    }
}