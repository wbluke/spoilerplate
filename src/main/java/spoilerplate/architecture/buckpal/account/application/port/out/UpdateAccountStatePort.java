package spoilerplate.architecture.buckpal.account.application.port.out;

import spoilerplate.architecture.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
