package eu.timhuebert.ipv4;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    private static Address promptAddress(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            Address address = new Address(scanner.nextLine());

            if (address.isValid()) {
                return address;
            }
        }
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        IPv4 iPv4 = new IPv4();

        iPv4.setTOS(prompt(scanner, "TOS > ", 0, 255));
        iPv4.setTTL(prompt(scanner, "TTL > ", 0, 255));
        iPv4.setSourceAddress(promptAddress(scanner, "Source Address > "));
        iPv4.setDestinationAddress(promptAddress(scanner, "Destination Address > "));

        String taskA = iPv4.taskA();
        String taskB = iPv4.taskB();
        IPv4 taskC = IPv4.taskC(taskB);

        System.out.println(taskA);
        System.out.println(taskB);
        System.out.println(taskC);

        scanner.close();
    }

    private int prompt(Scanner scanner, String message, int from, int to) {
        while (true) {
            System.out.print(message);
            String valStr = scanner.nextLine();

            try {
                int val = Integer.parseInt(valStr);

                if (val >= from && val <= to) return val;
            } catch (NumberFormatException ignored) {
            }
        }
    }
}
