lexer grammar NeoObjectPascalLexer;

// Keywords
USES: 'uses';
BEGIN: 'begin';
END: 'end';
VAR: 'var';
FUNCTION: 'function';
IF: 'if';
THEN: 'then';
ELSE: 'else';
WHILE: 'while';
DO: 'do';
FOR: 'for';
TO: 'to';
WRITELN: 'WriteLn';
READLN: 'ReadLn';
SHOWMENU: 'showMenu';
JSON_PARSE: 'JSON.parse';
CSV_PARSE: 'CSV.parse';
INTO: 'into';
RETURN: 'return';

// Types
TYPE_INTEGER: 'Integer';
TYPE_STRING: 'String';
TYPE_BOOLEAN: 'Boolean';
TYPE_REAL: 'Real';
TYPE_OBJECT: 'Object';

// Operators and Punctuation
ASSIGN: ':=';
COLON: ':';
COMMA: ',';
LPAREN: '(';
RPAREN: ')';
SEMI: ';';
DOT: '.';
MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
EQUAL: '=';
NOT_EQUAL: '<>';
LT: '<';
GT: '>';
LTE: '<=';
GTE: '>=';
PIPE: '|>';

// Basic Tokens
INTEGER: [0-9]+;
STRING:
    '"' ( '\\' . | ~('"'|'\\') )* '"' |
    '\'' ( '\\' . | ~('\''|'\\') )* '\''
    ;
IDENTIFIER: [a-zA-Z_] [a-zA-Z_0-9]*;

WS: [ \t\r\n]+ -> skip;
COMMENT: '{' .*? '}' -> skip;
