package com.lbraz.lms.service.impl;

import com.lbraz.lms.entity.Student;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.InvalidAgeException;
import com.lbraz.lms.repository.StudentRepository;
import com.lbraz.lms.service.StudentService;
import com.lbraz.lms.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, UUID> implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional
    public Student save(Student entity) {
        if (calculateAge(entity.getBirthDate()) < 16) {
            throw new InvalidAgeException(MessageUtil.get("error.age.invalid"));
        }
        if (repository.findByEmail(entity.getEmail()).isPresent()) {
            throw new DuplicateResourceException(MessageUtil.get("error.email.duplicate"));
        }

        return repository.save(entity);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}