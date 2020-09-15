package eu.timhuebert.ipv4;

import lombok.Data;

@Data
public class IPv4 {

    private int version = 4;
    private int IHL = 0;
    private int TOS;
    private int totalLength = 0;
    private int identification = 0;
    private int flags = 0b000;
    private int fragmentOffset = 0;
    private int TTL;
    private int protocol = 0;
    private int headerChecksum = 0;
    private Address sourceAddress;
    private Address destinationAddress;

    public void calculateHeaderLength() {
        IHL = toBinaryString().length() / 32; // no data

        System.out.println("IHL: " + IHL);
    }

    public void calculateLength() {
        totalLength = toBinaryString().length() / 32;

        System.out.println("Total Length: " + totalLength);
    }

    public void calculateChecksum() {
        String binary = toBinaryString();
        String[] parts = binary.split("(?<=\\G................)"); // 16 bit blocks
        int sum = 0;

        for (String part : parts) {
            sum += Integer.parseInt(part, 2); // sum up all blocks
        }

        String sumStr = Integer.toString(sum, 2); // convert to binary
        sumStr = sumStr.replace('0', '2').replace('1', '0').replace('2', '1'); // flip

        headerChecksum = Integer.parseInt(sumStr, 2); // convert to dec

        System.out.println("Header Checksum: " + headerChecksum);
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

    public IPv4 taskC(String binary) {
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

    private Address extractAddress(String binary) {
        String[] parts = binary.split("(?<=\\G........)");
        StringBuilder ip = new StringBuilder();

        for (String part : parts) ip.append(Integer.parseInt(part, 2)).append(".");

        ip.setLength(ip.length() - 1);
        return new Address(ip.toString());
    }

    private String toBinaryString(int i, int bits) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(i));

        while (binary.length() < bits) binary.insert(0, "0");

        return binary.toString();
    }

    public String toBinaryString() {
        String binary = String.format(
                "%s%s%s%s%s%s%s%s%s%s%s%s",
                toBinaryString(version, 4),
                toBinaryString(IHL, 4),
                toBinaryString(TOS, 8),
                toBinaryString(totalLength, 16),
                toBinaryString(identification, 16),
                toBinaryString(flags, 3),
                toBinaryString(fragmentOffset, 13),
                toBinaryString(TTL, 8),
                toBinaryString(protocol, 8),
                toBinaryString(0, 16),
                sourceAddress.toBinaryString(),
                destinationAddress.toBinaryString()
        );

        return binary;
    }

    @Override
    public String toString() {
        return taskA();
    }
}
