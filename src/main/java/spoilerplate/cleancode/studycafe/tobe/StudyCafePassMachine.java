package spoilerplate.cleancode.studycafe.tobe;

import spoilerplate.cleancode.studycafe.tobe.exception.AppException;
import spoilerplate.cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import spoilerplate.cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import spoilerplate.cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import spoilerplate.cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import spoilerplate.cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import spoilerplate.cleancode.studycafe.tobe.model.pass.StudyCafeSeatPasses;
import spoilerplate.cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import spoilerplate.cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedSeatPass = selectSeatPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedSeatPass);
            StudyCafePassOrder passOrder = StudyCafePassOrder.of(
                selectedSeatPass,
                optionalLockerPass.orElse(null)
            );

            ioHandler.showPassOrderSummary(passOrder);
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass selectSeatPass() {
        StudyCafePassType studyCafePassType = ioHandler.askPassTypeSelecting();

        List<StudyCafeSeatPass> seatPassCandidates = findPassCandidatesBy(studyCafePassType);
        return ioHandler.askPassSelecting(seatPassCandidates);
    }

    private List<StudyCafeSeatPass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        StudyCafeSeatPasses allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.findPassBy(studyCafePassType);
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate.isPresent()) {
            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();

            boolean isLockerSelected = ioHandler.askLockerPass(lockerPass);
            if (isLockerSelected) {
                return Optional.of(lockerPass);
            }
        }
        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidateBy(StudyCafeSeatPass selectedPass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();
        return allLockerPasses.findLockerPassBy(selectedPass);
    }

}
