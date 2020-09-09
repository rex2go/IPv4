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

        for (String part : parts) ip.append(Integer.parseInt(part, 2)).append(".");

        ip.setLength(ip.length() - 1);
        return new Address(ip.toString());
    }

    public String taskA() {
        return String.format(
                "%s-%s-%s-%s-%s-%s-%s-%s-%s-%s-%s-%s",
                version,
                IHL,
                TOS,
                totalLength,
                identification,
                flags,
                fragmentOffset,
                TTL,
                protocol,
                headerChecksum,
                sourceAddress.toString(),
                destinationAddress.toString()
        );
    }

    public String taskB() {
        return String.format(
                "%s %s %s %s %s %s %s %s %s %s %s %s",
                toBinaryString(version, 4),
                toBinaryString(IHL, 4),
                toBinaryString(TOS, 8),
                toBinaryString(totalLength, 16),
                toBinaryString(identification, 16),
                toBinaryString(flags, 3),
                toBinaryString(fragmentOffset, 13),
                toBinaryString(TTL, 8),
                toBinaryString(protocol, 8),
                toBinaryString(headerChecksum, 16),
                sourceAddress.toBinaryString(),
                destinationAddress.toBinaryString()
        );
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
