package me.vforchi.aoc.y2019.day14;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.Utils;

import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class Day14 extends Day {

    private record Component(String chemical, long amount) {
        public static Component fromText(String text) {
            var t = text.split(" ");
            return new Component(t[1], Long.parseLong(t[0]));
        }
        public static List<Component> manyFromText(String text) {
            return Arrays.stream(text.split(", "))
                    .map(Component::fromText)
                    .toList();
        }
    }

    private record Reaction(Component product, List<Component> reagents) {
        public static Reaction fromText(String text) {
            var tokens = text.split(" => ");
            return new Reaction(Component.fromText(tokens[1]), Component.manyFromText(tokens[0]));
        }
    }

    private Map<String, Reaction> reactions;

    @Override
    public void setup(String path) {
        super.setup(path);
        reactions = input.stream()
                .map(Reaction::fromText)
                .collect(toMap(r -> r.product.chemical, identity()));
    }

    @Override
    public Object partOne() {
        return getOreForFuel(1);
    }

    @Override
    public Object partTwo() {
        return Utils.binarySearch(0, 1000000000000L, 1000000000000L, this::getOreForFuel);
    }

    private long getOreForFuel(long amount) {
        long ore = 0;
        SortedMap<String, Long> needed = new TreeMap<>();
        Map<String, Long> extra = new HashMap<>();
        needed.put("FUEL", amount);
        while (needed.size() > 0) {
            var chemical = needed.firstKey();
            var chemicalNeeded = needed.get(chemical);
            if (extra.getOrDefault(chemical, 0L) > chemicalNeeded) {
                increase(chemical, -chemicalNeeded, extra);
                needed.remove(chemical);
            } else {
                var remaining = chemicalNeeded - extra.getOrDefault(chemical, 0L);
                extra.remove(chemical);
                needed.remove(chemical);

                var reaction = reactions.get(chemical);
                var numReaction = (long) Math.ceil(((double) remaining) / reaction.product.amount);
                var extraProduct = reaction.product.amount * numReaction - remaining;

                increase(reaction.product.chemical, extraProduct, extra);
                for (var reagent: reaction.reagents) {
                    if (reagent.chemical.equals("ORE")) {
                        ore += reagent.amount * numReaction;
                    } else {
                        increase(reagent.chemical, reagent.amount * numReaction, needed);
                    }
                }
            }
        }
        return ore;
    }

    private void increase(String chemical, long amount, Map<String, Long> map) {
        map.compute(chemical, (k, v) -> amount + ((v == null) ? 0 : v));
    }

}
