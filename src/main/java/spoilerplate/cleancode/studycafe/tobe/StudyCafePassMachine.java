package spoilerplate.cleancode.studycafe.tobe;

import spoilerplate.cleancode.studycafe.tobe.exception.AppException;
import spoilerplate.cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import spoilerplate.cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePass;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = getLockerPass(selectedPass);
            optionalLockerPass.ifPresentOrElse(
                lockerPass -> ioHandler.showOrderSummary(selectedPass, lockerPass),
                () -> ioHandler.showOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        StudyCafePassType studyCafePassType = ioHandler.askPassTypeSelecting();

        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> passCandidates = studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
            .toList();
        return ioHandler.askPassSelecting(passCandidates);
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPassCandidate = lockerPasses.stream()
            .filter(selectedPass::isSameDurationType)
            .findFirst()
            .orElse(null);

        if (lockerPassCandidate != null) {
            boolean isLockerSelected = ioHandler.askLockerPass(lockerPassCandidate);
            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

}
