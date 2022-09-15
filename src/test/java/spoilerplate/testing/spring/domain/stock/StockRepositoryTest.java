package spoilerplate.testing.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("상품번호 리스트로 판매할 수 있는 재고를 조회한다.")
    @Test
    void findStocksByProductNumberIn() {
        // given
        Stock stock1 = createStock("001", 1L);
        Stock stock2 = createStock("002", 2L);
        Stock stock3 = createStock("003", 3L);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // when
        List<Stock> stocks = stockRepository.findStocksByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 1L),
                tuple("002", 2L)
            );
    }

    private Stock createStock(String productNumber, long quantity) {
        return Stock.builder()
            .productNumber(productNumber)
            .quantity(quantity)
            .build();
    }

}