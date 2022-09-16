package spoilerplate.testing.spring.api.controller.product.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.domain.product.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductSearchRequest {

    private static final String ALL_CODE = "ALL";

    @NotNull(message = "검색어는 null이 될 수 없습니다.")
    private String keyword;

    @NotBlank(message = "상품 타입은 필수입니다.")
    private String productType; // ALL

    public List<ProductType> getProductTypes() {
        if (ALL_CODE.equals(productType)) {
            return List.of(ProductType.values());
        }
        return List.of(ProductType.valueOf(productType));
    }

}
