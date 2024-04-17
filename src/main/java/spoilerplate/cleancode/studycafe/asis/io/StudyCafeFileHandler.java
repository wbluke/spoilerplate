package spoilerplate.cleancode.studycafe.asis.io;

import spoilerplate.cleancode.studycafe.asis.model.StudyCafeLockerOption;
import spoilerplate.cleancode.studycafe.asis.model.StudyCafePass;
import spoilerplate.cleancode.studycafe.asis.model.StudyCafePassType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler {

    public List<StudyCafePass> readStudyCafePasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/pass-list.csv"));
            List<StudyCafePass> studyCafePasses = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);
                double discountRate = Double.parseDouble(values[3]);

                StudyCafePass studyCafePass = StudyCafePass.of(studyCafePassType, duration, price, discountRate);
                studyCafePasses.add(studyCafePass);
            }

            return studyCafePasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    public List<StudyCafeLockerOption> readLockerOptions() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/locker.csv"));
            List<StudyCafeLockerOption> lockerOptions = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);

                StudyCafeLockerOption lockerOption = StudyCafeLockerOption.of(studyCafePassType, duration, price);
                lockerOptions.add(lockerOption);
            }

            return lockerOptions;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

}
