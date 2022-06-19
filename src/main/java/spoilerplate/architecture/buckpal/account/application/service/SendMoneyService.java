package spoilerplate.architecture.buckpal.account.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoilerplate.architecture.buckpal.account.application.port.in.SendMoneyCommand;
import spoilerplate.architecture.buckpal.account.application.port.in.SendMoneyUseCase;
import spoilerplate.architecture.buckpal.account.application.port.out.LoadAccountPort;
import spoilerplate.architecture.buckpal.account.application.port.out.UpdateAccountStatePort;

@Transactional
@RequiredArgsConstructor
@Service
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // TODO: 2022/06/19 비즈니스 규칙 검증
        // TODO: 2022/06/19 모델 상태 조작
        // TODO: 2022/06/19 출력 값 반환
        return false;
    }

}
