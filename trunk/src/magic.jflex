import java.util.HashMap;
import java.util.List;

%%

%class MagicLexer
%type HashMap<Potion, Element>

%{
	
	private List<Element> elementsList;
	private HashMap<Potion, Element> magic;
	
	private Element result;
		
	public void init(List<Element> elementsList){
		this.elementsList = elementsList;
		this.magic = new HashMap<Potion, Element>();
	}
	
	public Potion getPotion(String yytext){
		int id1, id2, spaceIndex;
		spaceIndex = yytext.lastIndexOf(" ");
		id1 = Integer.valueOf(yytext.substring(1, spaceIndex));
		id2 = Integer.valueOf(yytext.substring(spaceIndex+1));
		return new Potion(elementsList.get(id1-1), elementsList.get(id2-1));
	}
	
	public Element getResult(String yytext){
		int resultId = Integer.valueOf(yytext.substring(0, yytext.indexOf(":")));
		return elementsList.get(resultId-1);
	}
	
%}

D = [0-9]
%state POTION

%%
<YYINITIAL> {D}+:/\n					{}
<YYINITIAL> {D}+:						{result = getResult(yytext()); yybegin(POTION);}
<POTION> 	" "{D}+" "{D}+/,				{
											magic.put(getPotion(yytext()), result);	
											yybegin(POTION);
										}
<POTION> 	" "{D}+" "{D}+				{
											magic.put(getPotion(yytext()), result);
											yybegin(YYINITIAL);
										}
, | \n									{}
<<EOF>>        							{return magic;}