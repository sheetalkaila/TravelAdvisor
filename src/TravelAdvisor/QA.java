package TravelAdvisor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class QA {

	String attractionName;
	String type;
	String userID;
	String content;
	String dateAndTime;
	int Q_ID = 0;

	public void AddQA(Attraction a, String userid) {
		Scanner scanner = new Scanner(System.in);

		attractionName = a.name;
		userID = userid;
		dateAndTime = DateAndTime.Datetime();

		System.out.print("Enter the type: (Q or A) ");
		type = scanner.nextLine();

		System.out.print("Enter the content  ");
		content = scanner.nextLine();

		System.out.print("Enter the Q_ID: ");
		Q_ID = scanner.nextInt();

		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			statement.executeUpdate(
					"Insert into qanda (attractionName,	type,	userID,	content,	dateAndTime,	Q_ID) "
							+ "values ('" + attractionName + "', '" + type + "', '" + userID + "','" + content + "','"
							+ dateAndTime + "','" + Q_ID + "')");

			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("QA created successfully!");

		} catch (SQLException e) {
			System.out.println("QA creation failed!");
			e.printStackTrace();

		} finally {
			try {
				conn.close();
				statement.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void ViewQAOptions(Attraction a, String userid2) {
		String choose = "";
		while (!choose.equalsIgnoreCase("x")) {
			System.out.println("Select one from below:");
			System.out.println("1.Q & A");
			System.out.println("2.Write a review");
			System.out.println("3.Save to my favorite");
			System.out.println("x.Go back");

			choose = input.next();

			if (choose.equalsIgnoreCase("1")) {
				System.out.println("Q and A");
				QA qa = new QA();
				qa.ViewQAOptions(a,userid);
				qa.AddQA(a,userid);

			} else if (choose.equalsIgnoreCase("2")) {
				System.out.println("review");
				Review r = new Review();
				r.createReview(a, userid);

			} else if (choose.equalsIgnoreCase("3")) {
				System.out.println("fav");
				addAttractionToFav(userid, a.name);

			}
		}
		
	}

}
