%%

%class ElementsLexer
%type String

L = [a-z]

%%

{L}+			{return yytext();}
.*				{}
\n				{}
<<EOF>>        {return "EOF";}