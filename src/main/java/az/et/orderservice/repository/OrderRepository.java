package az.et.orderservice.repository;

import az.et.orderservice.constant.OrderStatus;
import az.et.orderservice.model.Order;
import az.et.orderservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndCustomerAndStatusNotIn(Long orderId,
                                                      UserEntity customer,
                                                      List<OrderStatus> orderStatusList);

    Optional<Order> findByIdAndCourierAndStatusNotIn(Long orderId,
                                                     UserEntity courier,
                                                     List<OrderStatus> orderStatusList);

    Optional<Order> findByIdAndCustomer(Long orderId,
                                        UserEntity customer);

    List<Order> findAllByCustomer(UserEntity customer);

    Optional<Order> findByIdAndCourier(Long orderId,
                                       UserEntity customer);

}