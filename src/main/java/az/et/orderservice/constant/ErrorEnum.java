package az.et.orderservice.constant;

public enum ErrorEnum {
    INTERNAL_ERROR(500, "Internal Error"),
    INTERNAL_ERROR_DESCRIPTION(500,"Internal Error : %s"),
    EXTERNAL_ERROR_BY_CALLING_API(500, "External error by calling api : %s"),
    USERNAME_NOT_FOUND(404, "Username not found"),
    AUTH_ERROR(403, "Authentication failed"),
    USER_ALREADY_EXISTS(400, "User already exists"),
    USER_NOT_FOUND(404, "User not found"),
    RELEVANT_ORDER_NOT_FOUND(404, "Relevant order not found"),
    ORDER_NOT_FOUND(404, "Oder not found");


    private final Integer code;
    private final String message;

    ErrorEnum(Integer code) {
        this(code, null);
    }

    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
