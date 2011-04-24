%%

%class AlchemyLexer
%type String

%{
	private String e1;
	private String ans;
%}

L = [a-z]
W = [\t" "]
%state PLUS
%state ELEMENT2
%state ERROR

%%

<YYINITIAL> {L}+	{e1 = yytext(); yybegin(PLUS);}
<PLUS>		\+		{yybegin(ELEMENT2);}
<ELEMENT2>	{L}+	{
						ans = e1+"+"+yytext();
						e1 = null;
						yybegin(YYINITIAL);
						return ans;
					}
								
{W}*	{}

<YYINITIAL>	\n		{}
<PLUS>		\n		{}
<ELEMENT2>	\n		{}
<ERROR>		\n		{System.out.println("/!\\ Comando inválido."); yybegin(YYINITIAL);}

[^a-z] | {L}+		{
						e1 = null;
						ans = null;
						yybegin(ERROR);						
					}