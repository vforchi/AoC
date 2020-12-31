package me.vforchi.aoc.y2020.day18;

import aoc.y2020.day18.parser.CalcPartOneBaseVisitor;
import aoc.y2020.day18.parser.CalcPartOneParser;

public class CalcPartOneVisitorImpl extends CalcPartOneBaseVisitor<Long> {

    @Override
    public Long visitNumber(CalcPartOneParser.NumberContext ctx) {
        return Long.parseLong(ctx.NUMBER().toString());
    }

    @Override
    public Long visitOpExpr(CalcPartOneParser.OpExprContext ctx) {
        var op = ctx.op().getText();
        var left = visit(ctx.expr(0));
        var right = visit(ctx.expr(1));
        if (op.equals("+")) {
            return left + right;
        } else if (op.equals("*")) {
            return left * right;
        } else {
            return null;
        }
    }

    @Override
    public Long visitParenExpr(CalcPartOneParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

}
