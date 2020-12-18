grammar CalcPartOne;

start : expr | <EOF> ;

expr : expr op expr  # opExpr
   | '(' expr ')'  # parenExpr
   | NUMBER        # number
   ;

op : '+' | '*' ;

NUMBER : ('0' .. '9') + ;

WS : [ \r\n\t] + -> skip ;