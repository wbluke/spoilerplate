package spoilerplate.testing.spring.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findStocksByProductNumberIn(List<String> productNumbers);

}
