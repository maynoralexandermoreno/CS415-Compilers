package application;
/*
 * ParseTable.java has a description of the grammar, first(), follow(), and the LL1 parse table
 */
public class Parser {
	private char[] input;
	private int i;
	int a;
	int b;
	int c;
	int temp;
	int num;
	int denom;
	boolean assignA;
	boolean assignB;
	boolean assignC;
	
/* 
 * Call the constructor and Parser will initialize the "registers" a, b, c, to zero and set their flags to false (not assigned). 
 * "Pointer" i will point to the head of the array. 
 * If input string is provided at this stage, then set Parser's char array will equal to the char array made from input string.
 */
	public Parser() {
		a = 0; b = 0; c = 0; i = 0;
		assignA = false; assignB = false; assignC = false;
	}
	
	public Parser(String input) {
		a = 0; b = 0; c = 0; i = 0;
		assignA = false; assignB = false; assignC = false;
		this.input = input.toCharArray();
	}
	
/*
 * Running parser requires calling parse(); parse(String input) allows to change the Parser's char array
 * Both start <Program> in the grammar 
 */
	public void parse() {
		Program();
	}
	
	public void parse(String input) {
		this.input = input.toCharArray();
		Program();
	}
	
/* 
 * <Program> :== <StmtList>. 
 *  On {a b c !} go to <StmtList>
 *  Afterward, check if the pointer points to a '.' and that nothing comes afterward
 *  Otherwise reject; Syntax Error
 */
	public void Program() {
		// System.out.println("In program i = " + i);
		// Test to see if input at i is {a b c !}, then run StmtList();
		switch(input[i]) {
			case 'a':
			case 'b':
			case 'c':
			case '!':
				StmtList();			// Now should point to a '.' and should be the last input
				if(input[i] == '.' && i+1 == input.length) {
					System.out.println("Success");
				}
				else {
					System.out.println("Syntax Error");
					// System.out.println("Syntax Error: Doesn't end with a '.'");	
					System.exit(1);
				}
				
				break;
			default:
				System.out.println("Syntax Error");
				// System.out.println("Syntax Error: Program Expecting [a-c] or ! ");
				System.exit(1);
		}
	}
	
/*
 * <StmtList> ::= <Stmt><NextStmt>
 * On {a b c !} go to <Stmt>; afterwards perform <NextStmt>
 * Otherwise reject; Syntax Error
 */
	public void StmtList() {
		// System.out.println("In stmtlist i = " + i);
		// Test to see if input at i is {a b c !}, then run Stmt(); After run NextStmt();
		
		switch(input[i]) {
			case 'a':
			case 'b':
			case 'c':
			case '!':
				Stmt();				// Process StmtList first 
				NextStmt();			// Process NextStmt 2nd
				break;				// This essentially adds these methods into a stack where Stmt must finish first before NextStmt begins processing
			
			default:
				System.out.println("Syntax Error");
				// System.out.println("Syntax Error: StmtList Expecting [a-c] or ! ");
				System.exit(1);
		}
	}

/*
 * <NextStmt> ::= ;<StmtList> | epsilon
 * On {;}, "match" ';' by incrementing i; perform <StmtList>
 * On {.} (the result epsilon on <NextStmt>), complete NextStmt and return to wherever <NextStmt> was called from
 * Otherwise, reject; Syntax Error
 */
	public void NextStmt() {
		// System.out.println("In nextstmt i = " + i);
		// 2 different actions performed based on input
		
		switch(input[i]) {
			case ';':				// This part here performs the "matching"
				i++; 				// if the element is ";" then match it with the rule's ";" and push <StmtList>
				StmtList();
				break;
				
			case '.':				// Don't match here, matching for this terminal occurs at Program()
				break;
				
			default:
				System.out.println("Syntax Error");
				// System.out.println("Syntax Error: NextStmt Expecting . or ;");
				System.exit(1);
				break;
		}				
	}

/*
 *  <Stmt>	::= <Assign> | <Print>
 *  On {a b c} perform <Assign>
 *  On {!} perform <Print>
 *  Otherwise reject; Syntax Error
 */
	public void Stmt() {
		// System.out.println("In stmt i = " + i);
		// 2 different actions performed based on input
		
		switch(input[i]) {
			case 'a':
			case 'b':
			case 'c':
				Assign();		
				break;
				
			case '!':
				Print();
				break;
				
			default:
				System.out.println("Syntax Error");
				// System.out.println("Syntax Error: Stmt Expecting [a-c] or !");
				System.exit(1);
				break;
		}	
	}

/*
 * <Assign>	::= <Id>=<Expr>
 * The character at i, i.e {a b c}, will dictate which register we use to save the variable at
 * "Match" the character and increment i, then "match" the '=' and increment i before performing the assignment of <Expr> to <Id>
 * Note that we assign a value to a register in this use of <Id>, not return the value of <Id>
 */
	public void Assign() {
		// System.out.println("In assign i = " + i);
		// Every matched case will perform the same: match <Id>, i++, match '=', i++, perform <Expr>
		
		switch(input[i]) {
			case 'a':
				i++;				// match the 'a' match the '='
				if(input[i] != '=') { 
					System.out.println("Syntax Error");
					// System.out.println("Syntax Error: Assign Expecting =");
					System.exit(1);
				}
				i++;
				a = Expr();			// assign to register the value of Expr();
				assignA = true;		// Set the assignment flag of this register to true
				break;
				
			case 'b':
				i++;				// match the 'a' match the '='
				if(input[i] != '=') { 
					System.out.println("Syntax Error");
					// System.out.println("Syntax Error: Assign Expecting =");
					System.exit(1);
				}
				i++;
				b = Expr();			// assign to register the value of Expr();
				assignB = true;		// Set the assignment flag of this register to true
				break;
				
			case 'c':
				i++;				// match the 'a' match the '='
				if(input[i] != '=') {
					System.out.println("Syntax Error");
					// System.out.println("Syntax Error: Assign Expecting =");
					System.exit(1);
				}
				i++;
				c = Expr();			// assign to register the value of Expr();
				assignC = true;		// Set the assignment flag of this register to true
				break;
				
			default:
				System.out.println("Syntax Error");
				// System.out.println("Syntax Error: Assign Expecting [a-c]");
				System.exit(1);
				break;
		}
	}

/*
 * <Print>	::= !<Id>
 * On {i} match '!', i++, call <Id> to get the value at the register after '!'
 * Otherwise, reject; Syntax Error (Though at this point this is more for modularity; Stmt() has already matched '!")
 */
	public void Print() {
		
		// Switch statement not needed; Stmt() already confirmed char at i to be '!'; We could just call <Id> 
		// Left in comments in case needed down the road
		i++;
		System.out.println(Id());
		
//		System.out.println("In print i = " + i);
//		switch(input[i]) {
//			
//			case '!':
//				i++;				// '!' matched; Prints the value of the register next to '!'
//				System.out.println(Id());
//				break;
//			
//			default:				// Shouldn't ever get here in our program; No way to fail after Stmt() fails
//				System.out.println("Syntax Error");	
//				// System.out.println("Syntax Error: Print Expecting !");
//				System.exit(1);
//				break;
//		}
		
	}

/*
 * <Expr>	::= +<Expr><Expr> | -<Expr><Expr> | *<Expr><Expr> | /<Expr><Expr> | <Id> | <Const>
 * On {+ - / *} perform the operator using the 2 values returned from the two <Expr> and save it into a temp variable
 * On {a b c} perform <Id>, i.e get the int at that register
 * On {[0-9]} perform <Const> i.e convert the char to an int 
 * NOTE: THIS PROJECT ONLY DEALS WITH SINGLE DIGIT INTEGERS AS INPUT
 * NOTE: Both <Id> and <Const> can be performed at this level, but for modularity's sake it has been split
 * Afterwards, return the temp variable
 * Otherwise, reject; Syntax Error
 */
	public int Expr() {
		// System.out.println("In expr i = " + i);
		switch(input[i]) {
			case '+':				// add the two Expr()
				i++;
				temp = Expr() + Expr();
				break;
				
			case '-':				// subtract the two Expr()
				i++;
				temp = Expr() - Expr();
				break;
				
			case '*':				// Multiply the two Expr()
				i++;
				temp = Expr() * Expr();
				break;
				
			case '/':				// Divide the two Expr()
				i++;
				num = Expr();
				denom = Expr();
				if(denom == 0) {	// Account for if the denominator is zero
					System.out.println("Runtime Error");
					// System.out.println("Runtime Error: Division By Zero");
					System.exit(1);
				}
				temp = num/denom;
				break;
			case '0':				// Retrieve the constant 
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				temp = Const();
				break;
			case 'a':				// Retrieve the values at registers a, b, or c; No way to really know to call Id() w.o a switch here
			case 'b':
			case 'c':
				temp = Id();
				break;
			default:
				System.out.println("Syntax Error");
				// System.out.println("Syntax Error: Expr Expecting [0-9][a-c][+-/*]");
				// System.out.println(i + " is i; the array is " + input[i]);
				System.exit(1);
				break;
		}
		return temp;				// Expr() needs to return the int so that its value can be saved or used in other Expr()
	}

/*
 * On {[0-9]} simply matches the char at i and returns that int value
 */
	public int Const() {
		// Since we matched i in Expr(), we know that the character is in the range of [0-9] 
		// after using i increment it
		try {
			temp = Integer.parseInt(input[i++] + "");
		}
		catch (NumberFormatException e){
			System.out.println("Syntax Error");
			//System.out.println("Syntax Error: Const Expecting [0-9]");
			System.exit(1);
		}
		return temp ;
	}

/*
 * Retrieve the value at the register a, b or c
 */
	public int Id() {
		// Note that the if statements check if the variables were initialized; Syntax error if using a register that not initialized
		switch(input[i]) {
			case 'a':	
				i++;
				if(assignA == false ) {
					System.out.println("Syntax Error");
					// System.out.println("Syntax Error: Variable referenced without assignment");
					System.exit(-1);
				}
				temp = a;
				break;
				
			case 'b':
				i++;
				if(assignB == false ) {
					System.out.println("Syntax Error");
					// System.out.println("Syntax Error: Variable referenced without assignment");
					System.exit(-1);
				}
				temp = b;
				break;
				
			case 'c':
				i++;
				if(assignC == false ) {
					System.out.println("Syntax Error");
					// System.out.println("Syntax Error: Variable referenced without assignment");
					System.exit(-1);
				}
				temp = c;
				break;
		}
		return temp;				// Return the value at this register
	}
}

