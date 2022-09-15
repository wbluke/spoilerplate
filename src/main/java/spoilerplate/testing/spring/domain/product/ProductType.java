package spoilerplate.testing.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    private final String text;

    public static Set<ProductType> getStockTypes() {
        return Set.of(BOTTLE, BAKERY);
    }

    public static boolean containsStockType(ProductType productType) {
        return getStockTypes().contains(productType);
    }

}
