grammar CalcPartTwo;

start : expr | <EOF> ;

expr : expr '+' expr  # sumExpr
   | expr '*' expr  # mulExpr
   | '(' expr ')'  # parenExpr
   | NUMBER        # number
   ;

NUMBER : ('0' .. '9') + ;

WS : [ \r\n\t] + -> skip ;