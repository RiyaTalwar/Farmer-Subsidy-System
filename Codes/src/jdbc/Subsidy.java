package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;


public class Subsidy 
{
	private int Seeds_schid;
	private int Fertiliser_schid;
	private int Machine_schid;
	private int IrrigationEquip_schid;
	private ArrayList<Long> s_Appl=new ArrayList<Long>();
	private String type;
	Scanner sc=new Scanner(System.in);
	public int getSeeds_schid() {
		return Seeds_schid;
	}
	public void setSeeds_schid(int seeds_schid) {
		Seeds_schid = seeds_schid;
	}
	public int getFertiliser_schid() {
		return Fertiliser_schid;
	}
	public void setFertiliser_schid(int fertiliser_schid) {
		Fertiliser_schid = fertiliser_schid;
	}
	public int getMachine_schid() {
		return Machine_schid;
	}
	public void setMachine_schid(int machine_schid) {
		Machine_schid = machine_schid;
	}
	public int getIrrigationEquip_schid() {
		return IrrigationEquip_schid;
	}
	public void setIrrigationEquip_schid(int irrigationEquip_schid) {
		IrrigationEquip_schid = irrigationEquip_schid;
	}
	public ArrayList<Long> getS_Appl() {
		return s_Appl;
	}
	public void setS_Appl(ArrayList<Long> s_Appl) {
		this.s_Appl = s_Appl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void suggestion(String type,int fid)
	{
		String scheme;
		String ssoil="k",sclimate="k",scategory="k";
		Farmer f=new Farmer();
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt1= con.prepareStatement("select soil from farmersubsidy.farmer where f_id=?");
			stmt1.setInt(1, fid);
			ResultSet rs=stmt1.executeQuery();
			while(rs.next())
			{
			ssoil=rs.getString(1);
			//System.out.println(ssoil);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt2= con.prepareStatement("select climate from farmersubsidy.farmer where f_id=?");
			stmt2.setInt(1, fid);
			ResultSet rs=stmt2.executeQuery();
			while(rs.next())
			{
				sclimate=rs.getString(1);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt3= con.prepareStatement("select category from farmersubsidy.farmer where f_id=?");
			stmt3.setInt(1, fid);
			ResultSet rs=stmt3.executeQuery();
			while(rs.next())
			{
				scategory=rs.getString(1);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		//DB: find scheme corresponding to this soil,climate,category and return the scheme names
		//System.out.println(ssoil+sclimate+scategory);
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select sc_id from farmersubsidy.scheme where soil=? and climate=? and category=? and sc_type=? ");
			stmt.setString(1, ssoil);
			stmt.setString(2, sclimate);
			stmt.setString(3, scategory);
			stmt.setString(4, type);
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
	}
	public void add_scheme()
	{
		System.out.println("Enter the type of subsidy this scheme is for");
		String type=sc.nextLine();
		System.out.println("Enter the name of the scheme");
		String name=sc.nextLine();	
		System.out.println("Enter the soil type required for the scheme");
		String soil=sc.nextLine();
		System.out.println("Enter the climate condition required for the scheme");
		String climate=sc.nextLine();
		System.out.println("Enter the rate at which subsidy is given");
		String rate=sc.next();
		sc.nextLine();
		System.out.println("Enter the category of farmer applicable for this scheme");
		String category=sc.nextLine();
		Scheme s=new Scheme(type,name,soil,climate,rate,category);
	}
	public int apply_for_subsidy(int fid)
	{
		Scheme s=new Scheme();
		System.out.println("Enter the choice of subsidy you wish to apply for:");
		System.out.println("1. Seed");
		System.out.println("2. Fertilizer");
		System.out.println("3. Irrigation");
		System.out.println("4. Machine");
		int choice=sc.nextInt();
		if(choice==1)
		{
			Seeds_schid=1;
			s.display_schemeInfo("seeds");
			System.out.println("Suggestion for you:");
			suggestion("seeds",fid);
		}
		else if(choice==2)
		{
			Fertiliser_schid=1;
			s.display_schemeInfo("Fertiliser");
			System.out.println("Suggestion for you:");
			suggestion("Fertiliser",fid);
		}
		else if(choice==3)
		{
			IrrigationEquip_schid=1;
			s.display_schemeInfo("Irrigation");
			System.out.println("Suggestion for you:");
			suggestion("Irrigation",fid);
		}
		else if(choice==4)
		{
			Machine_schid=1;
			s.display_schemeInfo("Machinery");
			System.out.println("Suggestion for you:");
			suggestion("Machinery",fid);
		}
		System.out.println("Enter the scheme id you want to apply for");
		int s_id=sc.nextInt();
		Notifications n=new Notifications();
		n.notifyUser(s_id, "sRequest");
		//DB: enter in subsidy request table s_id, fid, status as pending
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("insert into farmersubsidy.subsidyrequest(s_id,fid,stat)"+"values(?,?,'pending')");
			stmt.setInt(1, s_id);
			stmt.setInt(2, fid);
			ResultSet rs=stmt.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return s_id;
	}
	public void display_subsidyInfo()
	{
		Scheme s=new Scheme();
		System.out.println("Enter the choice of subsidy whose information you want to see:");
		System.out.println("1. Seed");
		System.out.println("2. Fertilizer");
		System.out.println("3. Irrigation");
		System.out.println("4. Machine");
		int choice=sc.nextInt();
		if(choice==1)
		{
			Seeds_schid=1;
			s.display_schemeInfo("seeds");
		}
		else if(choice==2)
		{
			Fertiliser_schid=1;
			s.display_schemeInfo("Fertiliser");
		}
		else if(choice==3)
		{
			IrrigationEquip_schid=1;
			s.display_schemeInfo("Irrigation");
		}
		else if(choice==4)
		{
			Machine_schid=1;
			s.display_schemeInfo("Machinery");
		}
		String type=sc.nextLine();
		s.display_schemeInfo(type);	//display schemes with type=type
	}
	public String send_subsidyStatus(int s_id)
	{
		//DB: status corresponding to s_Appl id from subsidy requests
		String stat=null;
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select stat from farmersubsidy.subsidyrequest where s_id=?");
			stmt.setInt(1, s_id);
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				stat=rs.getString(1);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return stat;	
	}
	public void update_subsidyStatus(int app_id)
	{
		System.out.println("Enter status to be updated");
		String stat=sc.next();
		//DB: update status in db
		int fi=0;
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("update farmersubsidy.subsidyrequest set stat=?"+" where s_id=?");
			stmt.setString(1, stat);
			stmt.setInt(2, app_id);
			ResultSet rs=stmt.executeQuery();
			PreparedStatement stmt1= con.prepareStatement("select fid from farmersubsidy.subsidyrequest where s_id=?");
			stmt1.setInt(1, app_id);
			ResultSet rs1=stmt1.executeQuery();
			while(rs1.next())
			{
				fi=rs1.getInt(1);
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		 System.out.println("Status updated!!");
		// System.out.println(fid);
         Notifications n=new Notifications();
         n.notifyUser(app_id, "sUpdate", fi, "subsidy");
		
	}
}

