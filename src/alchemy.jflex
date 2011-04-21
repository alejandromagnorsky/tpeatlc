%%

%class AlchemyLexer
%type String

%{
	private String e1;
	private boolean plus;
	private String ans;
%}

L = [a-z]
W = [\t" "]

%%

{L}+			{ 
					if(!plus)
						e1 = yytext();
					else if(e1 != null){
						ans = e1+"+"+yytext();
						e1 = null;
						plus = false;						
						return ans;
					} else
						plus = false;
				}
{W}*			{}
\+				{plus = true;}
\n				{}
[0-9]+|[A-Z]+	{
					e1 = null;
					plus = false;
					ans = null;
					System.out.println("???");
				}