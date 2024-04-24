package spoilerplate.cleancode.studycafe.tobe;

import spoilerplate.cleancode.studycafe.tobe.io.provider.LockerPassFileReader;
import spoilerplate.cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import spoilerplate.cleancode.studycafe.tobe.model.provider.LockerPassProvider;
import spoilerplate.cleancode.studycafe.tobe.model.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvider seatPassProvider = new SeatPassFileReader();
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(
            seatPassProvider,
            lockerPassProvider
        );
        studyCafePassMachine.run();
    }

}
