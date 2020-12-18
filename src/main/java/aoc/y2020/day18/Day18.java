package aoc.y2020.day18;

import aoc.Day;
import aoc.parser.Day18Lexer;
import aoc.parser.Day18Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;

import java.io.ByteArrayInputStream;

public class Day18 extends Day {

    @Override
    public Object partOne() {
        return solve(6, false);
    }

    @Override
    public Object partTwo() {
        return solve(6, true);
    }

    private void parse() {
//
//        input.stream()
//                .map()

        var markupLexer = new Day18Lexer(new UnbufferedCharStream(new ByteArrayInputStream("".getBytes())));
        var commonTokenStream = new CommonTokenStream(markupLexer);
        var parser = new Day18Parser(commonTokenStream);
        var visitor = new Day18Visitor();
        visitor.visit(parser.file());
    }
}