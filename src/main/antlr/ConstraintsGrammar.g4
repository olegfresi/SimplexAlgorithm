grammar ConstraintsGrammar;

@header {
package Parser.Generated;
}

file
    : NL* (formula (NL+ formula)*)? NL* EOF
    ;

formula
    : expr relop expr
    ;

expr
    : term (ADD term | SUB term)*
    | SUB term (ADD term | SUB term)*
    ;

term
    : NUMBER VARIABLE      # coeffVar
    | VARIABLE             # var
    | NUMBER               # number
    | SUB term             # negTerm
    | '(' expr ')'         # parens
    ;

relop
    : LE | GE | EQ | LT | GT
    ;

ADD : '+';
SUB : '-';

LE : '<=';
GE : '>=';
EQ : '=';
LT : '<';
GT : '>';

VARIABLE : [a-zA-Z][a-zA-Z0-9]*;

NUMBER
    : [0-9]+ ('/' [0-9]+)? ('.' [0-9]+)?
    ;

NL : '\r'? '\n' ;

WS : [ \t]+ -> skip;