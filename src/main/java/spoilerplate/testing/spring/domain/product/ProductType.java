package spoilerplate.testing.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    BEVERAGE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    private final String text;

}
