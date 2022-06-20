package spoilerplate.architecture.buckpal.account.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Activity {

    private AccountId id;

    public Activity(AccountId id, AccountId id1, AccountId targetAccountId, LocalDateTime now, Money money) {
        this.id = id;
    }

}
