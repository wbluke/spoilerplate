package spoilerplate.testing.spring.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT("생성"),
    PAYMENT_COMPLETED("결제완료"),
    RECEIVED("주문접수"),
    COMPLETED("처리완료");

    private final String text;

}
