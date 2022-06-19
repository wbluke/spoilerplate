package spoilerplate.architecture.buckpal.account.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Money {

    private final long value;

    public static Money add(Money money, Money addition) {
        return new Money(money.value + addition.value);
    }

    public Money negate() {
        return new Money(-1 * value);
    }

    public boolean isPositive() {
        return value > 0;
    }

}
