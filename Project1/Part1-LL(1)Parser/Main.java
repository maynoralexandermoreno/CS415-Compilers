package application;
import java.util.Scanner;
import application.Parser;
public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("Please Enter String:");
			Parser p = new Parser(in.nextLine());
			p.parse();
		}
	}

}
