package spoilerplate.cleancode.studycafe.tobe.io;

import spoilerplate.cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePass;
import spoilerplate.cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafeIOHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void showWelcomeMessage() {
        outputHandler.showWelcomeMessage();
    }

    public void showAnnouncement() {
        outputHandler.showAnnouncement();
    }

    public StudyCafePassType askPassTypeSelecting() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    public StudyCafePass askPassSelecting(List<StudyCafePass> passCandidates) {
        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    public void showOrderSummary(StudyCafePass selectedPass) {
        outputHandler.showOrderSummary(selectedPass);
    }

    public void showOrderSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass) {
        outputHandler.showOrderSummary(selectedPass, lockerPass);
    }

    public void showSimpleMessage(String message) {
        outputHandler.showSimpleMessage(message);
    }

    public boolean askLockerPass(StudyCafeLockerPass lockerPassCandidate) {
        outputHandler.askLockerPass(lockerPassCandidate);
        return inputHandler.getLockerSelection();
    }

}
