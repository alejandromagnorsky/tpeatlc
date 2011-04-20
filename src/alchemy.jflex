%%

%class Lexer
%type String

L = [a-z]

%%

{L}+" "\+" "{L}+ {return yytext();}
.*		         {System.out.println("???");}
\n				 {}