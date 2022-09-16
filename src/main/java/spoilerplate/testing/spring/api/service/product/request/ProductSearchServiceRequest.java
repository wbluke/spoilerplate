package spoilerplate.testing.spring.api.service.product.request;

import lombok.Builder;
import lombok.Getter;
import spoilerplate.testing.spring.domain.product.ProductType;

import java.util.List;

@Getter
public class ProductSearchServiceRequest {

    private String keyword;
    private List<ProductType> productTypes;

    @Builder
    private ProductSearchServiceRequest(String keyword, List<ProductType> productTypes) {
        this.keyword = keyword;
        this.productTypes = productTypes;
    }

}
