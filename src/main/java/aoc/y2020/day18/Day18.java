package aoc.y2020.day18;

import aoc.Day;
import aoc.y2020.day18.parser.CalcPartOneLexer;
import aoc.y2020.day18.parser.CalcPartOneParser;
import aoc.y2020.day18.parser.CalcPartTwoLexer;
import aoc.y2020.day18.parser.CalcPartTwoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Day18 extends Day {

    @Override
    public Object partOne() {
        return input.stream()
                .mapToLong(this::evaluateOne)
                .sum();
    }

    @Override
    public Object partTwo() {
        return input.stream()
                .mapToLong(this::evaluateTwo)
                .sum();
    }

    private long evaluateOne(String expr) {
        var markupLexer = new CalcPartOneLexer(CharStreams.fromString(expr));
        var commonTokenStream = new CommonTokenStream(markupLexer);
        var parser = new CalcPartOneParser(commonTokenStream);
        var visitor = new CalcPartOneVisitorImpl();
        return Long.parseLong(visitor.visitStart(parser.start()).toString());
    }

    private long evaluateTwo(String expr) {
        var markupLexer = new CalcPartTwoLexer(CharStreams.fromString(expr));
        var commonTokenStream = new CommonTokenStream(markupLexer);
        var parser = new CalcPartTwoParser(commonTokenStream);
        var visitor = new CalcPartTwoVisitorImpl();
        return Long.parseLong(visitor.visitStart(parser.start()).toString());
    }

}