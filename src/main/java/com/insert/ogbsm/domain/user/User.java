package com.insert.ogbsm.domain.user;

import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true,nullable = false)
    private String email;

    @Column()
    private String name;

    @Column()
    @Min(2021)
    private Long enroll;

    @Column()
    @Min(1)
    @Max(3)
    private Long grade;

    @Column()
    @Min(1)
    @Max(4)
    private Long class_number;

    @Column()
    @Min(1)
    @Max(16)
    private Long student_number;

    @Column()
    private String profile_image;

    @Enumerated(EnumType.STRING)
    @Column()
    private Authority authority;

    @Enumerated(EnumType.STRING)
    @Column()
    private Role role;



}
