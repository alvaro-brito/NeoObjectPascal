parser grammar NeoObjectPascalParser;

options { tokenVocab=NeoObjectPascalLexer; }

// Parser rules
program: (usesClause SEMI)? (declaration SEMI)* (block DOT)? EOF;

declaration:
      variableDeclaration
    | functionDeclaration
    ;

usesClause: USES modulePath (COMMA modulePath)*;

modulePath: IDENTIFIER (DOT IDENTIFIER)*;

block: BEGIN (statement SEMI)* END;

statement:
    variableDeclaration
    | assignment
    | call
    | ifStatement
    | whileStatement
    | forStatement
    | writeLnStatement
    | readLnStatement
    | showMenuStatement
    | jsonParseStatement
    | csvParseStatement
    | returnStatement
    | functionDeclaration;

variableDeclaration: VAR identifier COLON type;

assignment: identifier ASSIGN expression;

call: identifier (LPAREN expressionList? RPAREN)?;

functionDeclaration: FUNCTION identifier (LPAREN parameterList? RPAREN )? COLON type block;

parameterList: parameter (COMMA parameter)*;

parameter: identifier (COLON type)?;

ifStatement: IF expression THEN statement (ELSE statement)?;

whileStatement: WHILE expression DO statement;

forStatement: FOR identifier ASSIGN expression TO expression DO statement;

writeLnStatement: WRITELN LPAREN expressionList? RPAREN;

readLnStatement: READLN LPAREN identifier RPAREN;

showMenuStatement: SHOWMENU LPAREN expressionList RPAREN;

jsonParseStatement: JSON_PARSE LPAREN expression RPAREN INTO identifier;

csvParseStatement: CSV_PARSE LPAREN expression RPAREN INTO identifier;

returnStatement: RETURN expression;

expressionList: expression (COMMA expression)*;

expression:
    javaBlock
    | primary
    | expression (MUL | DIV) expression
    | expression (ADD | SUB) expression
    | expression (EQUAL | NOT_EQUAL | LT | GT | LTE | GTE) expression
    | expression PIPE expression;

primary:
    INTEGER
    | STRING
    | identifier
    | LPAREN expression RPAREN
    | call;

type: TYPE_INTEGER | TYPE_STRING | TYPE_BOOLEAN | TYPE_REAL | TYPE_OBJECT;

identifier: IDENTIFIER;

javaBlock: JAVA COLON LPAREN expressionList? RPAREN JAVA_CODE;
