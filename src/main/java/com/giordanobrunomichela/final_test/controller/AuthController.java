package com.giordanobrunomichela.final_test.controller;


import com.giordanobrunomichela.final_test.dto.AuthResponseDTO;
import com.giordanobrunomichela.final_test.dto.LoginDTO;
import com.giordanobrunomichela.final_test.dto.RegisterDTO;
import com.giordanobrunomichela.final_test.dto.UpdatePasswordDTO;
import com.giordanobrunomichela.final_test.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing authentication.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    /**
     * Login endpoint.
     *
     * @param loginDto the login data transfer object
     * @return the authentication response data transfer object (JWT Bearer token)
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {

        //01 - Receive the token from AuthService
        String token = authService.login(loginDto);

        //02 - Set the token as a response using JwtAuthResponse Dto class
        AuthResponseDTO authResponseDto = new AuthResponseDTO();
        authResponseDto.setAccessToken(token);

        //03 - Return the response to the user
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    /**
     * Register endpoint.
     *
     * @param registerDto the register data transfer object
     * @return a success message
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        authService.register(registerDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    /**
     * Update password endpoint.
     *
     * @param userDetails       the user details
     * @param updatePasswordDto the update password data transfer object
     * @return a success message
     */
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdatePasswordDTO updatePasswordDto) {
        authService.updatePassword(userDetails.getUsername(), updatePasswordDto);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }

    @GetMapping("/has-users")
    public ResponseEntity<Boolean> hasUsers() {
        boolean hasUsers = authService.hasUsers();
        return ResponseEntity.ok(hasUsers);
    }
}
