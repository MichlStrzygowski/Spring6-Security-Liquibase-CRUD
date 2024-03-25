package pl.michalstrzygowski.restapi.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.michalstrzygowski.restapi.model.Role;
import pl.michalstrzygowski.restapi.model.User;
import org.springframework.security.web.SecurityFilterChain;
import pl.michalstrzygowski.restapi.repository.UserRepository;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService detailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(detailsService);
        return new ProviderManager(provider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/posts**").authenticated()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("swagger-resources/**").permitAll()
                        .requestMatchers("/v2/api-docs").permitAll()
                        .requestMatchers("h2-console/**").permitAll()
                )
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = User.builder().username("admin").password(passwordEncoder.encode("admin")).email("some@email.com").role(Role.ADMIN).enabled(true).build();
            userRepository.save(user);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user2 = User.builder().username("user").password(passwordEncoder.encode("user")).email("som@email.com").role(Role.USER).enabled(true).build();
            userRepository.save(user2);
        }

        return args -> {
            System.out.println("User count: " + userRepository.count());
        };
    }
}

