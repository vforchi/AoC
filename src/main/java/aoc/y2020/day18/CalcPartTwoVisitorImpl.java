package aoc.y2020.day18;

import aoc.y2020.day18.parser.CalcPartTwoParser;
import aoc.y2020.day18.parser.CalcPartTwoVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class CalcPartTwoVisitorImpl implements CalcPartTwoVisitor {

    @Override
    public Object visitStart(CalcPartTwoParser.StartContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitNumber(CalcPartTwoParser.NumberContext ctx) {
        return Long.parseLong(ctx.NUMBER().toString());
    }

    @Override
    public Object visitSumExpr(CalcPartTwoParser.SumExprContext ctx) {
        var left = Long.parseLong(visit(ctx.expr(0)).toString());
        var right = Long.parseLong(visit(ctx.expr(1)).toString());
        return left + right;
    }

    @Override
    public Object visitMulExpr(CalcPartTwoParser.MulExprContext ctx) {
        var left = Long.parseLong(visit(ctx.expr(0)).toString());
        var right = Long.parseLong(visit(ctx.expr(1)).toString());
        return left * right;
    }

    @Override
    public Object visitParenExpr(CalcPartTwoParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visit(ParseTree tree) {
        if (tree instanceof CalcPartTwoParser.NumberContext) {
            return visitNumber((CalcPartTwoParser.NumberContext) tree);
        } else if (tree instanceof CalcPartTwoParser.ParenExprContext) {
            return visitParenExpr((CalcPartTwoParser.ParenExprContext) tree);
        } else if (tree instanceof CalcPartTwoParser.SumExprContext) {
            return visitSumExpr((CalcPartTwoParser.SumExprContext) tree);
        } else if (tree instanceof CalcPartTwoParser.MulExprContext) {
            return visitMulExpr((CalcPartTwoParser.MulExprContext) tree);
        }
        return null;
    }

    @Override
    public Object visitChildren(RuleNode node) {
        return null;
    }

    @Override
    public Object visitTerminal(TerminalNode node) {
        return null;
    }

    @Override
    public Object visitErrorNode(ErrorNode node) {
        return null;
    }
}
