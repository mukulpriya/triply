package com.mukul.triply.features.user;

import com.mukul.triply.common.baseclasses.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {
    Optional<UserEntity> findByEmail(final String email);
    Optional<UserEntity> findByEmployeeId(final String employeeId);
}
