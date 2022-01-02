package me.vforchi.aoc.y2015.day19;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day19 extends Day {

    private List<Tuple2<String, String>> rules;
    private String molecule;

    @Override
    public void setup(String path) {
        super.setup(path);
        var parts = Utils.partitionByEmptyLine(input).toList();

        rules = parts.get(0).stream()
                .map(l -> l.split(" => "))
                .map(t -> Tuple.of(t[0], t[1]))
                .toList();

        molecule = parts.get(1).get(0);
    }

    @Override
    public Object partOne() {
        return rules.stream()
                .flatMap(r -> replaceOne(r, molecule))
                .distinct()
                .count();
    }

    private Stream<String> replaceOne(Tuple2<String, String> rule, String molecule) {
        var p = Pattern.compile(rule._1);
        var m = p.matcher(molecule);
        var replaced = new ArrayList<String>();
        while (m.find()) {
            replaced.add(molecule.substring(0, m.start(0)) + rule._2 + molecule.substring(m.end(0)));
        }
        return replaced.stream();
    }

    @Override
    public Object partTwo() {
        var p = Pattern.compile("[A-Z]");
        var m = p.matcher(molecule);

        var tokens = 0;
        while (m.find()) {
            tokens++;
        }
        var rn = StringUtils.countMatches(molecule, "Rn");
        var ar = StringUtils.countMatches(molecule, "Ar");
        var y = StringUtils.countMatches(molecule, "Y");

        return (tokens - rn - ar - 2 * y - 1);
    }

}
