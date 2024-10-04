package TravelAdvisor;

import java.sql.*;
import java.util.Scanner;

public class User {

	String userId = "";
	String password = "";
	String type = "";
	String tag = "";

	Scanner input = new Scanner(System.in);

	public void signup() {
		do {
			System.out.println("Please enter your userId: ");
			userId = input.nextLine();

		} while (!isValidId(userId));

		do {
			System.out.println("Please enter your Password: ");
			System.out.println("password can not same as loginId");
			password = input.nextLine();

		} while (userId.equals(password));

		while (!type.equals("1") & !type.equals("2")) {
			System.out.println("Please enter type:");
			System.out.println("1.admin");
			System.out.println("2.user");
			type = input.nextLine();

		}
		
		int x=1 ;
		
		while (!(Integer.toBinaryString(x) <= tag && tag <= 6.tostring())) 
		{
			System.out.println("Please enter tag: ");
			System.out.println("1.History Buff");
			System.out.println("2.Shopping Fanatic");
			System.out.println("3.Beach Goer");
			System.out.println("4.Urban Explorer");
			System.out.println("5.Nature Lover");
			System.out.println("6.Family Vacationer");
			tag = input.nextLine();
		}
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			rs = statement.executeQuery("select * from user where userId=    '" + userId + "'");

			if (rs.next()) {
				System.out.println(true);
			} else {
				System.out.println(false);
			}

			conn.setAutoCommit(false);

			statement.executeUpdate(
					"Insert into user values ('" + userId + "', '" + password + "', '" + type + "', '" + tag + "')");

			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("signup successful");

		} catch (SQLException e) {
			System.out.println("signup creation failed!");
			e.printStackTrace();

		} finally {
			try {
				conn.close();
				statement.close();
				rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private boolean isValidId(String id) {

		boolean isValid = true;

		if (id.equalsIgnoreCase("admin")) {
			isValid = false;
			System.out.println("id can not be 'admin'");

		}

		if (id.length() < 2 || id.length() > 11) {
			isValid = false;
			System.out.println("id must be 3 to 10 characters");

		}

		boolean hasLetter = false;
		boolean hasDigit = false;

		for (int i = 0; i < id.length(); i++) {
			char currentChar = id.charAt(i);

			if (Character.isLetter(currentChar)) {
				hasLetter = true;
			}
			if (Character.isDigit(currentChar)) {
				hasDigit = true;
			}
		}

		if (!hasLetter || !hasDigit) {
			isValid = false;
			System.out.println("id contains atleast 1 letter and 1 digit");

		}

		return isValid;
	}

	public void login() {
		System.out.println("Please enter your userId: ");
		userId = input.nextLine();

		System.out.println("Please enter your Password: ");
		password = input.nextLine();

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			rs = statement.executeQuery(
					"select * from user where userId= '" + userId + "'   and password= '" + password + "'");

			if (rs.next()) {
				System.out.println(true);
			} else {
				System.out.println(false);
			}

		} catch (SQLException e) {
			System.out.println("login failed!");
			e.printStackTrace();

		} finally {
			try {
				conn.close();
				statement.close();
				rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
