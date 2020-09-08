package eu.timhuebert.ipv4;

import lombok.Data;

@Data
public class IPv4 {

    private int version = 4;
    private int IHL = 6;
    private int TOS;
    private int totalLength = 6;
    private int identification = 0;
    private int flags = 0;
    private int fragmentOffset = 0;
    private int TTL;
    private int protocol = 0;
    private int headerChecksum = 0;
    private Address sourceAddress;
    private Address destinationAddress;

    public static IPv4 taskC(String binary) {
        String[] parts = binary.split(" ");
        IPv4 iPv4 = new IPv4();

        if (parts.length < 12) return iPv4;

        iPv4.setVersion(Integer.parseInt(parts[0], 2));
        iPv4.setIHL(Integer.parseInt(parts[1], 2));
        iPv4.setTOS(Integer.parseInt(parts[2], 2));
        iPv4.setTotalLength(Integer.parseInt(parts[3], 2));
        iPv4.setIdentification(Integer.parseInt(parts[4], 2));
        iPv4.setFlags(Integer.parseInt(parts[5], 2));
        iPv4.setFragmentOffset(Integer.parseInt(parts[6], 2));
        iPv4.setTTL(Integer.parseInt(parts[7], 2));
        iPv4.setProtocol(Integer.parseInt(parts[8], 2));
        iPv4.setHeaderChecksum(Integer.parseInt(parts[9], 2));
        iPv4.setSourceAddress(extractAddress(parts[10]));
        iPv4.setDestinationAddress(extractAddress(parts[11]));

        return iPv4;
    }

    private static Address extractAddress(String binary) {
        String[] parts = binary.split("(?<=\\G........)");
        StringBuilder ip = new StringBuilder();

        for (String part : parts) {
            int part1 = Integer.parseInt(part, 2);
            ip.append(part1).append(".");
        }

        ip.setLength(ip.length() - 1);
        return new Address(ip.toString());
    }

    public String taskA() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(version).append("-");
        stringBuilder.append(IHL).append("-");
        stringBuilder.append(TOS).append("-");
        stringBuilder.append(totalLength).append("-");
        stringBuilder.append(identification).append("-");

        StringBuilder flags = new StringBuilder(String.valueOf(this.flags));
        while (flags.length() < 3) {
            flags.insert(0, "0");
        }

        stringBuilder.append(flags.toString()).append("-");
        stringBuilder.append(fragmentOffset).append("-");
        stringBuilder.append(TTL).append("-");
        stringBuilder.append(protocol).append("-");
        stringBuilder.append(headerChecksum).append("-");
        stringBuilder.append(sourceAddress.toString()).append("-");
        stringBuilder.append(destinationAddress.toString());

        return stringBuilder.toString();
    }

    public String taskB() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(toBinaryString(version, 4)).append(" ");
        stringBuilder.append(toBinaryString(IHL, 4)).append(" ");
        stringBuilder.append(toBinaryString(TOS, 8)).append(" ");
        stringBuilder.append(toBinaryString(totalLength, 16)).append(" ");
        stringBuilder.append(toBinaryString(identification, 16)).append(" ");
        stringBuilder.append(toBinaryString(flags, 3)).append(" ");
        stringBuilder.append(toBinaryString(fragmentOffset, 13)).append(" ");
        stringBuilder.append(toBinaryString(TTL, 8)).append(" ");
        stringBuilder.append(toBinaryString(protocol, 8)).append(" ");
        stringBuilder.append(toBinaryString(headerChecksum, 16)).append(" ");

        stringBuilder.append(sourceAddress.toBinaryString()).append(" ");
        stringBuilder.append(destinationAddress.toBinaryString());

        return stringBuilder.toString();
    }

    private String toBinaryString(int i, int bits) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(i));
        while (binary.length() < bits) {
            binary.insert(0, "0");
        }

        return binary.toString();
    }

    @Override
    public String toString() {
        return taskA();
    }
}
