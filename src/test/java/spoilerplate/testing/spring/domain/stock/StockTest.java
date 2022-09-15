package spoilerplate.testing.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @DisplayName("재고를 생성할 때 상품번호와 수량을 입력받는다.")
    @Test
    void createStock() {
        // given
        String productNumber = "001";
        long quantity = 1L;

        // when
        Stock stock = Stock.create(productNumber, quantity);

        // then
        assertThat(stock)
            .extracting("productNumber", "quantity")
            .contains(productNumber, quantity);
    }

    @DisplayName("재고 수량을 1 차감한다.")
    @Test
    void deductQuantity() {
        // given
        String productNumber = "001";
        long quantity = 1L;

        Stock stock = Stock.create(productNumber, quantity);

        // when
        stock.deductQuantity(1L);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고 수량이 0인 경우에는 차감할 수 없다.")
    @Test
    void deductQuantityWhenZero() {
        // given
        String productNumber = "001";
        long quantity = 0L;

        Stock stock = Stock.create(productNumber, quantity);

        // when // then
        assertThatThrownBy(() -> stock.deductQuantity(1L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("차감할 재고 수량이 없습니다.");
    }

}