package spoilerplate.architecture.buckpal.account.application.port.in;

import spoilerplate.architecture.buckpal.account.domain.AccountId;
import spoilerplate.architecture.buckpal.account.domain.Money;

public interface GetAccountBalanceQuery {

    Money getAccountBalance(AccountId accountId);

}
