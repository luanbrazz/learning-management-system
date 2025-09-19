package com.lbraz.lms.service.impl;

import com.lbraz.lms.dto.StudentRegistrationDTO;
import com.lbraz.lms.entity.Student;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.InvalidAgeException;
import com.lbraz.lms.repository.StudentRepository;
import com.lbraz.lms.service.StudentService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, UUID> implements StudentService {

    private final StudentRepository repository;
    private final MessageSource messageSource;

    public StudentServiceImpl(StudentRepository repository, MessageSource messageSource) {
        super(repository);
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public void registerStudent(StudentRegistrationDTO studentDTO) {
        if (repository.findByEmail(studentDTO.email()).isPresent()) {
            throw new DuplicateResourceException(messageSource.getMessage("error.email.duplicate", null, null));
        }

        if (calculateAge(studentDTO.birthDate()) < 16) {
            throw new InvalidAgeException(messageSource.getMessage("error.age.invalid", null, null));
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