package me.vforchi.aoc.y2019.day12;

import io.vavr.collection.Stream;
import me.vforchi.aoc.Day;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.math3.util.ArithmeticUtils.lcm;

public class Day12 extends Day {

    private static class Moon {
        List<Integer> position;
        List<Integer> velocity;

        public Moon(List<Integer> position) {
            this.position = position;
            this.velocity = new ArrayList<>() {{ add(0); add(0); add(0); }};
        }

        @Override
        public String toString() {
            return String.format("pos=<x=%3d, y=%3d, z=%3d>, vel=<x=%3d, y=%3d, z=%3d>",
                    position.get(0), position.get(1), position.get(2),
                    velocity.get(0), velocity.get(1), velocity.get(2)
            );
        }

        public static Moon fromText(String text) {
            var tokens = text.split("[=,>]");
            return new Moon(List.of(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[5])));
        }

        public Moon applyGravity(List<Moon> moons) {
            moons.forEach(this::applyGravity);
            return this;
        }

        private Moon applyGravity(Moon moon) {
            var corrections = Stream.ofAll(position)
                    .zip(moon.position)
                    .map(t -> (int) Math.signum(t._2 - t._1))
                    .toJavaList();
           velocity = Stream.ofAll(velocity)
                    .zip(corrections)
                    .map(t -> t._1 + t._2)
                    .toJavaList();
           return this;
        }

        public Moon applyVelocity() {
            position = Stream.ofAll(position)
                    .zip(velocity)
                    .map(t -> t._1 + t._2)
                    .toJavaList();
            return this;
        }

        public int totalEnergy() {
            return potentialEnergy() * kineticEnergy();
        }

        private int potentialEnergy() {
            return position.stream()
                    .mapToInt(Math::abs)
                    .sum();
        }

        private int kineticEnergy() {
            return velocity.stream()
                    .mapToInt(Math::abs)
                    .sum();
        }
    }

    private List<Moon> moons;

    @Override
    public void setup(String path) {
        super.setup(path);
        moons = input.stream()
                .map(Moon::fromText)
                .toList();
    }

    @Override
    public Object partOne() {
        return Stream.iterate(moons, this::simulateMotion)
                .drop(1000)
                .get().stream()
                .mapToInt(Moon::totalEnergy)
                .sum();
    }

    private List<Moon> simulateMotion(List<Moon> moons) {
        moons.forEach(m -> m.applyGravity(moons));
        moons.forEach(Moon::applyVelocity);
        return moons;
    }

    @Override
    public Object partTwo() {
        return period(moons);
    }

    private long period(List<Moon> moons) {
        // not pretty, but faster than using Lists
        var initialConditionX = getStatus(moons, 0);
        var initialConditionY = getStatus(moons, 1);
        var initialConditionZ = getStatus(moons, 2);
        long foundX = 0, foundY = 0, foundZ = 0;

        long i = 0;
        while (foundX * foundY * foundZ == 0) {
            simulateMotion(moons);
            i++;
            if (getStatus(moons, 0).equals(initialConditionX)) {
                foundX = i;
            }
            if (getStatus(moons, 1).equals(initialConditionY)) {
                foundY = i;
            }
            if (getStatus(moons, 2).equals(initialConditionZ)) {
                foundZ = i;
            }
        }
        return lcm(lcm(foundX, foundY), foundZ);
    }

    private static List<Integer> getStatus(List<Moon> moons, int coord) {
        return moons.stream()
                .flatMap(m -> List.of(m.position.get(coord), m.velocity.get(coord)).stream())
                .toList();
    }
}
