package com.lbraz.lms.service;

import com.lbraz.lms.dto.ViaCepResponse;

public interface ViaCepService {
    ViaCepResponse find(String cep);
}
