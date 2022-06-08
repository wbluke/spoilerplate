package spoilerplate.architecture.buckpal.account.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import spoilerplate.architecture.buckpal.account.application.port.in.SendMoneyUseCase;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final SendMoneyUseCase sendMoneyUseCase;

}
