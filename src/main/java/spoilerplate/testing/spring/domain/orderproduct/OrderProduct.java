package spoilerplate.testing.spring.domain.orderproduct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.domain.order.Order;
import spoilerplate.testing.spring.domain.product.Product;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class OrderProduct {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne
    private Product product;

    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public int getPrice() {
        return product.getPrice();
    }

}
