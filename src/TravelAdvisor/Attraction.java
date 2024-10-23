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
					"Nature lover", "Family vacationer" };

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

		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			statement.executeUpdate("Insert into attraction values ('" + name + "', '" + tag + "', '" + city + "', '"
					+ desc + "','" + "0" + "','" + userId + "','underProcess')");

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

	public void searchedAttraction(String userid) {
		String Selection = "";

		while (!Selection.equalsIgnoreCase("t") & !Selection.equalsIgnoreCase("c")) {
			System.out.println("Do you want to search by tag or city? type t for tag and c for city.");
			Selection = input.nextLine();// c or t

			System.out.println("Enter name");
			String name = input.nextLine();//
			String q = "select * from attraction where  status='approved' and ";

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
				rs = statement.executeQuery(
						"select * from attraction where  status='approved' and attractionName = '" + attr_inp + "'");
				if (rs.next()) {
					System.out.println("Attraction Name:" + rs.getString("attractionName") + ", Description:"
							+ rs.getString("description") + ", City:" + rs.getString("city") + ", Rating:"
							+ rs.getString("rating"));
				} else {
					System.out.println(attr_inp + " not found.");

				}

				Attraction a = new Attraction();
				a.name = rs.getString("attractionName");
				a.desc = rs.getString("description");
				a.city = rs.getString("city");
				a.rating = Double.parseDouble(rs.getString("rating"));
				a.tag = rs.getString("tag");

				String x = "";
				do {

					x = mainmenu(a,userid);

				} while (x.equalsIgnoreCase("x"));

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

	public String mainmenu(Attraction a, String userid) {

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
				qa.ViewQAOptions(a, userid);

			} else if (choose.equalsIgnoreCase("2")) {
				System.out.println("review");
				Review r = new Review();
				r.createReview(a, userid);

			} else if (choose.equalsIgnoreCase("3")) {
				System.out.println("fav");
				addAttractionToFav(userid, a.name);

			}
			else if (choose.equalsIgnoreCase("x")) {
				Attraction attraction = new Attraction();
				attraction.welcome(userid,a.tag);
				

			}
		}

		return choose;
	}

	public void addAttractionToFav(String userid, String attName) {
		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			statement.executeUpdate(
					"Insert into favorite (userID	,attractionName) values ('" + userid + "', '" + attName + "')");

			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("Added to favorite successfully!");

		} catch (SQLException e) {
			System.out.println("Adding to favorite failed!");
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

					rs = statement.executeQuery("select * from attraction where  status='approved'  and city= '"
							+ city_name + "' order by rating desc");

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

					rs = statement.executeQuery("select * from attraction where  status='approved'  and tag like '%"
							+ tag_name + "%' order by rating desc");

					while (rs.next()) {
						System.out.println(rs.getString("attractionName"));
						System.out.println("------------");

					}
					System.out.println("Select one attraction for information");
					String attr_inp = input.nextLine();
					rs = statement
							.executeQuery("select * from attraction where  status='approved'  and attractionName = '"
									+ attr_inp + "'");
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

	public void welcome(String userid, String tag) {
		List<Attraction> a = new ArrayList<>();
		String Selection = "";

		while (!Selection.equalsIgnoreCase("x")) {

			Connection conn = null;
			Statement statement = null;
			ResultSet rs = null;


			try {
				conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
				statement = conn.createStatement();


				String q = "select * from attraction where status='approved' and tag='"+tag+"' order by rating desc limit 2";
				rs = statement.executeQuery(q);

				// to print list
				System.out.println("You May Like.....");
			
				

				while (rs.next()) {
					Attraction aa = new Attraction();
					aa.name = rs.getString("attractionName");
					aa.tag = rs.getString("tag");
					aa.city= rs.getString("city");
					aa.desc = rs.getString("description");
					aa.rating = rs.getDouble("rating");
					aa.Creator = rs.getString("creator");
					a.add(aa);
				}
				
				int c = 0;
				if(a.size()>0) {
				for(int i = 0;i<a.size();i++) {
					System.out.println(++c +"."+ a.get(i).name);
					if(c>=2)break;
				}}
				

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

			System.out.println("C. Create an attraction");
			System.out.println("S. Search for an attraction");
			System.out.println("F. My Favorite");
			Notification n = new Notification();

			System.out.println("N. Notification (" + n.getNotification(userid) + ")");
			System.out.println("X. Exit");
			System.out.println("Please make your selection: ");

			Selection = input.nextLine();

			if (Selection.equals("1")) {
				if(a.size()>0)System.out.println("AttractionName-"+a.get(0).name+",Tag-"+a.get(0).tag+",City-"+a.get(0).city+",Description-"+a.get(0).desc+",Rating-"+a.get(0).rating+",Creator-"+a.get(0).Creator);

			} else if (Selection.equals("2")) {
				if(a.size()>1)System.out.println("AttractionName-"+a.get(1).name+",Tag-"+a.get(1).tag+",City-"+a.get(1).city+",Description-"+a.get(1).desc+",Rating-"+a.get(1).rating+",Creator-"+a.get(1).Creator);

			} else if (Selection.equals("c")) {
				createAttraction(userid);

			} else if (Selection.equals("s")) {
				System.out.println("searching Attraction .........");
				searchedAttraction(userid);

			} else if (Selection.equals("f")) {
				System.out.println("fetching myfavAttraction .........");
				getfavAttraction(userid);

			} else if (Selection.equals("n")) {
				System.out.println("Notification");
				n.changeNotificationtoRead(userid);

			}

		}
	}

	public void getfavAttraction(String userid) {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			conn.setAutoCommit(false);

			rs = statement.executeQuery("select *  from favorite where userid = '" + userid + "'");

			if (rs.next()) {
				System.out.println("myfavAttraction : " + rs.getString("attractionName"));
			} else {
				System.out.println("No favorite attraction yet.");
			}
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			System.out.println("fav creation failed!");
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

	public void adminwelcome() {
		System.out.println("Attractions");
		viewAllAttractions();

	}


	public void viewAllAttractions() {
		String Selection = "";

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			String q = "select * from attraction";
			rs = statement.executeQuery(q);

			while (rs.next()) {
				System.out.println(rs.getString("attractionName") + " - " + rs.getString("status"));
			}

			System.out.println("Enter name of attraction to change the status");
			String att_name = input.nextLine();
			System.out.println("Enter Status : approved or reject");
			String att_status = input.nextLine();

			changeAttractionStatus(att_name, att_status);

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

	public void changeAttractionStatus(String att_name, String att_status) {
		Connection conn = null;
		Statement statement = null;

		try {
			conn = DriverManager.getConnection(DBConnection.url, DBConnection.username, DBConnection.password);
			statement = conn.createStatement();

			String q = "update attraction set status ='" + att_status + "' where attractionName='" + att_name + "'";
			statement.executeUpdate(q);

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
