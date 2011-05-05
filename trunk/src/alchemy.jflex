%%

%class AlchemyLexer
%type String

%{
	private String e1;
	private String e2;
	
	private void printError(){
		System.err.println("/!\\ Invalid command.");
		yybegin(YYINITIAL);
	}
%}

L = [a-z!]
W = [" "\t]
%state ELEMENT2
%state ERROR

%%

<YYINITIAL> {L}+	{
						if(e1 == null)
							e1 = yytext();
						else
							e1 += yytext(); 
					}
<YYINITIAL>	\+		{
						if(e1 == null)
							yybegin(ERROR);
						else		
							yybegin(ELEMENT2);
					}
<ELEMENT2>	{L}+	{
						if(e2 == null)
							e2 = yytext();
						else
							e2 += yytext();							
					}
								
{W}*				{
						if(e2 != null)
							e2 += " ";
						else if(e1 != null)
							e1 += " ";
					}

<ELEMENT2>	\n		{
						if(e2 == null){
							e1 = null;
							printError();
						} else {
							e1 += '&';
							e2 += '&';
							e1 = e1.replaceAll("[ \t]*&", "");
							e2 = e2.replaceAll("[ \t]*&", "");
							String ans = e1+"+"+e2;
							e1 = null;
							e2 = null;
							yybegin(YYINITIAL);
							return ans;
						}
					}
<ERROR>		\n		{printError();}
							
[^a-z] | {L}+		{
						e1 = null;
						e2 = null;
						if(yytext().contains("\n"))
							printError();
						else
							yybegin(ERROR);						
					}