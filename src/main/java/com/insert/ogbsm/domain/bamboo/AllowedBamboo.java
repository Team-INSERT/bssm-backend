package com.insert.ogbsm.domain.bamboo;

import com.insert.ogbsm.domain.common.CreatedAt;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllowedBamboo extends CreatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long allowedAdminId;

    @OneToOne(fetch = FetchType.EAGER)
    private Bamboo bamboo;

}
