package com.anilozmen.springbootmvcblog.service.impl;

import com.anilozmen.springbootmvcblog.entity.Role;
import com.anilozmen.springbootmvcblog.entity.User;
import com.anilozmen.springbootmvcblog.entity.dto.request.UserRequestDTO;
import com.anilozmen.springbootmvcblog.exception.UserAlreadyExistException;
import com.anilozmen.springbootmvcblog.exception.UserNotFoundException;
import com.anilozmen.springbootmvcblog.repository.UserRepository;
import com.anilozmen.springbootmvcblog.service.UserService;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User save(UserRequestDTO userRequestDTO) {

    if (existsByEmail(userRequestDTO.getEmail())) {
      throw new UserAlreadyExistException();
    }

    User user = User.builder()
        .firstName(userRequestDTO.getFirstName())
        .lastName(userRequestDTO.getLastName())
        .email(userRequestDTO.getEmail())
        .password(passwordEncoder.encode(userRequestDTO.getPassword()))
        .roles(List.of(Role.USER))
        .isActive(true)
        .build();

    return userRepository.save(user);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
