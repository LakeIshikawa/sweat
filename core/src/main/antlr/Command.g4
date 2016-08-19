grammar Command;

// Skip whitespace and comments
WS:  [ \t\r\n\u000C]+ -> skip;
COMMENT:   '/*' .*? '*/' -> skip;
LINE_COMMENT:   '//' ~[\r\n]* -> skip;

KEY: 'F' | 'B' | 'D' | 'U' | 'B1'| 'B2'| 'B3'| 'B4'| 'B5'| 'B6';
INT : '-' ? [0-9]+;

command:
    '{' INT '}' '<' commandseq '>'
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