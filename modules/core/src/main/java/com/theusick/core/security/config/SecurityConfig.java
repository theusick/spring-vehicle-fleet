package com.theusick.core.security.config;

import com.theusick.core.security.service.DBUserDetailsService;
import com.theusick.core.security.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final DBUserDetailsService userDetailsService;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain webHttpSecurity(HttpSecurity http,
                                               JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
            .securityMatcher("/**")
            .csrf(CsrfConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(configurer -> configurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(request -> request
                .requestMatchers(
                    "/",
                    "/error",
                    "/login",
                    "/favicon.ico",
                    "/js/**",
                    "/css/**",
                    "/webjars/**",
                    "/images/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated())
            .formLogin(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) ->
                    response.sendRedirect("/login")))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout.deleteCookies(JwtService.JWT_COOKIE_NAME))
            .build();
    }

    @Bean
    public SecurityFilterChain apiHttpSecurity(HttpSecurity http,
                                               JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
            .securityMatcher("/api/**")
            .csrf(CsrfConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement((configurer) -> configurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((request) -> request
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder builder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
