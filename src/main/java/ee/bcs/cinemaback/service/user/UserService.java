package ee.bcs.cinemaback.service.user;

import ee.bcs.cinemaback.infrastructure.exception.InvalidCredentialsException;
import ee.bcs.cinemaback.infrastructure.exception.UsernameExistsException;
import ee.bcs.cinemaback.persistence.ticket.TicketRepository;
import ee.bcs.cinemaback.persistence.user.User;
import ee.bcs.cinemaback.persistence.user.UserRepository;
import ee.bcs.cinemaback.persistence.user.role.Role;
import ee.bcs.cinemaback.persistence.user.role.RoleRepository;
import ee.bcs.cinemaback.security.jwt.JwtUtil;
import ee.bcs.cinemaback.service.user.dto.LoginRequest;
import ee.bcs.cinemaback.service.user.dto.LoginResponse;
import ee.bcs.cinemaback.service.user.dto.RegistrationRequest;
import ee.bcs.cinemaback.service.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.bcs.cinemaback.infrastructure.Error.*;
import static ee.bcs.cinemaback.infrastructure.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TicketRepository ticketRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse register(RegistrationRequest registrationRequest) {
        User user = registerAndGetUser(registrationRequest);
        return getLoginResponse(user);
    }

    User registerAndGetUser(RegistrationRequest registrationRequest) {

        if (userRepository.existsBy(registrationRequest.getUsername())) {
            throw new UsernameExistsException(USER_EXISTS.getMessage());
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new UsernameExistsException(USER_EMAIL_EXISTS.getMessage());
        }

        User user = userMapper.toUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(getDefaultRole());
        user.setStatus(ACTIVE.getLetter());
        userRepository.save(user);
        return user;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = getUser(loginRequest);
        return getLoginResponse(user);
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> users = userMapper.toUserDtoList(userRepository.findAll());
        users.forEach(userDto -> userDto.setBoughtTickets(ticketRepository.countByUserId(userDto.getId())));
        return users;
    }

    

    private Role getDefaultRole() {
        return roleRepository.findByName("CUSTOMER").orElseGet(() -> {
            Role role = new Role("CUSTOMER");
            return roleRepository.save(role);
        });
    }

    private LoginResponse getLoginResponse(User user) {
        UserDetails userDetails = new UserDetailsImpl(user);
        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        LoginResponse loginResponse = userMapper.toLoginResponse(user);
        loginResponse.setToken(token);
        loginResponse.setRefreshToken(refreshToken);

        return loginResponse;
    }

    private User getUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseGet(() -> userRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException(INVALID_CREDENTIALS.getMessage())));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS.getMessage());
        }
        return user;
    }



}
