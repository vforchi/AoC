package me.vforchi.aoc.y2015.day21;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.vforchi.aoc.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day21 extends Day {

    public enum Type { Weapon, Armor, Ring }

    private record Item(Type type, String name, int cost, int damage, int armor) {}

    @AllArgsConstructor
    @RequiredArgsConstructor
    private static class Character {
        private final String name;
        private final int hp;

        private int damage;
        private int armor;
        @Getter private int cost;

        public Character equip(List<Item> items) {
            this.damage = items.stream()
                    .mapToInt(Item::damage)
                    .sum();
            this.armor = items.stream()
                    .mapToInt(Item::armor)
                    .sum();
            this.cost = items.stream()
                    .mapToInt(Item::cost)
                    .sum();
            return this;
        }
    }

    private List<Item> weapons = List.of(
            new Item(Type.Weapon, "Dagger", 8, 4, 0),
            new Item(Type.Weapon, "Shortsword", 10, 5, 0),
            new Item(Type.Weapon, "Warhammer", 25, 6, 0),
            new Item(Type.Weapon, "Longsword", 40, 7, 0),
            new Item(Type.Weapon, "Greataxe", 74, 8, 0)
    );

    private List<Item> armors = List.of(
            new Item(Type.Armor, "Skin", 0, 0, 0),
            new Item(Type.Armor, "Leather", 13, 0, 1),
            new Item(Type.Armor, "Chainmail", 31, 0, 2),
            new Item(Type.Armor, "Splintmail", 53, 0, 3),
            new Item(Type.Armor, "Bandedmail", 75, 0, 4),
            new Item(Type.Armor, "Platemail", 102, 0, 5)
    );

    private Set<Item> rings = Set.of(
            new Item(Type.Ring, "Finger 1", 0, 0, 0),
            new Item(Type.Ring, "Finger 2", 0, 0, 0),
            new Item(Type.Ring, "Damage +1", 25, 1, 0),
            new Item(Type.Ring, "Damage +2", 50, 2, 0),
            new Item(Type.Ring, "Damage +3", 100, 3, 0),
            new Item(Type.Ring, "Defense +1", 20, 0, 1),
            new Item(Type.Ring, "Defense +2", 40, 0, 2),
            new Item(Type.Ring, "Defense +3", 80, 0, 3)
    );

    private Character boss = new Character("Boss", 104, 8, 1, 0);

    @Override
    public Object partOne() {
        return equipAll()
                .filter(this::bossLoses)
                .mapToInt(Character::getCost)
                .min()
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        return equipAll()
                .filter(this::bossWins)
                .mapToInt(Character::getCost)
                .max()
                .orElseThrow();
    }

    private Stream<Character> equipAll() {
        var twoRings = Sets.combinations(rings, 2).stream()
                .map(ArrayList::new)
                .toList();
        return Lists.cartesianProduct(armors, weapons, twoRings).stream()
                .map(l -> l.stream()
                        .flatMap(e -> (e instanceof List l1) ? l1.stream() : Stream.of(e))
                        .toList())
                .map(this::buildCharacter);
    }

    private Character buildCharacter(List<Item> items) {
        var me = new Character("Me", 100);
        return me.equip(items);
    }

    public boolean bossLoses(Character character) {
        var hp1 = character.hp;
        var hp2 = boss.hp;
        while (hp1 > 0) {
            hp2 -= Math.max(character.damage - boss.armor, 1);
            if (hp2 <= 0) {
                return true;
            }
            hp1 -= Math.max(boss.damage - character.armor, 1);
        }
        return false;
    }

    public boolean bossWins(Character character) {
        return !bossLoses(character);
    }

}
