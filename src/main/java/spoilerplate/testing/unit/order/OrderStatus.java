package spoilerplate.testing.unit.order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    INIT("생성"),
    PAYMENT_COMPLETED("결제완료"),
    RECEIVED("주문접수"),
    COMPLETED("처리완료");

    private final String text;

}
