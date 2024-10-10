package TravelAdvisor;

import java.util.*;
import java.sql.*;

public class Attraction {

	String name = "";
	String tag = "";
	String city = "";
	String desc = "";
	double rating = 0;
	String Creator = "";
	String Status = "underProcess";

	Scanner input = new Scanner(System.in);

	public void createAttraction(String userId) {

		System.out.println("Enter Attraction name:");
		name = input.nextLine();

		while (tag.equals("")) {
			String[] allowedtaglist = { "History Buff", "Shopping Fanatic", "Beach Goer", "Urban Explorer",
					"Nature Lover", "Family Vacationer" };

			for (int i = 0; i < allowedtaglist.length; i++) {
				System.out.println((i + 1) + "." + allowedtaglist[i]);
			}

			tag = input.nextLine();
			User user = new User();
			tag = user.ConvertToTagName(tag);

		}

		System.out.println("Enter city:");
		city = input.nextLine();

		System.out.println("Enter desc:");
		desc = input.nextLine();

		System.out.println("Enter rating:");
		rating = input.nextDouble();

		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			statement.executeUpdate("Insert into attraction values ('" + name + "', '" + tag + "', '" + city + "', '"
					+ desc + "','" + rating + "','" + userId + "','underProcess')");

			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("Attraction create successful");

		} catch (SQLException e) {
			System.out.println("Attraction creation failed!");
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

	public void searchedAttraction() {
		String Selection = "";

		while (!Selection.equalsIgnoreCase("t") & !Selection.equalsIgnoreCase("c")) {
			System.out.println("Do you want to search by tag or city? type t for tag and c for city.");
			Selection = input.nextLine();// c or t

			System.out.println("Enter name");
			String name = input.nextLine();//
			String q = "select * from attraction where ";

			if (Selection.equalsIgnoreCase("c")) {
				q += "city= '" + name + "' order by rating desc";//
			} else if (Selection.equalsIgnoreCase("t")) {
				q += "tag like '%" + name + "%' order by rating desc";
			}

			Connection conn = null;
			Statement statement = null;
			ResultSet rs = null;

			try {
				conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
				statement = conn.createStatement();

				rs = statement.executeQuery(q);

				while (rs.next()) {
					System.out.println(rs.getString("attractionName"));
				}

				System.out.println("Select one attraction for information");
				String attr_inp = input.nextLine();
				rs = statement.executeQuery("select * from attraction where attractionName = '" + attr_inp + "'");
				if (rs.next()) {
					System.out.println("Attraction Name:" + rs.getString("attractionName") + ", Description:"
							+ rs.getString("description") + ", City:" + rs.getString("city") + ", Rating:"
							+ rs.getString("rating"));
				}
					else {
						System.out.println(attr_inp + " not found.");

					}
				
				String x = "";
				do {
					
				x=mainmenu();
				
				}while(x.equalsIgnoreCase("x"));	
				
				
				
				
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

	}

	public String mainmenu() {
		
		String choose = "";
		while(!choose.equalsIgnoreCase("x")) {
		System.out.println("Select one from below:");
		System.out.println("1.Q & A");
		System.out.println("2.Write a review");
		System.out.println("3.Save to my favorite");
		System.out.println("x.Go back");
		
		choose = input.next();
		
		if(choose.equalsIgnoreCase("1"))
		{
			System.out.println("Q and A");

		}
		else if(choose.equalsIgnoreCase("2")) 
		{
			System.out.println("review");

		}
		else if(choose.equalsIgnoreCase("3")) 
		{
			System.out.println("fav");

		}		
		}

		return choose;
	}
	
	public void searchedAttraction_() {
		String Selection = "";

		while (!Selection.equalsIgnoreCase("t") & !Selection.equalsIgnoreCase("c")) {
			System.out.println("Do you want to search by tag or city? type t for tag and c for city.");
			Selection = input.nextLine();

			if (Selection.equalsIgnoreCase("c")) {
				System.out.println("Enter city name");
				String city_name = input.nextLine();

				Connection conn = null;
				Statement statement = null;
				ResultSet rs = null;

				try {
					conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
					statement = conn.createStatement();

					rs = statement.executeQuery(
							"select * from attraction where city= '" + city_name + "' order by rating desc");

					while (rs.next()) {
						System.out.println(rs.getString("attractionName"));
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

			} else if (Selection.equalsIgnoreCase("t")) {
				System.out.println("Enter tag name");
				String tag_name = input.nextLine();

				Connection conn = null;
				Statement statement = null;
				ResultSet rs = null;

				try {
					conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
					statement = conn.createStatement();

					rs = statement.executeQuery(
							"select * from attraction where tag like '%" + tag_name + "%' order by rating desc");

					while (rs.next()) {
						System.out.println(rs.getString("attractionName"));
						System.out.println("------------");

					}
					System.out.println("Select one attraction for information");
					String attr_inp = input.nextLine();
					rs = statement.executeQuery("select * from attraction where attractionName = '" + attr_inp + "'");
					if (rs.next()) {
						System.out.println(rs.getString("attractionName") + ", " + rs.getString("description") + ", "
								+ rs.getString("city") + ", " + rs.getString("rating"));

					} 
					
					else {
						System.out.println(attr_inp + " not found.");

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

			}

		}

	}

	public void welcome(String userid) {

		String Selection = "";

		while (!Selection.equalsIgnoreCase("x")) {

			Connection conn = null;
			Statement statement = null;
			ResultSet rs = null;

			try {
				conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
				statement = conn.createStatement();

				String q = "select * from attraction order by rating desc limit 2";
				rs = statement.executeQuery(q);

				// to print list
				System.out.println("Top 2 attraction by rating");

				while (rs.next()) {
					System.out.println(rs.getString("attractionName") + " : " + rs.getString("rating"));
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

			System.out.println("Please make your selection: ");
			System.out.println("C. Create an attraction");
			System.out.println("S. Search for an attraction");
			System.out.println("F. My Favorite");
			System.out.println("N. Notification");
			System.out.println("X. Exit");

			Selection = input.nextLine();

			if (Selection.equals("1")) {
				System.out.println("attraction1");

			} else if (Selection.equals("2")) {
				System.out.println("attraction2");

			} else if (Selection.equals("c")) {
				createAttraction(userid);

			} else if (Selection.equals("s")) {
				searchedAttraction();
				
			} else if (Selection.equals("f")) {
				System.out.println("myfavAttraction");

			} else if (Selection.equals("n")) {
				System.out.println("Notification");

			}

		}
	}

}
