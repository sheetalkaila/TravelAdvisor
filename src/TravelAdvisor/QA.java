package TravelAdvisor;

import java.sql.Connection;
import java.sql.Date;
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
	Date dateAndTime;
	int Q_ID = 0;

	Scanner input = new Scanner(System.in);

	public void AddAnswer(Attraction a, String userid, int qid, String ans) {

		String type = "A";
		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			statement.executeUpdate("Insert into qanda (attractionName,	type,	userID,	content,	Q_ID) "
					+ "values ('" + a.name + "', '" + type + "', '" + userid + "','" + ans + "','" + qid + "')");

			statement.executeUpdate("Insert into Notification (userID, Q_ID, content, status	) " + "values ('"
					+ userid + "', '" + qid + "','" + ans + "','" + "unread" + "')");

			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("Answer created successfully!");

		} catch (SQLException e) {
			System.out.println("Answer creation failed!");
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

	public void ViewQAOptions(Attraction a, String userid) {

		String Selection = "";

		while (!Selection.equalsIgnoreCase("x")) {

			System.out.println("1. Ask a question");
			System.out.println("2. Answer a question");
			System.out.println("3. View all que & ans");
			System.out.println("4. MainMenu");
			System.out.println("X. Go back");

			Selection = input.nextLine();

			if (Selection.equals("1")) {
				AskQuestion(a, userid);
			} else if (Selection.equals("2")) {
				ViewQuestions(a, userid);
			} else if (Selection.equals("3")) {
				ViewallQA();
			} else if (Selection.equals("4")) {
				Attraction attraction = new Attraction();
				attraction.mainmenu(attraction, userid);
			}

		}

	}

	public void ViewQuestions(Attraction a, String userid) {
		System.out.println("Questions for attraction : " + a.name);

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			String q = "select * from qanda where attractionname = '" + a.name +  "' and Q_ID = 0 ";
			rs = statement.executeQuery(q);

			// to print list
			while (rs.next()) {
				System.out.println(rs.getString("autoid") + "." + rs.getString("content"));

			}
			System.out.println("Enter question id to answer");
			int qid = input.nextInt();
			input.nextLine();
			System.out.println("enter your Answer");
			String ans = input.nextLine();
			System.out.println(ans);

			AddAnswer(a, userid, qid, ans);

		} catch (SQLException e) {
			System.out.println("search failed!");
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

	public void AskQuestion(Attraction a, String userid)

	{
		attractionName = a.name;
		userID = userid;

		System.out.println("Please type in your question");
		content = input.nextLine();

		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			statement.executeUpdate(
					"Insert into qanda (attractionName,	type,	userID,	content,	Q_ID) " + "values ('"
							+ attractionName + "', '" + "Q" + "', '" + userID + "','" + content + "','" + "0" + "')");

			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("Question inserted successfully!");

		} catch (SQLException e) {
			System.out.println("Question insertion failed!");
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

	public void ViewallQA() {
		Connection conn = null;
		Statement statement = null;
		Statement statement1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();
			statement1 = conn.createStatement();

			String q = "select * from qanda  where type = 'Q' ";
			rs = statement.executeQuery(q);

			// to print list
			while (rs.next()) {
				System.out.println("Question:");
				System.out.println(rs.getString("content"));
				String qid = rs.getString("autoID");
				String q2 = "select * from qanda  where type = 'A' and q_id=" + qid;
				rs1 = statement1.executeQuery(q2);

				while (rs1.next()) {
					System.out.println(rs1.getString("content"));

				}
			}

		} catch (SQLException e) {
			System.out.println("search failed!");
			e.printStackTrace();

		} finally {
			try {
				conn.close();
				statement.close();
				statement1.close();
				rs.close();
				rs1.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}