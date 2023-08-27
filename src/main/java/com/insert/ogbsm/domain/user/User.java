package com.insert.ogbsm.domain.user;

import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.role.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@MappedSuperclass
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
    private String profile_image;

    @Enumerated(EnumType.STRING)
    @Column()
    private Authority authority;

    @Enumerated(EnumType.STRING)
    @Column()
    private Role role;

}
