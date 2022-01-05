package me.vforchi.aoc.y2019.day16;

import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Day16 extends Day {

    private Integer[] password;

    @Override
    public void setup(String path) {
        super.setup(path);
        password = Arrays.stream(input.get(0).split(""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

    @Override
    public Object partOne() {
        var transformed = Stream.iterate(password, this::transform)
                .skip(100)
                .findFirst()
                .orElseThrow();
        return Arrays.stream(transformed)
                .limit(8)
                .map(Object::toString)
                .collect(joining());
    }

    private Integer[] transform(Integer[] password) {
        var transformed = new Integer[password.length];
        for (int i = 1; i <= password.length; i++) {
            int digit = 0;
            for (int j = 0; j < i; j++) {
                for (int k = i - 1; k + j < password.length; k += i * 4) {
                    digit += password[k + j];
                    if (k + j + 2 * i < password.length) {
                        digit -= password[k + j + 2 * i];
                    }
                }
            }
            transformed[i - 1] = Math.abs(digit % 10);
        }
        return transformed;
    }

    @Override
    public Object partTwo() {
        var offset = Integer.parseInt(input.get(0).substring(0, 7));
        var longPassword = IntStream.range(0, 10000)
                .boxed()
                .flatMap(i -> Arrays.stream(password))
                .skip(offset)
                .toArray(Integer[]::new);
        var transformed = Stream.iterate(longPassword, this::transformTail)
                .skip(100)
                .findFirst()
                .orElseThrow();
        return Arrays.stream(transformed)
                .limit(8)
                .map(Object::toString)
                .collect(joining());
    }

    // This works only because the offset is larger than half the length of the number
    private Integer[] transformTail(Integer[] password) {
        var transformed = new Integer[password.length];
        int sum = 0;
        for (int i = password.length - 1; i >= 0; i--) {
            sum += password[i];
            transformed[i] = sum % 10;
        }
        return transformed;
    }

}
