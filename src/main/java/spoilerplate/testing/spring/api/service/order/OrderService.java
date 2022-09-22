package spoilerplate.testing.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoilerplate.testing.spring.api.service.order.request.OrderServiceRequest;
import spoilerplate.testing.spring.api.service.order.response.OrderResponse;
import spoilerplate.testing.spring.domain.order.Order;
import spoilerplate.testing.spring.domain.order.OrderRepository;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductRepository;
import spoilerplate.testing.spring.domain.product.ProductType;
import spoilerplate.testing.spring.domain.stock.Stock;
import spoilerplate.testing.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderServiceRequest orderRequest, LocalDateTime orderCreatedDateTime) {
        List<String> productNumbers = orderRequest.getProductNumbers();
        Map<String, Product> productCandidatesMap = createProductCandidatesMap(productNumbers);

        deductStockQuantities(productNumbers, productCandidatesMap);

        Order order = createOrder(productNumbers, productCandidatesMap, orderCreatedDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private Map<String, Product> createProductCandidatesMap(List<String> productNumbers) {
        List<Product> productCandidates = productRepository.findProductsByProductNumberIn(new HashSet<>(productNumbers));
        return productCandidates.stream()
            .collect(Collectors.toMap(Product::getProductNumber, p -> p));
    }

    private void deductStockQuantities(List<String> productNumbers, Map<String, Product> productCandidatesMap) {
        List<String> stockProductNumbers = filterStockProductNumbers(productCandidatesMap);

        Map<String, Stock> stockMap = createStockMap(stockProductNumbers);
        Map<String, Long> productNumberCountingMap = productNumbers.stream()
            .collect(Collectors.groupingBy(productNumber -> productNumber, Collectors.counting()));

        for (String stockProductNumber : stockProductNumbers) {
            Stock stock = stockMap.get(stockProductNumber);
            Long quantity = productNumberCountingMap.get(stockProductNumber);
            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private List<String> filterStockProductNumbers(Map<String, Product> productCandidatesMap) {
        return productCandidatesMap.entrySet().stream()
            .filter(entry -> ProductType.containsStockType(entry.getValue().getType()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    private Map<String, Stock> createStockMap(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findStocksByProductNumberIn(stockProductNumbers);
        return stocks.stream()
            .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    private Order createOrder(List<String> productNumbers, Map<String, Product> productCandidatesMap, LocalDateTime orderCreatedDateTime) {
        List<Product> products = productNumbers.stream()
            .map(productCandidatesMap::get)
            .collect(Collectors.toList());
        return Order.createInitialOrder(products, orderCreatedDateTime);
    }

}
