package com.example.isdemir.service;

import com.example.isdemir.model.User;
import com.example.isdemir.user.RegisterForm;
import com.example.isdemir.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void register(RegisterForm f) {
        // --- Güvenli alan okuma ---
        String username = safeTrim(f.getUsername());
        String pass = safeTrim(f.getPass());
        String confirm = safeTrim(f.getConfirmPassword());
        String perm = safeTrim(f.getPermission());

        // --- Temel kontroller ---
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı adı boş olamaz.");
        }
        if (pass == null || pass.isEmpty()) {
            throw new IllegalArgumentException("Şifre boş olamaz.");
        }
        // confirmPassword opsiyonel: varsa eşleşme kontrolü yap
        if (confirm != null && !confirm.isEmpty() && !pass.equals(confirm)) {
            throw new IllegalArgumentException("Şifre ve şifre tekrarı uyuşmuyor.");
        }

        // --- Benzersizlik kontrolü ---
        if (repo.existsByUsername(username)) {
            throw new IllegalStateException("Bu kullanıcı adı zaten kayıtlı.");
        }

        // --- Kullanıcı oluşturma ---
        User u = new User();
        u.setUsername(username);
        // DB'deki 'pass' sütununa hash yazıyoruz
        u.setPass(encoder.encode(pass));

        // permission: String -> Enum (boş ya da hatalıysa OPERATOR)
        User.Role role = User.Role.OPERATOR;
        if (perm != null && !perm.isEmpty()) {
            try {
                role = User.Role.valueOf(perm.toUpperCase());
            } catch (IllegalArgumentException ignored) {
                // geçersiz değer gelirse OPERATOR olarak bırak
            }
        }
        u.setPermission(role);

        u.setFullname(safeTrim(f.getFullname()));
        u.setDepartment(safeTrim(f.getDepartment()));

        repo.save(u);
    }

    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }
}
