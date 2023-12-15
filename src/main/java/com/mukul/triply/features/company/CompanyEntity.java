package com.mukul.triply.features.company;

import com.mukul.triply.common.baseclasses.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "companies")
@Data
public class CompanyEntity extends BaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    private String name;

    private String domain;
}
