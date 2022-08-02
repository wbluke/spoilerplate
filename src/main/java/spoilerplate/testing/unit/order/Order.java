package spoilerplate.testing.unit.order;

import lombok.Getter;
import spoilerplate.testing.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {

    private List<Beverage> beverages;
    private LocalDateTime createdDateTime;
    private OrderStatus status;

    public Order(List<Beverage> beverages, LocalDateTime createdDateTime, OrderStatus status) {
        this.beverages = beverages;
        this.createdDateTime = createdDateTime;
        this.status = status;
    }

}
