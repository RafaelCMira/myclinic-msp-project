package com.myclinic.auth;

import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.exception.customexceptions.UnauthorizedException;
import com.myclinic.utils.Validations;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Optional;

@Service
class AuthService {

    private final JdbcTemplate db;

    private final UserMapper userMapper;

    AuthService(JdbcTemplate db, UserMapper userMapper) {
        this.db = db;
        this.userMapper = userMapper;
    }

    UserDTO register(UserDTO userDTO) {
        Validations.validate(userDTO);

        var user = UserMapper.fromDTO(userDTO);

        String procedureCall;
        if (user.getRole().equals("patient")) {
            procedureCall = "{call insert_patient(?, ?, ?, ?, ?, ?)}";
        } else {
            procedureCall = "{call insert_doctor(?, ?, ?, ?, ?, ?)}";
        }

        var userId = db.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
            cs.setString(1, user.getName());
            cs.setDate(2, java.sql.Date.valueOf(user.getBirthDate()));
            cs.setString(3, user.getEmail());
            cs.setString(4, user.getPassword());
            cs.setString(5, user.getPhone());
            cs.registerOutParameter(6, Types.INTEGER);
            cs.execute();
            return cs.getInt(6);
        });

        var id = Optional.ofNullable(userId).orElse(0);


        return UserDTO.withId(userDTO, id);
    }

    void login(String email, String password) {
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
    }


}
