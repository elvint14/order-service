package az.et.orderservice.service;

import az.et.orderservice.constant.OrderStatus;
import az.et.orderservice.dto.request.AddressDto;
import az.et.orderservice.dto.request.OrderRequestDto;
import az.et.orderservice.dto.request.UserDto;
import az.et.orderservice.dto.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    BaseResponse<?> createOrder(OrderRequestDto orderRequestDto);

    BaseResponse<?> changeDeliveryAddress(Long orderId, AddressDto newDeliveryAddressDto);

    BaseResponse<?> cancelOrder(Long orderId);

    BaseResponse<?> getDetailsOfOrder(Long orderId);

    BaseResponse<?> getDetailsOfAllOrder();

    BaseResponse<?> changeStatusOfOrder(Long orderId, OrderStatus newOrderStatus);

    BaseResponse<?> findDetailsOfAllOrder();

    BaseResponse<?> assignCourier(Long orderId, Long courierId);

    BaseResponse<?> createCourier(UserDto courierDto);

    BaseResponse<?> findDetailsOfAllOrderByCourier();

    BaseResponse<?> changeStatusOfOrderByCourier(Long orderId, OrderStatus newOrderStatus);

    BaseResponse<?> findDetailsOfOrderByCourier(Long orderId);
}
