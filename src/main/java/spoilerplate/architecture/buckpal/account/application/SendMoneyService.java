package spoilerplate.architecture.buckpal.account.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spoilerplate.architecture.buckpal.account.application.port.in.SendMoneyUseCase;
import spoilerplate.architecture.buckpal.account.application.port.out.LoadAccountPort;

@RequiredArgsConstructor
@Service
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;

}
