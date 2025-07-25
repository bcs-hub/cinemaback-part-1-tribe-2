package ee.bcs.cinemaback.controller.user;

import ee.bcs.cinemaback.service.user.UserService;
import ee.bcs.cinemaback.service.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/admin/v1/user")
@RestController
@Validated
public class UserAdminController {

    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/summary")
    @Operation(summary = "Returns all users",
            description = "If there are no users, returns an empty array.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
