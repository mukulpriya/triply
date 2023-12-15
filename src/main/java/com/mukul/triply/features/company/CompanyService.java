package com.mukul.triply.features.company;

import com.mukul.triply.common.baseclasses.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends BaseService<CompanyEntity, CompanyEntry> {
    @Autowired
    public CompanyService(final CompanyRepository repository) {
        super();
        this.repository = repository;
        this.mapper = CompanyMapper.INSTANCE;
    }
}
