package spoilerplate.testing.spring.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.domain.BaseEntity;
import spoilerplate.testing.spring.domain.orderproduct.OrderProduct;
import spoilerplate.testing.spring.domain.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;
    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Order(OrderStatus orderStatus, List<Product> products, LocalDateTime registeredDateTime) {
        this.orderStatus = orderStatus;
        this.orderProducts = products.stream()
            .map(product -> new OrderProduct(this, product))
            .collect(Collectors.toList());
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
    }

    public static Order createInitialOrder(List<Product> products, LocalDateTime createdDateTime) {
        return new Order(OrderStatus.INIT, products, createdDateTime);
    }

    private int calculateTotalPrice(List<Product> products) {
        return products.stream()
            .mapToInt(Product::getPrice)
            .sum();
    }

}
