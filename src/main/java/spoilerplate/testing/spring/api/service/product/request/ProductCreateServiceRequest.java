package spoilerplate.testing.spring.api.service.product.request;

import lombok.Builder;
import lombok.Getter;
import spoilerplate.testing.spring.domain.product.ProductSellingStatus;
import spoilerplate.testing.spring.domain.product.ProductType;

@Getter
public class ProductCreateServiceRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

}
