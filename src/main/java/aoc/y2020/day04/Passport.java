package aoc.y2020.day04;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Passport {

    private static final Set<String> eyeColors = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    public Map<String, String> fields;

    public static Passport fromFields(List<String> fieldList) {
        var fields = fieldList.stream()
                .map(f -> f.split(":"))
                .collect(Collectors.toMap(
                        l -> l[0],
                        l -> l[1]
                ));
        return new Passport(fields);
    }

    public boolean isValid1() {
        return hasRequiredFields(fields.size());
    }

    public boolean isValid2() {
        long validFields = fields.entrySet().stream()
                .filter(f -> isFieldValid(f.getKey(), f.getValue()))
                .count();

        return hasRequiredFields((int) validFields);
    }

    public boolean hasRequiredFields(int numFields) {
        return numFields == 7 + (fields.containsKey("cid") ? 1 : 0);
    }

    private boolean isFieldValid(String field, String value) {
        try {
            return switch (field) {
                case "byr" -> isYear(value, 1920, 2002);
                case "iyr" -> isYear(value, 2010, 2020);
                case "eyr" -> isYear(value, 2020, 2030);
                case "hgt" -> isHeight(value);
                case "hcl" -> value.matches("#[0-9a-f]{6}");
                case "ecl" -> eyeColors.contains(value);
                case "pid" -> value.matches("[0-9]{9}");
                case "cid" -> true;
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isYear(String field, int min, int max) {
        var year = Integer.parseInt(field);
        return (field.length() == 4 && year >= min && year <= max);
    }

    private boolean isHeight(String field) {
        if (field.contains("cm")) {
            var h = Integer.parseInt(field.replaceFirst("cm", ""));
            return (h >= 150 && h <= 193);
        } else if (field.contains("in")) {
            var h = Integer.parseInt(field.replaceFirst("in", ""));
            return (h >= 59 && h <= 76);
        } else {
            return false;
        }
    }

}
