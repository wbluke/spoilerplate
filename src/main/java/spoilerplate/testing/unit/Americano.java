package spoilerplate.testing.unit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Americano implements Beverage {

    private final int price;

    @Override
    public String getName() {
        return "아메리카노";
    }

}
