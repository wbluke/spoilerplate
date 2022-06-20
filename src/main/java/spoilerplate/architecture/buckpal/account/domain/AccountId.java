package spoilerplate.architecture.buckpal.account.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class AccountId {

    private final Long value;

    public AccountId(Long accountId) {
        this.value = accountId;
    }

}
