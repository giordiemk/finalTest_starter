package com.giordanobrunomichela.final_test.service.Impl;

import com.giordanobrunomichela.final_test.config.JwtTokenProvider;
import com.giordanobrunomichela.final_test.dto.LoginDTO;
import com.giordanobrunomichela.final_test.dto.RegisterDTO;
import com.giordanobrunomichela.final_test.dto.UpdatePasswordDTO;
import com.giordanobrunomichela.final_test.model.Role;
import com.giordanobrunomichela.final_test.model.User;
import com.giordanobrunomichela.final_test.repository.RoleRepository;
import com.giordanobrunomichela.final_test.repository.UserRepository;
import com.giordanobrunomichela.final_test.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service implementation for authentication and authorization operations.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for AuthServiceImpl.
     *
     * @param authenticationManager the authentication manager
     * @param jwtTokenProvider      the JWT token provider
     * @param userRepository        the user repository
     * @param roleRepository        the role repository
     * @param passwordEncoder       the password encoder
     */
    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate a user and generate a JWT token.
     *
     * @param loginDto the login data transfer object
     * @return the generated JWT token
     */
    @Override
    public String login(LoginDTO loginDto) {

        // 01 - AuthenticationManager is used to authenticate the user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        /* 02 - SecurityContextHolder is used to allows the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 03 - Generate the token based on username and secret key

        // 04 - Return the token to controller
        return jwtTokenProvider.generateToken(authentication);
    }

    /**
     * Register a new user.
     *
     * @param registerDto the registration data transfer object
     */
    @Override
    public void register(RegisterDTO registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role;
        if (userRepository.count() == 0) {
            role = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        } else {
            role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        }
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
    }

    /**
     * Update the password of an existing user.
     *
     * @param username          the username of the user
     * @param updatePasswordDTO the update password data transfer object
     */
    @Override
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean hasUsers() {
        return userRepository.count() > 0;
    }
}
