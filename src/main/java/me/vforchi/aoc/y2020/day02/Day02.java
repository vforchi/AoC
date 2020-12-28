package me.vforchi.aoc.y2020.day02;

import me.vforchi.aoc.Day;

import java.util.function.Predicate;

public class Day02 extends Day {

    @Override
    public Object partOne() {
        return validPasswords(PasswordAndPolicy::isValid1);
    }

    @Override
    public Object partTwo() {
        return validPasswords(PasswordAndPolicy::isValid2);
    }

    private Long validPasswords(Predicate<PasswordAndPolicy> validator) {
        return input.stream()
                .map(PasswordAndPolicy::fromString)
                .filter(validator)
                .count();
    }
    
}
