package spoilerplate.cleancode.studycafe.tobe.model.pass.locker;

import spoilerplate.cleancode.studycafe.tobe.model.pass.StudyCafePass;
import spoilerplate.cleancode.studycafe.tobe.model.pass.StudyCafePassType;

public class StudyCafeLockerPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;

    private StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public boolean isSamePassType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSameDuration(int duration) {
        return this.duration == duration;
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

}
