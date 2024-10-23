package TravelAdvisor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Review {

	String attractionName;
	String userID;
	String content;
	String score;
	Date dateAndTime;

	public void createReview(Attraction a, String userid) {
		Scanner input = new Scanner(System.in);

		System.out.print("Enter your review content: ");
		content = input.nextLine();

		System.out.print("Enter your score between 1 to 5(1 is lowest and 5 is highest): ");
		score = input.nextLine();

		attractionName = a.name;
		userID = userid;

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			statement.executeUpdate("Insert into review (attractionName,	userID,	content,	score) values ('"
					+ attractionName + "', '" + userID + "', '" + content + "','" + score + "')");

			rs = statement.executeQuery(
					"select avg(score) avgScore from review where attractionName = '" + attractionName + "'");
			double avgScore = 0;
			if (rs.next()) {
				avgScore = Double.parseDouble(rs.getString("avgScore"));
			}
			statement.executeUpdate(
					"update attraction set rating =" + avgScore + " where attractionName = '" + attractionName + "'");

			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("Review created successfully!");

		} catch (SQLException e) {
			System.out.println("Review creation failed!");
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

}
