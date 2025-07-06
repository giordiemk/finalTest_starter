package com.giordanobrunomichela.final_test.service;

import com.giordanobrunomichela.final_test.dto.LoginDTO;
import com.giordanobrunomichela.final_test.dto.RegisterDTO;
import com.giordanobrunomichela.final_test.dto.UpdatePasswordDTO;

public interface AuthService {
    String login(LoginDTO loginDto);

    void register(RegisterDTO registerDto);

    void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO);
    boolean hasUsers();
}