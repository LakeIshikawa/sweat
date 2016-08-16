grammar Fsm;

// Skip whitespace and comments
WS:  [ \t\r\n\u000C]+ -> skip;
COMMENT:   '/*' .*? '*/' -> skip;
LINE_COMMENT:   '//' ~[\r\n]* -> skip;

KEY: 'F' | 'B' | 'D' | 'U' | 'B1'| 'B2'| 'B3'| 'B4'| 'B5'| 'B6';
BOOL: 'true' | 'false';
ANIM: 'A:' [a-zA-Z$_] [a-zA-Z0-9$_.]*;
PHYSICS: 'P:' [a-zA-Z$_] [a-zA-Z0-9$_.]*;
COMMAND: 'C:' [a-zA-Z$_] [a-zA-Z0-9$_.]*;
HIT: 'H:' [a-zA-Z$_] [a-zA-Z0-9$_.]*;
KEYS: 'K:' [a-zA-Z$_] [a-zA-Z0-9$_.]*;
ID: [a-zA-Z$_] [a-zA-Z0-9$_.]*;
INT : '-' ? [0-9]+;
FLOAT: INT ? '.' [0-9]+;
STRING: '"' ('\\"' | ~'"')* '"';

fsm
    : includeOpt statesOpt stateless
    ;

cmd
    : commandlist
    |
    ;

commandlist
    : commandline
    | commandline commandlist
    ;

includeOpt
    : 'include' '(' STRING ')'
    |
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
    : 'State' ID '(S)' triggers   #StateStart
    | 'State' ID triggers               #StateNormal
    ;

triggers:
    trigger
    | trigger triggers
    ;

trigger:
    '[' ID ']' statementsOpt #SystemTrigger
    | '[' 'Trigger' e ']' statementsOpt #SingleCondTrigger
    | '[' 'Trigger' triglist ']' statementsOpt #MultiCondTrigger
    ;

triglist
    : trigel
    | trigel triglist
    ;

trigel
    : INT ':' e
    ;

statementsOpt
    : statements
    |
    ;

statements
    : statement
    | statement statements
    ;

statement
    : assignment    #AssignmentStmt
    | switchcase    #SwitchStmt
    | ite           #IteStmt
    | fcall         #FCallStmt
    | '->' ID       #StateChangeStmt
    | ID '{' statement '}' #FsmStatement
    ;

switchcase
    : 'switch' e '{' caselist '}'
    ;

caselist
    : scase
    | scase caselist
    ;

scase
    : e ':' '{' statements '}'
    ;

ite
    : 'if' e '{' statements '}'                             #IfThen
    | 'if' e '{' statements '}' 'else' '{' statements '}'   #IfThenElse
    ;

assignment
    : ID '=' e
    ;

e
    : INT       #IntLiteral
    | FLOAT     #FloatLiteral
    | STRING    #StringLiteral
    | ID        #IdLiteral
    | BOOL      #BoolLiteral
    | ANIM      #AnimLiteral
    | PHYSICS   #PhysicsLiteral
    | COMMAND   #CommandLiteral
    | HIT       #HitLiteral
    | KEYS      #KeysLiteral
    | e '?' e ':' e #CondExp
    | e '+' e   #AddExp
    | e '-' e   #SubExp
    | e '*' e   #MulExp
    | e '/' e   #DivExp
    | e '%' e   #ModExp
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
    | ID '{' e '}' #FsmExp
    ;

fcall
    : ID '(' elist ')'
    | ID '(' ')'
    ;

elist
    : e             #ElistE
    | e ',' elist   #EListEElist
    ;

commandline
    : 'Command' ID '{' INT '}' '<' commandseq '>'
    ;

commandseq
    : ctoken                   #CommandSeqCToken
    | ctoken ',' commandseq    #CTokenList
    ;

ctoken
    : exclusiveOpt modOpt timeOpt keylist
    ;

exclusiveOpt
    : '!'
    |
    ;

modOpt
    : '%'   #HoldMod
    | '~'   #ReleaseMod
    |       #NoMod
    ;

timeOpt
    : INT
    |
    ;

keylist
    : KEY
    | KEY '+' keylist
    ;