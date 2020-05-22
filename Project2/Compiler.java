package cs415;
import cs415.Node;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Compiler {
	private Hashtable<String, Node> registers;
	private Scanner input;
	private String currentString;
	private boolean isBool;
	private String first;
	private String second;
	private Pattern correctBoolOp = Pattern.compile("\\!|&&|\\|\\||<|<=|==|!=|=>|>|\\^");
	private Matcher correctBoolMatch;
	private Pattern correctIntOp = Pattern.compile("[+*-/]");
	private Matcher correctIntMatch;
	/* Constructors */
	public Compiler() {
		this.registers = new Hashtable<String, Node>();
		this.isBool = false;
	}
	public Compiler(Scanner in) {
		this.registers = new Hashtable<String, Node>();
		this.input = in;
		this.isBool = false;
		compile();
	}
	
	/* Setter for in; just in case person uses no-arg constructor */
	public void setInput(Scanner in) {
		this.input = in;
	}
	public void setCurrentString(String first) {
		this.currentString = first;
	}
	
	/* Create the methods for all the rules*/
	public void compile() {
		program();
	}
	
	public void program() {
		main();
	}
	
	public void main() {
		this.first = this.input.next();
		this.second = this.input.next();
		if(this.first.equals("def") && this.second.equals("main") ){
			System.out.println("Success @ main");
			this.currentString  = this.input.next();
			valdecls();
		} else System.out.println("Failiure @ main");
	}
	
	public void valdecls() {
		System.out.println(this.currentString);
		if(this.currentString.equals("int")) {
			this.isBool = false;
			this.currentString = this.input.next();
			if(Pattern.matches("([a-zA-Z])\\w*(;)", this.currentString)){
				this.currentString = this.currentString.substring(0, this.currentString.indexOf(";"));
				valdecl();
				this.currentString = this.input.next();
			}
			else {
				System.out.println("faliure at valdecls int");
				System.exit(1);
			}
		
		}
		else if(this.currentString.equals("bool")) {
			this.isBool = true;
			this.currentString = this.input.next();
			if(Pattern.matches("([a-zA-Z])\\w*(;)", this.currentString)){
				this.currentString = this.currentString.substring(0, this.currentString.indexOf(";"));
				valdecl();
			}
			else {
				System.out.println("faliure at valdecls int");
				System.exit(1);
			}
		} 
		else stmts();
	}
	
	public void valdecl() {
		Node temp = new Node(isBool,false);
		this.registers.remove(this.currentString);
		this.registers.put(this.currentString, temp);	
		this.currentString = this.input.next();
		valdecls();
	}
	
	public void stmts() {
		stmt();
		stmts();
	}
	
	public void stmt() {
		System.out.println(this.currentString);
		if (this.currentString.equals("end")) {
			//System.out.println(input.hasNext());
			System.out.println("Sccessfully compiled at stmts");
			System.exit(0);
		}
		else if(this.currentString.equals("print")) {
			print();
		}
		else if(Pattern.matches("([a-zA-Z])\\w*", this.currentString)) {
			assign();
		}
		else {
			System.out.println("Error at stmt");
			System.exit(1);
		}
	}
	
	public void assign() {
		String key = this.currentString;
		String equals = this.input.next();
		Node temp = this.registers.get(this.currentString);
		if(temp == null) {
			System.out.println("Error at assing; null node");
			System.exit(1);
		}
		if(equals.equals("=") != true) {
			System.out.println("Error at assing; no equals sign");
			System.exit(1);
		}
		//input.delimiter()
		this.currentString = this.input.next();
		if(Pattern.matches("\\w+(;)", this.currentString)) {
			this.currentString = this.currentString.substring(0,this.currentString.indexOf(";"));
			int x = 0;
			if(Pattern.matches("\\d\\+", this.currentString) && !temp.isBool()) {
				x = Integer.parseInt(this.currentString);
			}
			else if(this.currentString.equals("true") || this.currentString.equals("t") || this.currentString.equals("True") || this.currentString.equals("T") && temp.isBool()) {
				x = 1;
			}
			else if(this.currentString.equals("false") || this.currentString.equals("f") || this.currentString.equals("False") || this.currentString.equals("F") && temp.isBool()) {
				x = 0;
			}
			else if(Pattern.matches("([a-zA-Z])\\w*", this.currentString)) {
				Node temp2 = this.registers.get(this.currentString);
				if(temp.isBool() == temp2.isBool()) {
					x = temp2.getValue();
				}
				else {
					System.out.println("Error at assing; boolean int mismatch");
					System.exit(1);
				}
			}
			else {
				System.out.println("Error at assing; issue at pattern matching regex for one instruction");
				System.exit(1);
			}
			this.registers.remove(key);
			temp = new Node(temp.isBool(), x);
			this.registers.put(key, temp);
			this.currentString = this.input.next();
		}
		else {
			while(this.currentString.endsWith(";") != true) {
				System.out.println(this.currentString);
				if(this.input.hasNext()) {
					String s1= this.input.next();
					if(s1.equals("+") || s1.equals("-") || s1.equals("/") || s1.equals("*") || s1.equals("&&") 
							|| s1.equals("||") || s1.equals("^") || s1.equals("<") || s1.equals("!")
							|| s1.equals("<=") || s1.equals("==") || s1.equals(">=") || s1.equals(">")
							|| Pattern.matches("([a-zA-Z])\\w*", s1) || Pattern.matches("([a-zA-Z])\\w*(;)", s1)
							|| Pattern.matches("\\(.+",s1) || Pattern.matches(".+\\)",s1) || Pattern.matches(".+\\)(;)",s1)
							|| Pattern.matches("\\d+",s1)	|| Pattern.matches("\\d+(;)",s1)) {	
						this.currentString = this.currentString + " " + s1;
					}
					else {
						System.out.println("Error at assing; wrong input format in else stmt"); 
						System.exit(1);
					}
				}
				else {
					System.out.println("Error at assing; ran out of input"); 
					System.exit(1);
				}
			}
			this.correctBoolMatch = this.correctBoolOp.matcher(this.currentString);
			this.correctIntMatch = this.correctIntOp.matcher(this.currentString);
			System.out.println("is the variable an boolean? " + temp.isBool());
			if(this.correctBoolMatch.find() && temp.isBool()) {
				System.out.println("now we go to finding boolean value");
				this.currentString = this.input.next();
//				int x = boolExpr(currentString);
			}
			else if(this.correctIntMatch.find() && !temp.isBool()) {
				System.out.println("now we go to finding int value");
				int x = intExpr(this.currentString);
			}
			else {
				System.out.println("Error at assign after while loop; int bool mismatch");
				System.exit(0);
			}
		}
	}
	
	public void print() {
		this.currentString = this.input.next();
		if(Pattern.matches("\\(([a-zA-Z])\\w*\\)(;)", this.currentString)) {
			this.currentString = this.currentString.substring(1, this.currentString.indexOf(";")-1);
//			System.out.println(currentString + " @ print()");
			Node temp = this.registers.get(this.currentString);
			if(temp != null && temp.isInstantiated()) {
				int x = temp.getValue();
				if(temp.isBool()) {
					if(x==1) {
						System.out.println("true");
					}
					else if(x==0) {
						System.out.println("false");
					}
					else {
						System.out.println("Error at print; wrong value for a bool");
						System.exit(1);
					}
				}
				else {
					System.out.println(x);
				}
				this.currentString = this.input.next();
			}
			else {
				System.out.println("Error at print; not instantiated value");
				System.exit(1);
			}
		}
		else {
			System.out.println("Error at print; incorrect print (id) format");
			System.exit(1);
		}
	}
	
	public int intExpr(String input) {
		int answer = 0;
		
		return answer;
	}
	
	


	

	
}
