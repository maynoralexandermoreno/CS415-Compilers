package application;

public class ParseTable {
/*
 * GRAMMAR:
 * <Program> 	::= <StmtList>.
 * <StmtList> 	::= <Stmt><NextStmt>
 * <NextStmt> 	::= ;<StmtList> 
 * 				|   epsilon
 * <Stmt>		::= <Assign> 
 * 				|   <Print>
 * <Assign>		::= <Id>=<Expr>
 * <Print>		::= !<Id>
 * <Expr>		::= +<Expr><Expr>
 * 				|   -<Expr><Expr>
 * 				|   *<Expr><Expr>
 * 				|   /<Expr><Expr>
 * 				|   <Id>
 * 				|	<Const>
 * <Id>			::=	a|b|c
 * <Const>		::= 0|1|2|3|4|5|6|7|8|9
 * 
 * FIRST()
 * | <Const>	| <Id>		| <Expr>	| <Print>	| <Assign>	| <Stmt>	| <NextStmt>| <StmtList>| <Program>	|
 * |~~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|
 * |[0-9]		|[a-c]		|[-*+/]		| !			|[a-c]		|[a-c] !	| ; e		|[a-c] !	|[a-c] !	|
 * |			|			|[a-c][0-9]	|			|			|			|			|			|			|
 * |~~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|
 * 
 * 
 * FOLLOW():
 * | <Const>	| <Id>		| <Expr>	| <Print>	| <Assign>	| <Stmt>	| <NextStmt>| <StmtList>| <Program>	|
 * |~~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|
 * |[-*+/] ; .	|[-*+/] ; .	|[-*+/] ; .	| ; .		| ; .		| ; . 		| .			| .			|$			|
 * |[a-c][0-9] 	|[a-c][0-9] |[a-c][0-9]	|			|			|			|			|			|			|
 * |			| =			|			|			|			|			|			|			|			|
 * |~~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|~~~~~~~~~~~|
 * 
 * 
 * ParseTable
 * 				|[0-9]				|[a-c]				|[- * + /]			| !					| ;					| .					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <Program>	|					| <StmtList>.       |					| <StmtList>.       |					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <StmtList>	|					| <Stmt><NextStmt>  |					| <Stmt><NextStmt>  |					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <NextStmt>	|					|					|					|					| ;<StmtList>		| e					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <Stmt>		|					| <Assign>          |					| <Print>           |					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <Assign>		|					| <Id> = <Expr>     |					|					|					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <Print>		|					|					|					| ! <Id>            |					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <Expr>		| <Const>           | <Id>              |[-*+/]<Expr><Expr> |					|					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <Id>			|					| [a-c]				|					|					|					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 * <Const>		| [0-9]				|					|					|					|					|					|
 * 				|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~~~|
 */
 
}
