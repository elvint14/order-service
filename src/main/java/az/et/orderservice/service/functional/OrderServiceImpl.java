package az.et.orderservice.service.functional;

import az.et.orderservice.constant.ErrorEnum;
import az.et.orderservice.constant.OrderStatus;
import az.et.orderservice.dto.request.AddressDto;
import az.et.orderservice.dto.request.OrderRequestDto;
import az.et.orderservice.dto.request.UserDto;
import az.et.orderservice.dto.response.BaseResponse;
import az.et.orderservice.exception.BaseException;
import az.et.orderservice.model.Address;
import az.et.orderservice.model.Order;
import az.et.orderservice.model.RoleEntity;
import az.et.orderservice.model.UserEntity;
import az.et.orderservice.repository.AddressRepository;
import az.et.orderservice.repository.OrderRepository;
import az.et.orderservice.service.OrderService;
import az.et.orderservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static az.et.orderservice.constant.OrderStatus.CANCELED;
import static az.et.orderservice.constant.OrderStatus.DELIVERED;
import static az.et.orderservice.constant.UserStatusEnum.EMAIL_CONFIRMED;
import static az.et.orderservice.dto.request.AddressDto.mapToEntity;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Override
    @Transactional
    public BaseResponse<?> createOrder(OrderRequestDto orderRequestDto) {
        final UserEntity customer = userService.findCurrentUserFromContextOrThrEx();
        final Address addressFrom = mapToEntity(orderRequestDto.getAddressFrom(), customer);
        final Address addressTo = mapToEntity(orderRequestDto.getAddressTo(), customer);
        addressRepository.save(addressFrom);
        addressRepository.save(addressTo);
        final Order newOrder = Order.of(
                orderRequestDto.getDescription(),
                OrderStatus.ACCEPTED,
                orderRequestDto.getDeliveryPrice(),
                orderRequestDto.getDeliveryWeight(),
                customer,
                addressFrom,
                addressTo
        );
        orderRepository.save(newOrder);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse<?> changeDeliveryAddress(Long orderId, AddressDto newDeliveryAddressDto) {
        final UserEntity customer = userService.findCurrentUserFromContextOrThrEx();
        return orderRepository.findByIdAndCustomerAndStatusNotIn(
                        orderId,
                        customer,
                        Arrays.asList(CANCELED, DELIVERED)
                )
                .map(order -> {
                    final Address newToAddress = mapToEntity(newDeliveryAddressDto, customer);
                    addressRepository.save(newToAddress);
                    order.setAddressTo(newToAddress);
                    orderRepository.save(order);
                    return BaseResponse.ok();
                }).orElseThrow(
                        () -> BaseException.of(ErrorEnum.RELEVANT_ORDER_NOT_FOUND)
                );
    }

    @Override
    public BaseResponse<?> cancelOrder(Long orderId) {
        return orderRepository.findByIdAndCustomerAndStatusNotIn(
                orderId,
                userService.findCurrentUserFromContextOrThrEx(),
                List.of(DELIVERED)
        ).map(order -> {
            order.setStatus(CANCELED);
            orderRepository.save(order);
            return BaseResponse.ok();
        }).orElseThrow(
                () -> BaseException.of(ErrorEnum.RELEVANT_ORDER_NOT_FOUND)
        );
    }

    @Override
    public BaseResponse<?> getDetailsOfOrder(Long orderId) {
        return BaseResponse.ok(
                orderRepository.findByIdAndCustomer(
                        orderId,
                        userService.findCurrentUserFromContextOrThrEx()
                ).orElseThrow(
                        () -> BaseException.of(ErrorEnum.ORDER_NOT_FOUND)
                )
        );
    }

    @Override
    public BaseResponse<?> getDetailsOfAllOrder() {
        return BaseResponse.ok(
                orderRepository.findAllCustomer(
                        userService.findCurrentUserFromContextOrThrEx()
                )
        );
    }

    @Override
    public BaseResponse<?> changeStatusOfOrder(Long orderId, OrderStatus newOrderStatus) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(newOrderStatus);
            orderRepository.save(order);
            return BaseResponse.ok();
        }).orElseThrow(
                () -> BaseException.of(ErrorEnum.ORDER_NOT_FOUND)
        );
    }

    @Override
    public BaseResponse<?> findDetailsOfAllOrder() {
        return BaseResponse.ok(
                orderRepository.findAll()
        );
    }

    @Override
    public BaseResponse<?> assignCourier(Long orderId, Long courierId) {
        return orderRepository.findById(orderId).map(order -> {
            final UserEntity courier = userService.findUserById(courierId)
                    .orElseThrow(() -> BaseException.of(ErrorEnum.USER_NOT_FOUND));
            order.setCourier(courier);
            orderRepository.save(order);
            return BaseResponse.ok();
        }).orElseThrow(
                () -> BaseException.of(ErrorEnum.ORDER_NOT_FOUND)
        );
    }

    @Override
    public BaseResponse<?> createCourier(UserDto courierDto) {
        final UserEntity courier = UserEntity.builder()
                .fullName(courierDto.getFullName())
                .password(passwordEncoder.encode(courierDto.getPassword()))
                .roles(Collections.singleton(RoleEntity.builder().id(2L).build()))
                .status(EMAIL_CONFIRMED)
                .build();
        return BaseResponse.ok(userService.save(courier));
    }

    @Override
    public BaseResponse<?> findDetailsOfAllOrderByCourier() {
        final UserEntity courier = userService.findCurrentUserFromContextOrThrEx();
        return BaseResponse.ok(
                orderRepository.findAllCustomer(courier)
        );
    }

    @Override
    public BaseResponse<?> changeStatusOfOrderByCourier(Long orderId, OrderStatus newOrderStatus) {
        final UserEntity courier = userService.findCurrentUserFromContextOrThrEx();
        return orderRepository.findByIdAndCourierAndStatusNotIn(
                orderId,
                courier,
                Arrays.asList(DELIVERED, CANCELED)
        ).map(order -> {
            order.setStatus(newOrderStatus);
            orderRepository.save(order);
            return BaseResponse.ok();
        }).orElseThrow(() -> BaseException.of(ErrorEnum.RELEVANT_ORDER_NOT_FOUND));
    }

    @Override
    public BaseResponse<?> findDetailsOfOrderByCourier(Long orderId) {
        final UserEntity courier = userService.findCurrentUserFromContextOrThrEx();
        return orderRepository.findByIdAndCourier(orderId, courier)
                .map(BaseResponse::ok)
                .orElseThrow(() -> BaseException.of(ErrorEnum.ORDER_NOT_FOUND));
    }
}