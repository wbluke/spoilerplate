package spoilerplate.testing.spring.api.controller.product.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.api.service.product.request.ProductCreateServiceRequest;
import spoilerplate.testing.spring.domain.product.ProductSellingStatus;
import spoilerplate.testing.spring.domain.product.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;

    @NotNull(message = "상품 판매 상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @Positive(message = "상품 가격은 필수입니다.")
    private int price;

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }

}
