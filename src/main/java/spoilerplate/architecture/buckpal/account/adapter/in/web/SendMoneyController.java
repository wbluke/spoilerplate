package spoilerplate.architecture.buckpal.account.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spoilerplate.architecture.buckpal.account.application.port.in.SendMoneyCommand;
import spoilerplate.architecture.buckpal.account.application.port.in.SendMoneyUseCase;
import spoilerplate.architecture.buckpal.account.domain.AccountId;
import spoilerplate.architecture.buckpal.account.domain.Money;

@RequiredArgsConstructor
@RestController
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    public void sendMoney(
        @PathVariable("sourceAccountId") Long sourceAccountId,
        @PathVariable("targetAccountId") Long targetAccountId,
        @PathVariable("amount") Long amount
    ) {
        SendMoneyCommand sendMoneyCommand = new SendMoneyCommand(
            new AccountId(sourceAccountId),
            new AccountId(sourceAccountId),
            Money.of(amount)
        );

        sendMoneyUseCase.sendMoney(sendMoneyCommand);
    }

}
