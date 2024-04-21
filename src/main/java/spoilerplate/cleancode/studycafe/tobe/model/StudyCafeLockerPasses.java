package spoilerplate.cleancode.studycafe.tobe.model;

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

    public Optional<StudyCafeLockerPass> findLockerPassBy(StudyCafePass selectedPass) {
        return lockerPasses.stream()
            .filter(selectedPass::isSameDurationType)
            .findFirst();
    }

}
