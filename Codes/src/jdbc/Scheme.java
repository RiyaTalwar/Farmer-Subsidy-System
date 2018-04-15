package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

//DB: all gets will be from database and sets
public class Scheme {
	private String type;
	private String name;
	private String soil;
	private String climate;
	private String rate;
	private String category;
	private int s_id;
	public Scheme(String type,String name,String soil,String climate,String rate,String category)
	{
		this.type=type;
		this.name=name;
		this.soil=soil;
		this.climate=climate;
		this.rate=rate;
		this.category=category;
		//DB: store these in DB with a new s_id and return this s_id which will be stored in s_id of this class
		int curid=0;
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select max(sc_id) from farmersubsidy.scheme");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				curid=rs.getInt(1);
			}
			curid=curid+1;
			PreparedStatement stmt1= con.prepareStatement("insert into farmersubsidy.scheme (sc_id,sc_type,sc_name,soil,climate,rate,category)"+" values(?,?,?,?,?,?,?)");
			stmt1.setInt(1, curid);
			stmt1.setString(2, type);
			stmt1.setString(3,name);
			stmt1.setString(4, soil);
			stmt1.setString(5, climate);
			stmt1.setString(6, rate);
			stmt1.setString(7, category);
			ResultSet rs1=stmt1.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		Notifications n=new Notifications();
		n.notifyUser(curid, "sNew",0, type);
	}
	public Scheme()
	{
		//constructor
	}
	public void display_schemeInfo(String type)
	{
		//DB: from db print info(such as type,name,soil,sid,...etc) of all schemes with this type
		//store these in the variables of this class and then print
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select sc_id,sc_type,sc_name,soil,climate,rate,category from farmersubsidy.scheme where sc_type=?");
			stmt.setString(1,type);
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6)+"  "+rs.getString(7));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void withdrawal()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Scheme ids:");
		//DB: print all scheme ids from db
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select sc_id from farmersubsidy.scheme");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("Enter the scheme id of the scheme you want to withdraw:");
		s_id=sc.nextInt();
		//DB: delete this sid tuple from database
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("delete from farmersubsidy.scheme where sc_id=?");
			stmt.setInt(1, s_id);
			ResultSet rs=stmt.executeQuery();
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("The scheme has been withdrawn successfully!");
		//();
	}
	public void update_scheme()
	{
		Scanner sc=new Scanner(System.in);

		System.out.println("Scheme ids:");
		//DB: print all scheme ids from db
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select sc_id from farmersubsidy.scheme");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString(1));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("Enter the scheme id of the scheme you want to update:");
		s_id=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter your choice of field you want to update:");
		System.out.println("1. type");
		System.out.println("2. name");
		System.out.println("3. soil");	
		System.out.println("4. climate");
		System.out.println("5. rate");
		System.out.println("6. category");
		int choice=sc.nextInt();
		switch(choice)
		{
			case 1:	
			{
				System.out.println("Enter the new type");
				type=sc.next();	//DB: update this to database
				try
	    		{
	    			Class.forName("org.postgresql.Driver");
	    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
	    			PreparedStatement stmt= con.prepareStatement("update farmersubsidy.scheme set sc_type=? where sc_id=?");
	    			stmt.setString(1,type);
	    			stmt.setInt(2,s_id);
	    			ResultSet rs=stmt.executeQuery();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println(e.getMessage());
	    		}
				break;
			}
			case 2:	
			{
					System.out.println("Enter the new name");
					name=sc.nextLine();	//DB: update this to database
					try
		    		{
		    			Class.forName("org.postgresql.Driver");
		    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
		    			PreparedStatement stmt= con.prepareStatement("update farmersubsidy.scheme set sc_name=?");
		    			stmt.setString(1,name);
		    			ResultSet rs=stmt.executeQuery();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println(e.getMessage());
		    		}
					break;
			}
			case 3:	
			{
					System.out.println("Enter the new soil");
					soil=sc.nextLine();	//DB: update this to database
					try
		    		{
		    			Class.forName("org.postgresql.Driver");
		    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
		    			PreparedStatement stmt= con.prepareStatement("update farmersubsidy.scheme set soil=?");
		    			stmt.setString(1,soil);
		    			ResultSet rs=stmt.executeQuery();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println(e.getMessage());
		    		}
					break;
			}
			case 4:	
			{
					System.out.println("Enter the new climate");
					climate=sc.nextLine();	//DB: update this to database
					try
		    		{
		    			Class.forName("org.postgresql.Driver");
		    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
		    			PreparedStatement stmt= con.prepareStatement("update farmersubsidy.scheme set climate=?");
		    			stmt.setString(1,climate);
		    			ResultSet rs=stmt.executeQuery();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println(e.getMessage());
		    		}
					break;
			}
			case 5:	
			{
					System.out.println("Enter the new rate");
					rate=sc.next();	//DB: update this to database
					try
		    		{
		    			Class.forName("org.postgresql.Driver");
		    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
		    			PreparedStatement stmt= con.prepareStatement("update farmersubsidy.scheme set rate=?");
		    			stmt.setString(1,rate);
		    			ResultSet rs=stmt.executeQuery();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println(e.getMessage());
		    		}
					break;
			}
			case 6:	
			{
					System.out.println("Enter the new category");
					category=sc.nextLine();	//DB: update this to database
					try
		    		{
		    			Class.forName("org.postgresql.Driver");
		    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
		    			PreparedStatement stmt= con.prepareStatement("update farmersubsidy.scheme set category=?");
		    			stmt.setString(1,category);
		    			ResultSet rs=stmt.executeQuery();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println(e.getMessage());
		    		}
					break;
			}
		}
		System.out.println("The scheme has been updated successfully!");
		//sc.close();
	}
	

	
}


