package spoilerplate.cleancode.studycafe.tobe.model.pass.locker;

import spoilerplate.cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;

import java.util.List;
import java.util.Optional;

public class StudyCafeLockerPasses {

    private final List<StudyCafeLockerPass> lockerPasses;

    public StudyCafeLockerPasses(List<StudyCafeLockerPass> lockerPasses) {
        this.lockerPasses = lockerPasses;
    }

    public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> lockerPass) {
        return new StudyCafeLockerPasses(lockerPass);
    }

    public Optional<StudyCafeLockerPass> findLockerPassBy(StudyCafeSeatPass selectedPass) {
        return lockerPasses.stream()
            .filter(selectedPass::isSameDurationType)
            .findFirst();
    }

}
