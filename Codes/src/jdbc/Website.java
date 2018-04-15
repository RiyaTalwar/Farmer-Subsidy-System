package jdbc;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Website {
	private static ArrayList<Long> auth_fid=new ArrayList<Long>();	//not reqd
	private static ArrayList<Long> auth_gid=new ArrayList<Long>();	//not reqd
	private static int id;
	private static String type;	//current user id and type
	
	public static ArrayList<Long> getAuth_fid() {
		return auth_fid;
	}
	public static void setAuth_fid(ArrayList<Long> auth_fid) {
		Website.auth_fid = auth_fid;
	}
	public static ArrayList<Long> getAuth_gid() {
		return auth_gid;
	}
	public static void setAuth_gid(ArrayList<Long> auth_gid) {
		Website.auth_gid = auth_gid;
	}
	public static long getId() {
		return id;
	}
	public static void setId(int id) {
		Website.id = id;
	}
	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		Website.type = type;
	}
	public static void login()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the username");
		String u=sc.nextLine();
		System.out.println("Enter the password");
		String pwd=sc.nextLine();
		authenticate(u,pwd);
		//sc.close();
	}
	public static void signUp()
	{
		Farmer f=new Farmer();
		Website.id=f.application_form();
		Website.type="farmer";	
	}
	public static void authenticate(String u,String pwd)
	{
		//DB: check u and pwd combination is in database and get fid or gid 
		//if not ask to enter correct details
		int flag=0;
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select mobile_no,pwd,f_id as id from farmersubsidy.farmer");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				//System.out.println(rs.getString(1)+rs.getString(2));
				if(rs.getString(1).equals(u) && rs.getString(2).equals(pwd))
				{
					flag=1;
					Website.id=rs.getInt(3);
					Website.type="farmer";
					break;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		//DB: 'type' variable to be set farmer or gov  after authentication
		if(flag==0)
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
				PreparedStatement stmt= con.prepareStatement("select username,pwd,g_id as id from farmersubsidy.government");
				ResultSet rs=stmt.executeQuery();
				while(rs.next())
				{
					//System.out.println(rs.getString(1)+rs.getString(2));
					if(rs.getString(1).equals(u) && rs.getString(2).equals(pwd))
					{
						flag=1;
						Website.id=rs.getInt(3);
						Website.type="government";
						break;
					}
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
		}
		if(flag==0)
		{
			System.out.println("Invalid credentials.");
		}
	}
	public static void main(String[] args)
	{
		Website.setId(-1);		//initially nobody is logged in, if someone logs in we store that 
		//unique fid or gid in this id so that the functionalities of the website can be used accordingly
		Scanner sc = new Scanner(System.in);
		int choice=0;
		do {
		System.out.println("Enter your choice:");
		System.out.println("1. Signup");
		System.out.println("2. Login");
		System.out.println("3. Subsidy Information");
		System.out.println("4. Close Website");
		choice=sc.nextInt();
		switch(choice)
		{
		case 1: Website.signUp();
				break;
		case 2: Website.login();
				if(Website.type=="farmer")
				{
					Farmer f=new Farmer();
					f.notifyFarmer(id);
				}
				else
				{
					Government g=new Government();
					g.notifyGov(id);
				}
				break;
		case 3: Farmer f=new Farmer();
				f.request_subsidyInfo();
				break;				
		}
		int ch;
		if(Website.id!=-1)		//user has logged in so he can access further functionalities of the website
		{
			if(type=="farmer")
			{
				do
				{
				System.out.println("Enter your choice:");
				System.out.println("1. Update Profile");
				System.out.println("2. Document upload");
				System.out.println("3. Check document status");
				System.out.println("4. Subsidy Information");
				System.out.println("5. Apply for subsidy");
				System.out.println("6. Check subsidy request status");
				System.out.println("7. Set account number");
				System.out.println("8. Set aadhaar number");
				System.out.println("9. Log out");
				ch=sc.nextInt();
				Farmer f=new Farmer();
				Account a=new Account();
				switch(ch)
				{
				case 1:	f.update_profile(id);
					   	break;
				case 2:	f.request_doc_upload(id);
						break;
				case 3: f.get_doc_status(id);
						break;
				case 4:	f.request_subsidyInfo();
						break;
				case 5:	f.request_for_subsidy(id);
						break;
				case 6:	f.get_subsidy_status(id);
						break;
				case 7:	System.out.println("Enter your account no.");
						String account_no=sc.next();
						a.setAccount_no(account_no,id);
						break;
				case 8:	System.out.println("Enter your aadhaar no.");
						int aadhaar_no=sc.nextInt();
						a.setAadhaar_no(aadhaar_no,id);
						break;
						
				}
				}while(ch!=9);
			}
			else if(type=="government")
			{
				do
				{
				System.out.println("Enter your choice:");
				System.out.println("1. Verify Document");
				System.out.println("2. Update subsidy request status");
				System.out.println("3. Add subsidy scheme");
				System.out.println("4. Update subsidy scheme");
				System.out.println("5. Log out");
				ch=sc.nextInt();
				sc.nextLine();
				Government g=new Government();
				Subsidy s=new Subsidy();
				switch(ch)
				{
				case 1:	g.verify_Doc();
					   	break;
				case 2:	g.update_subsidyStatus();
						break;
				case 3:	s.add_scheme();
						break;
				case 4:	g.update_subsidyScheme();
						break;
				}
				}while(ch!=5);
			}
		}
		//sc.close();
		}while(choice!=4);
	}
	
}

