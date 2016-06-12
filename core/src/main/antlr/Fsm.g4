grammar Fsm;

// Skip whitespace and comments
WS:  [ \t\r\n\u000C]+ -> skip;
COMMENT:   '/*' .*? '*/' -> skip;
LINE_COMMENT:   '//' ~[\r\n]* -> skip;

ID: [a-zA-Z$_] [a-zA-Z0-9$_.]*;
INT : '-' ? [0-9]+;
FLOAT: INT ? '.' [0-9]+;
STRING: '"' ('\\"' | ~'"')* '"';

fsm
    : params statesOpt stateless
    ;

params
    : 'Params' paramslist
    |
    ;

paramslist
    : param
    | param paramslist
    ;

param
    : assignment
    ;

stateless
    : 'Stateless' triggers
    |
    ;

statesOpt
    : states
    |
    ;

states
    : state
    | state states
    ;

state
    : 'State' ID '(' ID ')' triggers
    ;

triggers:
    trigger
    | trigger triggers
    ;

trigger:
    '[' ID ']' statements #SystemTrigger
    | '[' 'Trigger' e ']' statements #SingleCondTrigger
    | '[' 'Trigger' triglist ']' statements #MultiCondTrigger
    ;

triglist
    : trigel
    | trigel triglist
    ;

trigel
    : INT ':' e
    ;

statements
    : statement
    | statement statements
    ;

statement
    : assignment
    | '->' ID
    ;

assignment
    : ID '=' e
    ;

e
    : INT       #IntLiteral
    | FLOAT     #FloatLiteral
    | STRING    #StringListeral
    | ID        #IdLiteral
    | e '>=' e  #GtEqExp
    | e '<=' e  #LtEqExp
    | e '<' e   #LtExp
    | e '>' e   #GtExp
    | e '==' e  #EqExp
    | e '!=' e  #NeqExp
    | e '&&' e  #AndExp
    | e '||' e  #OrExp
    | '!' e     #NotExp
    | '(' e ')' #ParExp
    | fcall     #FCallExp
    | '->' ID   #StateChange
    ;

fcall
    : ID '(' elist ')'
    ;

elist
    : e
    | e ',' elist
    ;
