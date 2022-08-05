package spoilerplate.testing.spring.domain.product;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.domain.orderproduct.OrderProduct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    private Long id;

    private String productNumber;
    private String name;
    private int price;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts;

    @Builder
    private Product(String productNumber, String name, int price) {
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
    }

}
