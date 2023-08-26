package com.anilozmen.springbootmvcblog.config;

import com.anilozmen.springbootmvcblog.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final UserRepository userRepository;

  public SecurityConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((auth) -> auth
        .requestMatchers("/register", "/login").anonymous()
        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/", "/**").permitAll()
        .anyRequest().authenticated()
    ).formLogin(login -> login
        .loginPage("/login")
        .usernameParameter("email")
        .defaultSuccessUrl("/", true)
    ).logout(logout -> logout
        .logoutSuccessUrl("/login")
        .clearAuthentication(true)
    );
    return http.build();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
