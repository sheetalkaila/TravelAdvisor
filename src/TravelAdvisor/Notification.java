package TravelAdvisor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Notification {

	String userID;
	Date dateAndTime;
	String content;
	String status;
	int Q_ID = 0;

	Scanner input = new Scanner(System.in);

	public String getNotification(String userid) {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			String q = "select count(1) No_of_Notification from qanda qa,Notification n where qa.autoID = n.Q_ID and status = 'unread' and  qa.userID ='"
					+ userid + "' ";
			rs = statement.executeQuery(q);

			while (rs.next()) {
				return rs.getString("No_of_Notification");
			}

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
		return "0";

	}

	public void changeNotificationtoRead(String userid) {
		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			String q = "update Notification set status ='read' where q_id in (select autoID from qanda where userId = '"
					+ userid + "')";
			statement.executeUpdate(q);

			conn.commit();
			conn.setAutoCommit(true);

			System.out.println("Changed Status Success.");
			input.nextLine();

		} catch (SQLException e) {
			System.out.println("Changed Status failed.");
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
