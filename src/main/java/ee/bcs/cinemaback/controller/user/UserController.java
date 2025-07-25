package ee.bcs.cinemaback.controller.user;

import ee.bcs.cinemaback.service.user.UserService;
import ee.bcs.cinemaback.service.user.dto.LoginRequest;
import ee.bcs.cinemaback.service.user.dto.LoginResponse;
import ee.bcs.cinemaback.service.user.dto.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/user")
@RestController
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registration. Returns userId and roleName",
            description = """
                    A new user is created in the system.
                    If the username already exists, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Username already exists")})
    public LoginResponse registration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return userService.register(registrationRequest);
    }

    @PostMapping("/login")
    @Operation(summary = "Login. Returns userId and roleName",
            description = """
                    Searches the system for a user by username and password.
                    If no match is found, an error with error code 403 (FORBIDDEN) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Invalid username or password")})
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
