package az.et.orderservice.service.functional;

import az.et.orderservice.constant.ErrorEnum;
import az.et.orderservice.exception.BaseException;
import az.et.orderservice.model.UserEntity;
import az.et.orderservice.repository.UserRepository;
import az.et.orderservice.security.JwtUserDetailsUtil;
import az.et.orderservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserEntity save(UserEntity courier) {
        return userRepository.save(courier);
    }

    @Override
    public UserEntity findCurrentUserFromContextOrThrEx() {
        return userRepository.findById(JwtUserDetailsUtil.getCustomerId())
                .orElseThrow(() -> BaseException.of(ErrorEnum.USER_NOT_FOUND));
    }
}
