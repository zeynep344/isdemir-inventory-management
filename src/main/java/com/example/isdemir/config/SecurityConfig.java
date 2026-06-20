package com.example.isdemir.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> { })

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login-page", "/perform_login", "/register",
                                "/healthz", "/error",
                                "/css/**", "/js/**", "/images/**", "/webjars/**"
                        ).permitAll()

                        // LİSTELEME: ADMIN + OPERATOR + USER
                        .requestMatchers(org.springframework.http.HttpMethod.GET,
                                "/products", "/products/**"
                        ).hasAnyRole("ADMIN", "OPERATOR", "USER")

                        // YENİ FORM ve DÜZENLE FORM: ADMIN + OPERATOR
                        .requestMatchers(
                                "/products/new",
                                "/products/*/edit",   // /products/{id}/edit
                                "/products/edit/*"    // /products/edit/{id}
                        ).hasAnyRole("ADMIN", "OPERATOR")

                        // KAYDET (/save): ADMIN + OPERATOR
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/products/save")
                        .hasAnyRole("ADMIN", "OPERATOR")

                        // SİLME: SADECE ADMIN
                        .requestMatchers("/products/*/delete", "/products/delete/*")
                        .hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/products/**")
                        .hasRole("ADMIN")

                        // (Koruyucu) Diğer değiştirici istekler: default ADMIN
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/products/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT,  "/products/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login-page")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("username")
                        .passwordParameter("pass")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login-page?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login-page?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .exceptionHandling(ex -> ex.accessDeniedPage("/403"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
