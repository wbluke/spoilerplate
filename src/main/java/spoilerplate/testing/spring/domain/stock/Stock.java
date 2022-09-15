package spoilerplate.testing.spring.domain.stock;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;
    private long quantity;

    @Builder
    private Stock(String productNumber, long quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public static Stock create(String productNumber, long quantity) {
        return Stock.builder()
            .productNumber(productNumber)
            .quantity(quantity)
            .build();
    }

    public void deductQuantity(Long quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
        }
        this.quantity -= quantity;
    }

}
