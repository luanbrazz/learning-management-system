package com.lbraz.lms.entity;

import com.lbraz.lms.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDateTime enrollmentDate;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private CourseStatus status = CourseStatus.NOT_STARTED;

    @Transient
    private boolean isActiveAndNearExpiration;

    @PrePersist
    public void prePersist() {
        if (this.enrollmentDate == null) {
            this.enrollmentDate = LocalDateTime.now();
        }
        if (this.expirationDate == null) {
            this.expirationDate = this.enrollmentDate.plusMonths(6);
        }
    }

    public boolean isActiveAndNearExpiration() {
        if (status == CourseStatus.COMPLETED || status == CourseStatus.EXPIRED) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthFromNow = now.plusMonths(1);
        return expirationDate.isBefore(now) || !expirationDate.isAfter(oneMonthFromNow);
    }
}