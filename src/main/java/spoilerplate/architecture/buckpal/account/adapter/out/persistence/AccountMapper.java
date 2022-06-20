package spoilerplate.architecture.buckpal.account.adapter.out.persistence;

import spoilerplate.architecture.buckpal.account.domain.Account;
import spoilerplate.architecture.buckpal.account.domain.Activity;

import java.util.List;

public class AccountMapper {

    public Account mapToDomainEntity(AccountJpaEntity account, List<ActivityJpaEntity> activities, Long withdrawalBalance, Long depositBalance) {
        return null;
    }

    public ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return null;
    }

}
