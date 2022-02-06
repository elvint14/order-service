package az.et.orderservice.service;

import az.et.orderservice.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<UserEntity> findUserById(Long userId);

    UserEntity save(UserEntity courier);

    UserEntity findCurrentUserFromContextOrThrEx();
}
