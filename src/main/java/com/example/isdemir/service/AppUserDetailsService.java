package com.example.isdemir.service;

import com.example.isdemir.model.User;
import com.example.isdemir.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public AppUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

        // User.Role enum -> "ROLE_ADMIN" / "ROLE_OPERATOR"
        String authority = "ROLE_" + u.getPermission().name();

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPass())              // DB'deki BCrypt hash (pass sütunu)
                .authorities(authority)             // tek yetki örneği
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
