package manager.instruments;

import java.util.Scanner;

/**
 * Класс-обёртка Scanner для реализации дополнительного условия ввода данных.
 * @autor komyak9
 */
public class ScannerWrapper {
    private Scanner scanner;

    public ScannerWrapper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readData() {
        String line = scanner.nextLine();
        if (line.equals(""))
            line = null;
        return line;
    }
}
