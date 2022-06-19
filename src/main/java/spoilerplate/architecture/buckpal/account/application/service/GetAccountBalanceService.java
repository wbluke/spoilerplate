package spoilerplate.architecture.buckpal.account.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spoilerplate.architecture.buckpal.account.application.port.in.GetAccountBalanceQuery;
import spoilerplate.architecture.buckpal.account.application.port.out.LoadAccountPort;
import spoilerplate.architecture.buckpal.account.domain.AccountId;
import spoilerplate.architecture.buckpal.account.domain.Money;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
            .calculateBalance();
    }

}
