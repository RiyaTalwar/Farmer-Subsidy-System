package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Government {
	private long g_id;
	private String username;
	private String password;
	private ArrayList<Long> g_sid=new ArrayList<Long>();
	private ArrayList<Long> g_did=new ArrayList<Long>();
	Scanner sc= new Scanner(System.in);
	public long getG_id() {
		return g_id;
	}
	public void setG_id(long g_id) {
		this.g_id = g_id;
	}
	public ArrayList<Long> getG_sid() {
		return g_sid;
	}
	public void setG_sid(ArrayList<Long> g_sid) {
		this.g_sid = g_sid;
	}
	public ArrayList<Long> getG_did() {
		return g_did;
	}
	public void setG_did(ArrayList<Long> g_did) {
		this.g_did = g_did;
	}
	public void verify_Doc()
	{	
		System.out.println("List of unverified docs:");
		//DB: display all dids from document request table with status= no
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select d_id from farmersubsidy.documents where status=?");
			stmt.setString(1, "pending");
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
		System.out.println("Enter the doc-id to verify:");
		int did=sc.nextInt();
		Documents d=new Documents();
		d.update_status(did);	
		//g_did.remove(did);	//DB: remove from db too
		/*try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("delete from farmersubsidy.documents where d_id=?");
			stmt.setInt(1,did);
			ResultSet rs=stmt.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}*/
	}
	public void update_subsidyStatus()
	{
		System.out.println("List of subsidy requests:");
		//DB: display all sids from subsidy request table that are not sanctioned
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select s_id from farmersubsidy.subsidyrequest where stat=?");
			stmt.setString(1, "pending");
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
		System.out.println("Enter the s-id whose status you want to update:");
		int sid=sc.nextInt();
		Subsidy s=new Subsidy();
		s.update_subsidyStatus(sid);
		if(s.send_subsidyStatus(sid)=="sanctioned")
		{
			Documents d=new Documents();
			d.generate_receipt(sid);
			/*g_sid.remove(sid);	//DB: remove from db too
			try
			{
				Class.forName("org.postgresql.Driver");
				Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
				PreparedStatement stmt= con.prepareStatement("delete from farmersubsidy.subsidyrequest where s_id=?");
				stmt.setInt(1,sid);
				ResultSet rs=stmt.executeQuery();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}*/
		}
	}
	public void update_subsidyScheme()
	{
		Scheme s=new Scheme();
		System.out.println("Enter your choice:");
		System.out.println("1. Delete a scheme");
		System.out.println("2. Update a scheme");
		int choice=sc.nextInt();
		switch(choice)
		{
		case 1: s.withdrawal();
				break;
		case 2: s.update_scheme();
				break;
		}
	}
	public void notifyGov(int gid)
	{
		//db alerts table print all msgs corresponding to gid
			
	}

}


