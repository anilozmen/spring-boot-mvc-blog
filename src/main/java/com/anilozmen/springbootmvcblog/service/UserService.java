package com.anilozmen.springbootmvcblog.service;

import com.anilozmen.springbootmvcblog.entity.User;
import com.anilozmen.springbootmvcblog.entity.dto.request.UserRequestDTO;

public interface UserService {

  User save(UserRequestDTO userRequestDTO);

  User findByEmail(String email);

  boolean existsByEmail(String email);

}
