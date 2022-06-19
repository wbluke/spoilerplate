package spoilerplate.architecture.buckpal.account.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class AccountId {

    private final Long id;

    public AccountId(Long accountId) {
        this.id = accountId;
    }

}
