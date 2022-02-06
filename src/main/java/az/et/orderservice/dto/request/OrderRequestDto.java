package az.et.orderservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderRequestDto {

    private String description;
    private BigDecimal deliveryPrice;
    private BigDecimal deliveryWeight;
    private AddressDto addressFrom;
    private AddressDto addressTo;

}