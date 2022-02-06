package az.et.orderservice.controller;

import az.et.orderservice.constant.OrderStatus;
import az.et.orderservice.dto.request.AddressDto;
import az.et.orderservice.dto.request.OrderRequestDto;
import az.et.orderservice.dto.request.UserDto;
import az.et.orderservice.dto.response.BaseResponse;
import az.et.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class OrderController {
    private final OrderService orderService;

    @PostMapping(
            value = "/create-order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<BaseResponse<?>> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(
                orderService.createOrder(orderRequestDto)
        );
    }

    @PostMapping(
            value = "/{orderId}/change-deliver-address",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<BaseResponse<?>> changeDeliveryAddress(@PathVariable Long orderId,
                                                                 @RequestBody AddressDto newDeliveryAddressDto) {
        return ResponseEntity.ok(
                orderService.changeDeliveryAddress(orderId, newDeliveryAddressDto)
        );
    }

    @PostMapping(
            value = "/{orderId}/cancel-order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<BaseResponse<?>> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                orderService.cancelOrder(orderId)
        );
    }

    @GetMapping(
            value = "/{orderId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<BaseResponse<?>> getDetailsOfOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                orderService.getDetailsOfOrder(orderId)
        );
    }

    @GetMapping(
            value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<BaseResponse<?>> getDetailsOfAllOrder() {
        return ResponseEntity.ok(
                orderService.getDetailsOfAllOrder()
        );
    }

    @PostMapping(
            value = "/{orderId}/change-status",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<?>> changeStatusOfOrder(@PathVariable Long orderId,
                                                               @RequestBody OrderStatus newOrderStatus) {
        return ResponseEntity.ok(
                orderService.changeStatusOfOrder(orderId, newOrderStatus)
        );
    }

    @GetMapping(
            value = "/admin/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<?>> findDetailsOfAllOrder() {
        return ResponseEntity.ok(
                orderService.findDetailsOfAllOrder()
        );
    }

    @PostMapping(
            value = "/{orderId}/assign-courier}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<?>> assignCourier(@PathVariable Long orderId,
                                                         @RequestParam Long courierId) {
        return ResponseEntity.ok(
                orderService.assignCourier(orderId, courierId)
        );
    }

    @PostMapping(
            value = "/admin/create-courier",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<?>> createCourier(@RequestBody UserDto courierDto) {
        return ResponseEntity.ok(
                orderService.createCourier(courierDto)
        );
    }

    @GetMapping(
            value = "/courier/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('COURIER')")
    public ResponseEntity<BaseResponse<?>> findDetailsOfAllOrderByCourier() {
        return ResponseEntity.ok(
                orderService.findDetailsOfAllOrderByCourier()
        );
    }

    @PostMapping(
            value = "/{orderId}/change-status-courier",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('COURIER')")
    public ResponseEntity<BaseResponse<?>> changeStatusOfOrderByCourier(@PathVariable Long orderId,
                                                                        @RequestBody OrderStatus newOrderStatus) {
        return ResponseEntity.ok(
                orderService.changeStatusOfOrderByCourier(orderId, newOrderStatus)
        );
    }

    @GetMapping(
            value = "/{orderId}/courier/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('COURIER')")
    public ResponseEntity<BaseResponse<?>> findDetailsOfOrderByCourier(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                orderService.findDetailsOfOrderByCourier(orderId)
        );
    }
}