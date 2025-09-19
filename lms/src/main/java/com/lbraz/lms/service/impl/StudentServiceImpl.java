package com.lbraz.lms.service.impl;

import com.lbraz.lms.entity.Student;
import com.lbraz.lms.entity.dto.StudentRegistrationDTO;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.InvalidAgeException;
import com.lbraz.lms.repository.StudentRepository;
import com.lbraz.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    @Transactional
    public void registerStudent(StudentRegistrationDTO studentDTO) {
        if (repository.findByEmail(studentDTO.email()).isPresent()) {
            throw new DuplicateResourceException("E-mail já cadastrado.");
        }

        if (calculateAge(studentDTO.birthDate()) < 16) {
            throw new InvalidAgeException("O estudante deve ter no mínimo 16 anos.");
        }

        Student student = Student.builder()
                .firstName(studentDTO.firstName())
                .lastName(studentDTO.lastName())
                .birthDate(studentDTO.birthDate())
                .email(studentDTO.email())
                .phoneNumber(studentDTO.phoneNumber())
                .zipCode(studentDTO.zipCode())
                .addressLine1(studentDTO.addressLine1())
                .addressLine2(studentDTO.addressLine2())
                .city(studentDTO.city())
                .state(studentDTO.state())
                .build();

        repository.save(student);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
