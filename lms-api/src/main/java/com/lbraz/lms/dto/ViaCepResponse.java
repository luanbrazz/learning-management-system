package com.lbraz.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponse(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String estado,
        String uf,
        @JsonProperty("erro") Boolean erro
) {}
