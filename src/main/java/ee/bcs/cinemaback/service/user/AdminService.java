package ee.bcs.cinemaback.service.user;

import ee.bcs.cinemaback.persistence.user.User;
import ee.bcs.cinemaback.persistence.user.UserRepository;
import ee.bcs.cinemaback.persistence.user.role.Role;
import ee.bcs.cinemaback.persistence.user.role.RoleRepository;
import ee.bcs.cinemaback.service.user.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;





    public void initAdminUser() {
        User adminUser = userRepository.findByUsername(adminUsername).orElse(null);
        
        if (adminUser == null) {
            createAdminUser();
        } else {
            updateAdminPasswordIfNeeded(adminUser);
        }
    }
    
    private void createAdminUser() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername(adminUsername);
        registrationRequest.setPassword(adminPassword);
        registrationRequest.setEmail(adminEmail);
        
        User adminUser = userService.registerAndGetUser(registrationRequest);
        adminUser.setRole(getAdminRole());
        userRepository.save(adminUser);
    }
    
    private void updateAdminPasswordIfNeeded(User adminUser) {
        if (!passwordEncoder.matches(adminPassword, adminUser.getPassword())) {
            // Password in properties is different from stored password, update it
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            userRepository.save(adminUser);
        }
    }
    
    private Role getAdminRole() {
        return roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role role = new Role("ADMIN");
            return roleRepository.save(role);
        });
    }



}
