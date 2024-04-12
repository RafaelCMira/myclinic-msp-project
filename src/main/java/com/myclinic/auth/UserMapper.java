package com.myclinic.auth;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
class UserMapper implements RowMapper<User> {

    static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getRole()
        );
    }

    static User fromDTO(UserDTO userDTO) {
        return new User(
                userDTO.id(),
                userDTO.name(),
                userDTO.birthDate(),
                userDTO.email(),
                userDTO.password(),
                userDTO.phone(),
                userDTO.role()
        );
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) {
        return new User(
                Utility.getIntOrNull(rs, "id"),
                Utility.getStringOrNull(rs, "name"),
                Utility.getLocalDateOrNull(rs, "birth_date"),
                Utility.getStringOrNull(rs, "email"),
                Utility.getStringOrNull(rs, "password"),
                Utility.getStringOrNull(rs, "phone"),
                Utility.getStringOrNull(rs, "role")
        );
    }
}
