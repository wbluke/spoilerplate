package spoilerplate.architecture.buckpal.account.application.port.out;

import spoilerplate.architecture.buckpal.account.domain.Account;
import spoilerplate.architecture.buckpal.account.domain.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(AccountId accountId, LocalDateTime now);

}
