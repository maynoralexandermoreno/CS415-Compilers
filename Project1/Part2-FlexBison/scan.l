%{
#include "parse.tab.h"
#define YY_SKIP_YYWRAP
%}

	extern YYSTYPE yylval;
	extern int yywrap() {return 1;};
	extern int isatty();

%%

t		{ return T; }
f		{ return F; }
[ \t]		; 		// ignore all whitespace
.			{ return yytext[0]; }

%%
