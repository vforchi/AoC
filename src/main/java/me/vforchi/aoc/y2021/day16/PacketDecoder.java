package me.vforchi.aoc.y2021.day16;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class PacketDecoder {

    private final String instructions;
    private int pos = 0;
    @Getter
    private int versions = 0;
    @Getter
    private long result = 0;
    
    public void parse() {
        reset();
        while (pos < instructions.length()) {
            result = parsePacket();
            pos += 8 - pos % 8;
        }
    }

    public void reset() {
        pos = 0;
        versions = 0;
        result = 0;
    }

    private long parsePacket() {
        parseVersion();
        var id = parseId();
        if (id == 4) {
           return parseLiteral();
        } else {
            var packets = parseSubPackets();
            return switch (id) {
                case 0 -> packets.stream().reduce(Long::sum).orElseThrow();
                case 1 -> packets.stream().reduce((a, b) -> a * b).orElseThrow();
                case 2 -> packets.stream().min(Long::compareTo).orElseThrow();
                case 3 -> packets.stream().max(Long::compareTo).orElseThrow();
                case 5 -> packets.get(0) > packets.get(1) ? 1 : 0;
                case 6 -> packets.get(0) < packets.get(1) ? 1 : 0;
                case 7 -> Objects.equals(packets.get(0), packets.get(1)) ? 1 : 0;
                default -> throw new RuntimeException();
            };
        }
    }

    private int parseVersion() {
        var version = parseBinaryInteger(3).intValue();
        versions += version;
        return version;
    }

    private int parseId() {
        return parseBinaryInteger(3).intValue();
    }

    private long parseLiteral() {
        var number = new StringBuilder();
        do {
            number.append(instructions, pos + 1, pos + 5);
            pos += 5;
        } while (instructions.charAt(pos - 5) == '1');
        return Long.parseLong(number.toString(), 2);
    }

    private List<Long> parseSubPackets() {
        var packets = new ArrayList<Long>();
        if (parseLengthTypeId() == '0') {
            long size = parseBinaryInteger(15);
            long goal = pos + size;
            while (pos < goal) {
                packets.add(parsePacket());
            }
        } else {
            long numPackets = parseBinaryInteger(11);
            for (int i = 0 ; i < numPackets; i++) {
                packets.add(parsePacket());
            }
        }
        return packets;
    }

    private char parseLengthTypeId() {
        pos += 1;
        return instructions.charAt(pos - 1);
    }

    private Long parseBinaryInteger(int size) {
        pos += size;
        return Long.parseLong(instructions.substring(pos - size, pos), 2);
    }

    public static PacketDecoder fromText(String input) {
        return new PacketDecoder(hexToBin(input));
    }
    
    private static String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }
}
