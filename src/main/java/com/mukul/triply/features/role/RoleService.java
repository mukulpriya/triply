package com.mukul.triply.features.role;

import com.mukul.triply.common.baseclasses.BaseService;
import com.mukul.triply.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService extends BaseService<RoleEntity, RoleEntry> {

    final RoleRepository roleRepository;

    @Autowired
    public RoleService(final RoleRepository repository) {
        super();
        this.repository = repository;
        this.roleRepository = repository;
        this.mapper = RoleMapper.INSTANCE;
    }

    public RoleEntry readByName(final String name) throws NotFoundException {
        final Optional<RoleEntity> optionalRole = roleRepository.findByName(name);
        return optionalRole.map(this.mapper::toEntry).orElseThrow(() -> new NotFoundException("Role not found."));
    }
}
