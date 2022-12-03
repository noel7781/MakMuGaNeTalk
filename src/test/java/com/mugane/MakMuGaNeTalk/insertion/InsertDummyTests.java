package com.mugane.MakMuGaNeTalk.insertion;

import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.enums.UserRoleType;
import com.mugane.MakMuGaNeTalk.repository.UserRepository;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class InsertDummyTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            User user = User.builder()
                .email("user" + i + "@test.com")
                .nickname("user" + i)
                .fromSocial(false)
                .password(passwordEncoder.encode("1234"))
                .build();

            user.addRole(UserRoleType.ROLE_USER);
            if (i % 10 == 1) {
                user.addRole(UserRoleType.ROLE_ADMIN);
            }
            userRepository.save(user);
        });
    }
}
