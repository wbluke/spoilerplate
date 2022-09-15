package spoilerplate.testing.spring.domain.orderproduct;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.domain.order.Order;
import spoilerplate.testing.spring.domain.product.Product;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product; // id mapping 도 가능. Trade-off

    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

}
