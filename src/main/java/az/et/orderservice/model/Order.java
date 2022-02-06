package az.et.orderservice.model;

import az.et.orderservice.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static az.et.orderservice.constant.OrderStatus.CREATED;

@Data
@Entity
@Builder
@Table(name = "orders")
@AllArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = 7916286765054251635L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @Column(name = "delivery_weight")
    private BigDecimal deliveryWeight;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private UserEntity courier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_from_id")
    private Address addressFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_to_id")
    private Address addressTo;

    public Order() {
    }

    public static Order of(String description,
                           OrderStatus status,
                           BigDecimal deliveryPrice,
                           BigDecimal deliveryWeight,
                           UserEntity customer,
                           Address addressFrom,
                           Address addressTo
                           ) {
        return Order
                .builder()
                .description(description)
                .status(status)
                .deliveryPrice(deliveryPrice)
                .deliveryWeight(deliveryWeight)
                .customer(customer)
                .addressFrom(addressFrom)
                .addressTo(addressTo)
                .build();
    }
}