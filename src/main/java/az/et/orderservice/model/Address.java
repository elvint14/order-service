package az.et.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@Table(name = "addresses")
@AllArgsConstructor
public class Address implements Serializable {
    private static final long serialVersionUID = 7916286765054251635L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    public Address() {
    }
}
