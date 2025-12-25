package com.react.user.service;

import com.react.common.IService;
import com.react.user.dto.UserDTO;
import com.react.user.entity.User;
import com.react.user.exception.UserNotFound;
import com.react.user.mapper.UserMapper;
import com.react.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<User, UserDTO, Long> {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(IUserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAll() {
        return List.of();
    }

    @Override
    public UserDTO getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFound("UserNotFound", "User Not Found!"));
    }

    @Override
    public UserDTO create(UserDTO dto) {
        return null;
    }

    @Override
    public UserDTO update(Long aLong, UserDTO dto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
