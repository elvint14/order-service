package az.et.orderservice.dto.request;

import az.et.orderservice.model.Address;
import az.et.orderservice.model.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
    private String postalCode;
    private String phone;
    private String description;

    public static Address mapToEntity(AddressDto addressDto, UserEntity customer) {
        return Address
                .builder()
                .country(addressDto.getCountry())
                .city(addressDto.getCity())
                .latitude(addressDto.getLatitude())
                .longitude(addressDto.getLongitude())
                .description(addressDto.getDescription())
                .postalCode(addressDto.getPostalCode())
                .phone(addressDto.getPhone())
                .customer(customer)
                .build();
    }
}
