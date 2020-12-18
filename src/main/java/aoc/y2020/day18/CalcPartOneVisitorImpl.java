package aoc.y2020.day18;

import aoc.y2020.day18.parser.CalcPartOneParser;
import aoc.y2020.day18.parser.CalcPartOneVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class CalcPartOneVisitorImpl implements CalcPartOneVisitor {

    @Override
    public Object visitStart(CalcPartOneParser.StartContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitNumber(CalcPartOneParser.NumberContext ctx) {
        return Long.parseLong(ctx.NUMBER().toString());
    }

    @Override
    public Object visitOpExpr(CalcPartOneParser.OpExprContext ctx) {
        var op = ctx.op().getText();
        var left = Long.parseLong(visit(ctx.expr(0)).toString());
        var right = Long.parseLong(visit(ctx.expr(1)).toString());
        if (op.equals("+")) {
            return left + right;
        } else if (op.equals("*")) {
            return left * right;
        } else {
            return null;
        }
    }

    @Override
    public Object visitParenExpr(CalcPartOneParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitOp(CalcPartOneParser.OpContext ctx) {
        return null;
    }

    @Override
    public Object visit(ParseTree tree) {
        if (tree instanceof CalcPartOneParser.NumberContext) {
            return visitNumber((CalcPartOneParser.NumberContext) tree);
        } else if (tree instanceof CalcPartOneParser.ParenExprContext) {
            return visitParenExpr((CalcPartOneParser.ParenExprContext) tree);
        } else if (tree instanceof CalcPartOneParser.OpExprContext) {
            return visitOpExpr((CalcPartOneParser.OpExprContext) tree);
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
