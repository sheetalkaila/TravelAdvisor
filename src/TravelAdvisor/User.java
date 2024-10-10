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

		while (type.equals("")) {
			System.out.println("Please enter type:");
			System.out.println("1.admin");
			System.out.println("2.user");
			type = input.nextLine();

			if (type.equals("1")) {
				type = "admin";
			} else if (type.equals("2")) {
				type = "user";
			} else {
				System.out.println("Please enter from the above choices only");
				type = "";
			}

		}

		while (tag.equals("")) {
			String[] allowedtaglist = { "History Buff", "Shopping Fanatic", "Beach Goer", "Urban Explorer",
					"Nature Lover", "Family Vacationer" };

			System.out.println("Please enter tag: ");
			for (int i = 0; i < allowedtaglist.length; i++) {
				System.out.println((i + 1) + "." + allowedtaglist[i]);
			}

			tag = input.nextLine();
			tag = ConvertToTagName(tag);

		}

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			rs = statement.executeQuery("select * from user where userId=    '" + userId + "'");

			if (rs.next()) {
				System.out.println("Id and password already exit");
			} else {
				conn.setAutoCommit(false);

				statement.executeUpdate("Insert into user values ('" + userId + "', '" + password + "', '" + type
						+ "', '" + tag + "')");

				conn.commit();
				conn.setAutoCommit(true);
				System.out.println("signup successful");
			}

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

	public String ConvertToTagName(String t) {

		switch (t) {
		case "1":
			return "History Buff";
		case "2":
			return "Shopping Fanatic";
		case "3":
			return "Beach Goer";
		case "4":
			return "Urban Explorer";
		case "5":
			return "Nature Lover";
		case "6":
			return "Family Vacationer";
		default:
			System.out.println("Please enter from the above choices only");
			return "";
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
				System.out.println("login successful");
				Attraction attraction = new Attraction();
				attraction.welcome(userId);
			} else {
				System.out.println("your userId or password is wrong");
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
