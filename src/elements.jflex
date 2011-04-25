%%

%class ElementsLexer
%type String

L = [a-z]

%%

{L}+			{return yytext();}
\n				{}
.+				{throw new IllegalArgumentException();}
<<EOF>>        	{return "EOF";}