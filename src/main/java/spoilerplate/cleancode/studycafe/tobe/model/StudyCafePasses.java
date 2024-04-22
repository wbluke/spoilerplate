package spoilerplate.cleancode.studycafe.tobe.model;

import java.util.List;

public class StudyCafePasses {

    private final List<StudyCafeSeatPass> passes;

    public StudyCafePasses(List<StudyCafeSeatPass> passes) {
        this.passes = passes;
    }

    public static StudyCafePasses of(List<StudyCafeSeatPass> passes) {
        return new StudyCafePasses(passes);
    }

    public List<StudyCafeSeatPass> findPassBy(StudyCafePassType studyCafePassType) {
        return passes.stream()
            .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
            .toList();
    }

}
