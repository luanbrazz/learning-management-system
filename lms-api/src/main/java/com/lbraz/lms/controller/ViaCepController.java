package com.lbraz.lms.controller;

import com.lbraz.lms.dto.ViaCepResponse;
import com.lbraz.lms.service.ViaCepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/via-cep")
public class ViaCepController {

    private final ViaCepService viaCepService;

    public ViaCepController(ViaCepService cepService) {
        this.viaCepService = cepService;
    }

    @GetMapping
    public ResponseEntity<ViaCepResponse> completeCep(@RequestParam("cep") String cep) {
        return ResponseEntity.ok(viaCepService.find(cep));
    }
}
