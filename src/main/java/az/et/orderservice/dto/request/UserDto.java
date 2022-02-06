package az.et.orderservice.dto.request;

import lombok.Data;

@Data
public class UserDto {
    private String fullName;
    private String username;
    private String password;
}