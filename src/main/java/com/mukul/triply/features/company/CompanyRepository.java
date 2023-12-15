package com.mukul.triply.features.company;

import com.mukul.triply.common.baseclasses.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends BaseRepository<CompanyEntity> {
    Optional<CompanyEntity> findByName(final String name);
}
