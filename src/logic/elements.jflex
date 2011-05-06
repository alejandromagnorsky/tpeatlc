package logic;
%%

%class ElementsLexer
%type String

L = [a-z" "\t!]

%%

{L}+			{
					String[] strings = yytext().split("[ \t]");
					String ans = "";
					for(int i = 0; i < strings.length; i++)
						if(strings[i].length() != 0){
							if(ans.length() != 0)
								ans += " ";
							ans += strings[i];
						}
					
					return ans;
				}
\n				{}
.+				{throw new IllegalArgumentException();}
<<EOF>>        	{return "EOF";}