package ee.bcs.cinemaback.security;

import ee.bcs.cinemaback.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        String mainUrl = "/api/v1/**";
        String admin = "ADMIN";
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/user/login", "/api/v1/user/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/ticket__public/type/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/ticket__public/*").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/ticket__public/*").authenticated()
                .requestMatchers(HttpMethod.GET, mainUrl).permitAll()
                // admin endpoints
                .requestMatchers(HttpMethod.POST, mainUrl).hasRole(admin)
                .requestMatchers(HttpMethod.PUT, mainUrl).hasRole(admin)
                .requestMatchers(HttpMethod.DELETE, mainUrl).hasRole(admin)
                .requestMatchers("/api/admin/**").hasRole(admin)
                // end admin endpoints
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .csrf().disable();

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }



}


