package az.et.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "reviews")
@AllArgsConstructor
public class Review implements Serializable {
    private static final long serialVersionUID = 8916286765054251635L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "review")
    private String review;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public Review() {
    }
}