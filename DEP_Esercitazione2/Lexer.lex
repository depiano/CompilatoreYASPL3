 
import java_cup.runtime.*;
  
  
%%

%class LexerLex
%cupsym LexerSym
%cup
%unicode
%line
%column
   	
  
%{

StringBuffer string = new StringBuffer();
 private Symbol symbol(int type)
	{
		return new Symbol(type);
	}

	private Symbol symbol(int type, String value)
	{
	Symbol toReturn = null;
		if(type == LexerSym.ID){
		
			toReturn = new Symbol(type, SymbolTable.addIntoTable(value));
		}else{
			toReturn = new Symbol(type, value);
		}
		return toReturn;
	}

	
%}

      
    
digit=[0-9]
digits={digit}+
letter=[a-zA-Z]
letter_={letter} ("_"|".")*
number= {digits}("."{digits})?("E"("+"|"-")?{digits})?
identificatore={letter_}({letter}|{digit})*


relop="<"|">"|"="|"<="|">="|"<>"
separatore=";" | "," | "(" | ")" | "[" | "]" | "{" | "}"|"."
assegnazione= "<--" | "-->" | "<<" | ">>"
operatore= "+" | "*" | "-" | "/"
 
separatore_istruzioni = \r|\n|\r\n
spazi_bianchi= {separatore_istruzioni}+ | [ \t\f] 

%state STRING
%state COMMENT
%state LINECOMMENT
%%

<YYINITIAL>
{
 
"if"    	{return symbol (LexerSym.IF, yytext());}
"then"  	{return symbol (LexerSym.THEN, yytext());}
"else"  	{return symbol (LexerSym.ELSE, yytext());}
"new"   	{return symbol (LexerSym.NEW, yytext());}
"for"   	{return symbol (LexerSym.FOR, yytext());}
"while"		{return symbol (LexerSym.WHILE, yytext());}
"do"		{return symbol (LexerSym.DO, yytext());}
"return"	{return symbol (LexerSym.RETURN, yytext());}
"endif"		{return symbol (LexerSym.ENDIF, yytext());}


{number} 		{return symbol (LexerSym.NUMBER, yytext());}
\" 				{string.setLength(0); yybegin(STRING);}

{separatore} 	{return symbol (LexerSym.SEP, yytext());}

{operatore}  	{return symbol (LexerSym.OP, yytext());}

{relop}			{return symbol (LexerSym.RELOP, yytext());}

{assegnazione}	{return symbol (LexerSym.ASS, yytext());}

{identificatore} {return symbol (LexerSym.ID, yytext());}	

{spazi_bianchi}	{         }

\/\* 			{string.setLength(0); yybegin(COMMENT);}
\/\/			{string.setLength(0); yybegin(LINECOMMENT);}

}

<STRING>{

<<EOF>>	{
			System.out.println("End of file");
			return symbol (LexerSym.EOF);
		}
 			 
\" {		yybegin(YYINITIAL);
			return symbol(LexerSym.STRING_LITERAL, string.toString());
	} 
-	 { string.append(yytext()); }		
.	 { string.append(yytext());}	 	
\\\\ { string.append('\\'); }
\\\" { string.append('\"'); }

	 
	{spazi_bianchi} { string.append(yytext()); }
			 
}

<COMMENT> {


  <<EOF>>	{
			System.out.println("End of file");
			return symbol (LexerSym.EOF);
		}

   \*\/ {
   			yybegin(YYINITIAL);
			return symbol(LexerSym.COMMENT, string.toString());
		 }
    -	 			{ string.append(yytext());}		
	.	 			{ string.append(yytext());}	 
   \/\* 			{ string.append(yytext());}	 
   \/\*\* 			{ string.append(yytext());}
   {spazi_bianchi}	{ string.append(yytext());}

}
 
<LINECOMMENT>{
 <<EOF>>	{
			System.out.println("End of file");
			return symbol (LexerSym.EOF);
		}
  
	\n {
		 yybegin(YYINITIAL);
		 return symbol(LexerSym.LINECOMMENT, string.toString()); 
		} 

[^\n]	{ string.append(yytext());}

}


[^]		{ throw new Error("Illegal character <"+yytext()+"> at line "+yyline+", column "+yycolumn); }
<<EOF>>	{ return symbol(LexerSym.EOF); }


 


