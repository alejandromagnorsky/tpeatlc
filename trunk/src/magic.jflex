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
		int id1 = 0, id2 = 0;
		String[] strings = yytext.split(" ");
		for(int i = 0; i < strings.length; i++)
			if(strings[i].length() != 0){
				if(id1 == 0)
					id1 = Integer.valueOf(strings[i].trim());
				else{
					id2 = Integer.valueOf(strings[i].trim());
				}
					
			}
		if(id1 > elementsList.size() || id2 > elementsList.size())
			throw new IllegalArgumentException();
		return new Potion(elementsList.get(id1-1), elementsList.get(id2-1));
	}
	
	public Element getResult(String yytext){
		int resultId = Integer.valueOf(yytext.substring(0, yytext.indexOf(":")));
		if(resultId > elementsList.size())
			throw new IllegalArgumentException();
		return elementsList.get(resultId-1);
	}
	
%}

D = [0-9]
W = [" "\t]
%state POTION

%%
<YYINITIAL> {D}+:/{W}*\n				{}
<YYINITIAL> {D}+:/{W}*					{result = getResult(yytext()); yybegin(POTION);}
<POTION> 	{W}+{D}+{W}+{D}+/{W}*,		{
											magic.put(getPotion(yytext()), result);	
											yybegin(POTION);
										}
<POTION> 	{W}+{D}+{W}+{D}+/{W}*		{	
											magic.put(getPotion(yytext()), result);
											yybegin(YYINITIAL);
										}
, | \n	| {W}							{}
[^0-9] | {D}+							{throw new IllegalArgumentException();}
<<EOF>>        							{return magic;}