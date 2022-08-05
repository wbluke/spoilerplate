package spoilerplate.testing.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductSellingStatus;

@Getter
public class ProductResponse {

    private Long id;
    private String productNumber;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductSellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .productNumber(product.getProductNumber())
            .sellingStatus(product.getSellingStatus())
            .name(product.getName())
            .price(product.getPrice())
            .build();
    }

}
