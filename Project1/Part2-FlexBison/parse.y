%{
#include <stdio.h>
int yylex();
void yyerror(char* s);
%}

%union{
int int_val;
}

%token T 
%token F
%type <int_val> expr
%type <int_val> expr1
%type <int_val> expr2
%type <int_val> expr3
%type <int_val> expr4
%type <int_val> const

%start program

%%
program : expr'.'	{ if($1)printf("T"); else printf("F"); }
;

expr : expr1 '|''|' expr { if($1 || $4) $$ = 1; else $$ = 0; }
    | expr1 { $$ = $1; } 
;
expr1 : expr2 '^' expr1 { if($1 ^ $3) $$ = 1; else $$ = 0; }
      | expr2 { $$ = $1; }
;

expr2 : expr3 '&''&' expr2 { if($1 && $4) $$ = 1; else $$ = 0; }
      | expr4 '&''&' expr2 { if($1 && $4) $$ = 1; else $$ = 0; } 
      | const '&''&' expr2 { if($1 && $4) $$ = 1; else $$ = 0; }
      | expr3 { $$ = $1; }
      | expr4 { $$ = $1; }
      | const { $$ = $1; }
;

expr3 : '~'const { if($2)$$ = 0; else $$ = 1; }
      | '~'expr4 { if($2)$$ = 0; else $$ = 1; }
;

expr4 : '('expr')' { $$ = $2; }
;

const : T { $$ = 1; }
      | F { $$ = 0; }
;
%%

void yyerror(char* s){
  fprintf(stderr, "%s\n",s);
}

int main(int argc, char* argv[]){
	yyparse();
	return 0;
}
