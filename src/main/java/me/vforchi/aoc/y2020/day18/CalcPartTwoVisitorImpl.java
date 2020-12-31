package me.vforchi.aoc.y2020.day18;

import aoc.y2020.day18.parser.CalcPartTwoBaseVisitor;
import aoc.y2020.day18.parser.CalcPartTwoParser;

public class CalcPartTwoVisitorImpl extends CalcPartTwoBaseVisitor<Long> {

    @Override
    public Long visitNumber(CalcPartTwoParser.NumberContext ctx) {
        return Long.parseLong(ctx.NUMBER().toString());
    }

    @Override
    public Long visitSumExpr(CalcPartTwoParser.SumExprContext ctx) {
        return visit(ctx.expr(0)) + visit(ctx.expr(1));
    }

    @Override
    public Long visitMulExpr(CalcPartTwoParser.MulExprContext ctx) {
        return visit(ctx.expr(0)) * visit(ctx.expr(1));
    }

    @Override
    public Long visitParenExpr(CalcPartTwoParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

}
