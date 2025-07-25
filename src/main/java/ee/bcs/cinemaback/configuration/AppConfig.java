package ee.bcs.cinemaback.configuration;

import ee.bcs.cinemaback.service.user.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final AdminService adminService;

    @Bean
    public Clock clock() {
        //set system time zone
        return Clock.system(ZoneId.of("Europe/Tallinn"));
    }

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> adminService.initAdminUser();
    }


}
