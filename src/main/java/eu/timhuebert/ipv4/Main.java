package eu.timhuebert.ipv4;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        IPv4 iPv4 = new IPv4();

        iPv4.setTOS(prompt(scanner, "TOS > ", 0, 255));
        iPv4.setTTL(prompt(scanner, "TTL > ", 0, 255));
        iPv4.setSourceAddress(promptAddress(scanner, "Source Address > "));
        iPv4.setDestinationAddress(promptAddress(scanner, "Destination Address > "));

        iPv4.calculateHeaderLength();
        iPv4.calculateLength();
        iPv4.calculateChecksum();

        String taskA = iPv4.taskA();
        String taskB = iPv4.taskB();
        IPv4 taskC = iPv4.taskC(taskB);

        System.out.println("Aufgabe A: " + taskA);
        System.out.println("Aufgabe B: " + taskB);
        System.out.println("Aufgabe C: " + taskC);

        scanner.close();
    }

    private int prompt(Scanner scanner, String message, int from, int to) {
        while (true) {
            System.out.print(message);
            String valStr = scanner.nextLine();

            try {
                int val = Integer.parseInt(valStr);

                if (val >= from && val <= to) return val;

                System.err.println("Ungültige Eingabe: Der Wert muss zwischen 0 und 255 liegen.");
            } catch (NumberFormatException exception) {
                System.err.println("Ungültige Eingabe: Der eingegebene Wert darf nur aus Zahlen bestehen.");
            }
        }
    }

    private Address promptAddress(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            Address address = new Address(scanner.nextLine());

            if (address.isValid()) return address;

            System.err.println("Ungültige Eingabe: Die IP-Adresse ist ungültig.");
        }
    }
}
