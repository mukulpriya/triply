package com.mukul.triply.features.user;

import com.mukul.triply.common.baseclasses.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends BaseService<UserEntity, UserEntry> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository repository) {
        super();
        this.repository = repository;
        this.userRepository = repository;
        this.mapper = UserMapper.INSTANCE;
    }

    final Optional<UserEntry> findByEmployeeId(final String employeeId) {
        return userRepository.findByEmployeeId(employeeId).map(this.mapper::toEntry);
    }

    final Optional<UserEntry> findByEmailAndPassword(final String email, final String password) {
        return userRepository.findByEmail(email).filter(userEntity -> password.equals(userEntity.getPassword())).map(this.mapper::toEntry);
    }
}
