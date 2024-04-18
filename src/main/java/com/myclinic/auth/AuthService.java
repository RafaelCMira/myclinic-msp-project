package com.myclinic.auth;

import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.exception.customexceptions.UnauthorizedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
class AuthService {

    private final JdbcTemplate db;

    private final UserMapper userMapper;

    AuthService(JdbcTemplate db, UserMapper userMapper) {
        this.db = db;
        this.userMapper = userMapper;
    }

    UserDTO login(String email, String password) {
        String query = """
                SELECT
                    user_id AS id,
                    name,
                    email,
                    password,
                    phone,
                    birth_date
                FROM
                    users
                WHERE
                    email = ?
                """;

        var user = db.query(query, userMapper, email)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User: " + email + " don't exist!"));

        if (!user.getPassword().equals(password)) {
            throw new UnauthorizedException("Password is incorrect!");
        }

        return UserMapper.toDTO(user);
    }


}
