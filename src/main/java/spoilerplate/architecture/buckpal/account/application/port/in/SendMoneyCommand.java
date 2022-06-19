package spoilerplate.architecture.buckpal.account.application.port.in;

import lombok.Getter;
import spoilerplate.architecture.buckpal.account.domain.AccountId;
import spoilerplate.architecture.buckpal.account.domain.Money;
import spoilerplate.architecture.buckpal.utils.validate.SelfValidating;

import javax.validation.constraints.NotNull;

@Getter
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final AccountId sourceAccountId;
    @NotNull
    private final AccountId targetAccountId;
    @NotNull
    private final Money money;

    public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;

        this.validateSelf();
        requireGreaterThan(money, 0);
    }

    private void requireGreaterThan(Money money, long base) {
        if (money.getValue() <= base) {
            throw new IllegalArgumentException();
        }
    }
}
