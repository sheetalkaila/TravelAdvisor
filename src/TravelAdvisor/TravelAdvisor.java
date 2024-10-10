package TravelAdvisor;

import java.util.Scanner;

public class TravelAdvisor {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		String Selection = "";
		
		while(!Selection.equalsIgnoreCase("x"))
		{
					
		System.out.println("Please make your selection: ");
		System.out.println("1. Sign up");
		System.out.println("2. Login");
		System.out.println("X. Exit");
		
		Selection = input.nextLine();
		
		User user = new User();
		
		if (Selection.equals("1"))
		{
			user.signup();
		}
		else if(Selection.equals("2"))
		{
			user.login();
		}
		
		}
	}

}
