package spoilerplate.architecture.buckpal.account.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class ActivityWindow {

    List<Activity> activities;

    public Money calculateBalance(AccountId accountId) {
        return null;
    }

    public void addActivity(Activity activity) {

    }

}
