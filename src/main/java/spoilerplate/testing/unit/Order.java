package spoilerplate.testing.unit;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Order {

    private LocalDate now;
    private List<Beverage> beverages;

    public Order(LocalDate now, List<Beverage> beverages) {
        this.now = now;
        this.beverages = beverages;
    }

}
