package com.lbraz.lms.service;

import com.lbraz.lms.dto.LoginRequest;
import com.lbraz.lms.dto.LoginResponse;

public interface AuthService {
    LoginResponse authenticate(LoginRequest request);
}