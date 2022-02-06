package az.et.orderservice.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUserDetailsUtil {
    public static Long getCustomerId() {
        return (
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        ).getId();
    }
}