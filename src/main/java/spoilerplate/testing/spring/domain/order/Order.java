package spoilerplate.testing.spring.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.domain.orderproduct.OrderProduct;
import spoilerplate.testing.spring.domain.product.Product;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    public Order(OrderStatus orderStatus, List<Product> products) {
        this.orderStatus = orderStatus;
        this.orderProducts = products.stream()
            .map(product -> new OrderProduct(this, product))
            .collect(Collectors.toList());
    }

    public static Order createInitialOrder(List<Product> products) {
        return new Order(OrderStatus.INIT, products);
    }

    public int calculateTotalPrice() { // field에 값을 관리하는 방법도 있음. Trade-off
        return orderProducts.stream()
            .mapToInt(OrderProduct::getPrice)
            .sum();
    }

}
