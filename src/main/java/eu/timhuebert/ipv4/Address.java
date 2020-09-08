package eu.timhuebert.ipv4;

import lombok.Getter;
import lombok.Setter;

public class Address {

    @Getter
    @Setter
    private String address;

    public Address(String ip) {
        this.address = ip;
    }

    public boolean isValid() {
        return address.matches("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}" +
                "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    }

    public String toBinaryString() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] parts = address.split("\\.");

        for (String part : parts) {
            StringBuilder binary = new StringBuilder(Integer.toBinaryString(Integer.parseInt(part)));

            while (binary.length() < 8) {
                binary.insert(0, "0");
            }

            stringBuilder.append(binary);
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return address;
    }
}
