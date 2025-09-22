package com.lbraz.lms.service.impl;

import com.lbraz.lms.dto.ViaCepResponse;
import com.lbraz.lms.exception.ViaCepException;
import com.lbraz.lms.service.ViaCepService;
import com.lbraz.lms.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ViaCepServiceImpl implements ViaCepService {

    private static final String URL_TEMPLATE = "https://viacep.com.br/ws/%s/json/";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ViaCepResponse find(String cep) {
        try {
            String url = String.format(URL_TEMPLATE, cep);
            ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);

            if (response == null || Boolean.TRUE.equals(response.erro())) {
                throw new ViaCepException(MessageUtil.get("error.viacep.notFound", cep));
            }

            return response;
        } catch (RestClientException e) {
            throw new ViaCepException(MessageUtil.get("error.viacep.unavailable"));
        }
    }
}
