package spoilerplate.cleancode.studycafe.tobe;

import spoilerplate.cleancode.studycafe.tobe.exception.AppException;
import spoilerplate.cleancode.studycafe.tobe.io.InputHandler;
import spoilerplate.cleancode.studycafe.tobe.io.OutputHandler;
import spoilerplate.cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafeLockerOption;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePass;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePassType;

import javax.swing.text.html.Option;
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
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
            outputHandler.showPassListForSelection(passCandidates);
            StudyCafePass selectedPass = inputHandler.getSelectPass(passCandidates);

            Optional<StudyCafeLockerOption> optionalLockerOption = getLockerOption(selectedPass);
            optionalLockerOption.ifPresentOrElse(
                lockerOption -> outputHandler.showOrderSummary(selectedPass, lockerOption),
                () -> outputHandler.showOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다.");
        }
    }

    private Optional<StudyCafeLockerOption> getLockerOption(StudyCafePass selectedPass) {
        if (selectedPass.getPassType() != StudyCafePassType.FIXED) {
            return Optional.empty();
        }

        List<StudyCafeLockerOption> lockerOptions = studyCafeFileHandler.readLockerOptions();
        StudyCafeLockerOption lockerOption = lockerOptions.stream()
            .filter(option ->
                option.getPassType() == selectedPass.getPassType()
                    && option.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);

        if (lockerOption != null) {
            outputHandler.askLockerOption(lockerOption);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return Optional.of(lockerOption);
            }
        }

        return Optional.empty();
    }

}
