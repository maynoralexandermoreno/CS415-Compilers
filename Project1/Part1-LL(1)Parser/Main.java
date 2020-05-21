package application;
import java.util.Scanner;
import application.Parser;
public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Parser p = new Parser(in.nextLine());
		p.parse();
	}

}
