package esercitazione_5;
import java_cup.runtime.*;

  
%%
 
%class YASPL3Lex
%cupsym YASPL3Sym
%cup
%unicode
%line
%column



 


%{
	StringBuffer string = new StringBuffer(); 

	private Symbol symbol(int type)
	{ 
		return new Symbol(type, yyline, yycolumn);
	}

	private Symbol symbol(int type, String value)
	{
		Symbol toReturn = null;
		toReturn = new Symbol(type, yyline, yycolumn, value);
		return toReturn;
	} 

	
%}

char_letter=[:jletter:]
digit=[0-9]
int_const={digit}+
double_const = {int_const} ("." {int_const})? ("E" ("+-")? {int_const})?


id=[:jletter:] [:jletterdigit:]*
semi=";"
comma=","
lpar="("
rpar=")"
lgpar="{"
rgpar="}"
read="<-"
write="->"
plus="+"
minus="-"
times="*"
div="/"
assign="="
gt=">"
ge=">="
lt="<"
le="<="
eq="=="
spazi_bianchi = [\r|\n|\r\n]+ | [ \t\f]

 

%state STRING_CONST
%state CHAR_CONST
%%

<YYINITIAL> {
	/* keywords */
	 
"head" 				{ return symbol(YASPL3Sym.HEAD, yytext()); }
"start"				{ return symbol (YASPL3Sym.START, yytext()); }
"int"				{ return symbol (YASPL3Sym.INT, yytext());}
"bool"				{ return symbol (YASPL3Sym.BOOL,yytext());}
"double"			{ return symbol (YASPL3Sym.DOUBLE, yytext());}
"string"			{ return symbol (YASPL3Sym.STRING, yytext());}
"char"			    { return symbol (YASPL3Sym.CHAR, yytext());}
{write}				{ return symbol (YASPL3Sym.WRITE, yytext());}
{read}				{ return symbol (YASPL3Sym.READ, yytext());}
"true"				{ return symbol (YASPL3Sym.TRUE, yytext());}
"false"				{ return symbol (YASPL3Sym.FALSE, yytext());}
"if"				{return symbol   (YASPL3Sym.IF, yytext());}
"then"				{return symbol   (YASPL3Sym.THEN, yytext());}
"while"				{return symbol   (YASPL3Sym.WHILE, yytext());}
"do"				{return symbol   (YASPL3Sym.DO, yytext());}
"else"				{return symbol   (YASPL3Sym.ELSE, yytext());}
"not"				{return symbol   (YASPL3Sym.NOT, yytext());}
"and"				{return symbol   (YASPL3Sym.AND, yytext());}
"or"				{return symbol   (YASPL3Sym.OR, yytext());}
"in"				{return symbol   (YASPL3Sym.IN, yytext());}
"out"				{return symbol   (YASPL3Sym.OUT, yytext());}
"inout"				{return symbol   (YASPL3Sym.INOUT, yytext());}
"def"			    { return symbol (YASPL3Sym.DEF, yytext());}



/*literals*/
{int_const}			{ return symbol (YASPL3Sym.INT_CONST, yytext());}
{double_const}		{ return symbol (YASPL3Sym.DOUBLE_CONST, yytext());}

/*separetor*/
{comma}				{ return symbol (YASPL3Sym.COMMA, yytext());}
{lpar}				{ return symbol (YASPL3Sym.LPAR, yytext());}
{rpar}				{ return symbol (YASPL3Sym.RPAR, yytext());}
{lgpar}				{ return symbol (YASPL3Sym.LGPAR, yytext());}
{rgpar}				{ return symbol (YASPL3Sym.RGPAR, yytext());}
{semi}				{ return symbol (YASPL3Sym.SEMI, yytext());}

/* identifiers */
{id} { return symbol(YASPL3Sym.ID, yytext()); }

/*relop*/
{gt}				{ return symbol (YASPL3Sym.GT, yytext());}
{ge}				{ return symbol (YASPL3Sym.GE, yytext());}
{lt}				{ return symbol (YASPL3Sym.LT, yytext());}
{le}				{ return symbol (YASPL3Sym.LE, yytext());}
{eq}				{ return symbol (YASPL3Sym.EQ, yytext());}


/*operators*/
{plus}				{ return symbol (YASPL3Sym.PLUS, yytext());}
{minus}				{ return symbol (YASPL3Sym.MINUS, yytext());}
{times}				{ return symbol (YASPL3Sym.TIMES, yytext());}
{div}				{ return symbol (YASPL3Sym.DIV, yytext());}
{assign}			{ return symbol (YASPL3Sym.ASSIGN, yytext());}


/* whitespace */
{spazi_bianchi} { /* ignore */ }
	

\" 					{ string.setLength(0); yybegin(STRING_CONST); }
\'					{ string.setLength(0); yybegin(CHAR_CONST); }
}

<STRING_CONST>{

	<<EOF>>	{ System.out.println("End of file");
			return symbol (YASPL3Sym.EOF);
			 }
	 \"    {	
		 	yybegin(YYINITIAL); 
			 return symbol(YASPL3Sym.STRING_CONST, string.toString());
			}
	. 		{ string.append(yytext()); }
	\\\\ 	{ string.append('\\'); }
	\\\" 	{ string.append('\"'); }
	{spazi_bianchi} { string.append(yytext()); }

}
 


<CHAR_CONST>{
<<EOF>>	{ System.out.println("End of file");
			return symbol (YASPL3Sym.EOF);
			 }
		 \'   {	
		 	if(string.length()==1)
		 	{yybegin(YYINITIAL); 
			 return symbol(YASPL3Sym.CHAR_CONST, string.toString());}
			 else
			 {
			 throw new Error("Illegal character <"+yytext()+"> at line "+yyline+", column "+yycolumn);
			 }}	 
	. 		{ string.append(yytext()); }
	{spazi_bianchi} { string.append(yytext()); }

}

 


/* error fallback */
[^]		{ throw new Error("Illegal character <"+yytext()+"> at line "+yyline+", column "+yycolumn);
		}

<<EOF>>	{ return symbol(YASPL3Sym.EOF); }
