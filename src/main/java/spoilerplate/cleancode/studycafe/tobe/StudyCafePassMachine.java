package spoilerplate.cleancode.studycafe.tobe;

import spoilerplate.cleancode.studycafe.tobe.exception.AppException;
import spoilerplate.cleancode.studycafe.tobe.io.InputHandler;
import spoilerplate.cleancode.studycafe.tobe.io.OutputHandler;
import spoilerplate.cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePass;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassSelectingUserAction();

            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
            List<StudyCafePass> passCandidates = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
            outputHandler.showPassListForSelection(passCandidates);
            StudyCafePass selectedPass = inputHandler.getSelectPass(passCandidates);

            Optional<StudyCafeLockerPass> optionalLockerPass = getLockerPass(selectedPass);
            optionalLockerPass.ifPresentOrElse(
                lockerPass -> outputHandler.showOrderSummary(selectedPass, lockerPass),
                () -> outputHandler.showOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다.");
        }
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
            .filter(selectedPass::isSameDurationType)
            .findFirst()
            .orElse(null);

        if (lockerPass != null) {
            outputHandler.askLockerPass(lockerPass);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return Optional.of(lockerPass);
            }
        }

        return Optional.empty();
    }

}
