package com.lbraz.lms.service.impl;

import com.lbraz.lms.dto.StudentRegistrationRequest;
import com.lbraz.lms.entity.Student;
import com.lbraz.lms.entity.User;
import com.lbraz.lms.enums.Role;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.DuplicateUsernameException;
import com.lbraz.lms.exception.InvalidAgeException;
import com.lbraz.lms.repository.StudentRepository;
import com.lbraz.lms.repository.UserRepository;
import com.lbraz.lms.service.StudentService;
import com.lbraz.lms.util.MessageUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, UUID> implements StudentService {

    private static final int MINIMUM_AGE = 16;

    private final StudentRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository repository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.repository = repository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Student save(Student entity) {
        validateStudent(entity.getEmail(), entity.getBirthDate(), entity.getUser().getUsername());
        return repository.save(entity);
    }

    @Override
    @Transactional
    public Student register(StudentRegistrationRequest request) {
        validateStudent(request.email(), request.birthDate(), request.email());

        User user = User.builder()
                .username(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_STUDENT)
                .build();

        Student student = Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthDate(request.birthDate())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .zipCode(request.zipCode())
                .addressLine1(request.addressLine1())
                .addressLine2(request.addressLine2())
                .city(request.city())
                .state(request.state())
                .user(user)
                .build();

        user.setStudent(student);
        return repository.save(student);
    }

    private void validateStudent(String email, LocalDate birthDate, String username) {
        this.validateMinimumAge(birthDate);
        this.validateEmailNotTaken(email);
        this.validateUsernameNotTaken(username);
    }

    private void validateMinimumAge(LocalDate birthDate) {
        int age = this.calculateAge(birthDate);
        if (age < MINIMUM_AGE) {
            throw new InvalidAgeException(MessageUtil.get("error.age.invalid"));
        }
    }

    private void validateEmailNotTaken(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new DuplicateResourceException(MessageUtil.get("error.email.duplicate"));
        }
    }

    private void validateUsernameNotTaken(String username) {
        if (username == null) {
            throw new IllegalArgumentException(MessageUtil.get("validation.username.notBlank"));
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicateUsernameException(MessageUtil.get("error.username.duplicate"));
        }
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}