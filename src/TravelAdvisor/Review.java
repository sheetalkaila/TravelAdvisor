package TravelAdvisor;

import java.sql.Connection;
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
	String dateAndTime;
	
	public void createReview(Attraction a, String userid) {
		Scanner input = new Scanner(System.in);	        
	        
	        System.out.print("Enter your review content: ");
	        content = input.nextLine();
	        
	        System.out.print("Enter your score: ");
	        score = input.nextLine();	                
	        
	        attractionName = a.name;
	        userID = userid;
	        
	        dateAndTime = DateAndTime.Datetime();
	        
	        Connection conn = null;
			Statement statement = null;
			ResultSet rs = null;

			try {
				conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
				statement = conn.createStatement();

				conn.setAutoCommit(false);

				statement.executeUpdate("Insert into review (attractionName,	userID,	content,	score,	dateAndTime) values ('" + attractionName + "', '" + userID + "', '"
						+ content + "','" + score + "','" + dateAndTime + "')");
				
				rs = statement.executeQuery("select avg(score) avgScore from review where attractionName = '" + attractionName + "'");
				double avgScore = 0;
				if(rs.next()) {
					avgScore = Double.parseDouble(rs.getString("avgScore"));
				}
				statement.executeUpdate("update attraction set rating ="+ avgScore +" where attractionName = '" + attractionName + "'");
				

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
