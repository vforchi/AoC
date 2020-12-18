grammar Day18;

file : expression EOF;

expression
   :  expression PLUS expression
   |  expression TIMES expression
   |  LPAREN expression RPAREN
   |  NUMBER
   ;

fragment NUMBER
   : [0-9]+
   ;

LPAREN
   : '('
   ;

RPAREN
   : ')'
   ;

PLUS
   : '+'
   ;

TIMES
   : '*'
   ;

WS
   : [ \r\n\t] + -> skip
   ;

