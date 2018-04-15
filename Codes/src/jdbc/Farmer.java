	package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;


public class Farmer {
	private int f_id;
	private String f_name;
	private String mobile_no;
	private String gender;
	private float area;
	private String category;
	private int income;
	private String soil,climate;
	private String state,district,village;
	private String password;
	private ArrayList<Integer> f_sid=new ArrayList<Integer>();
	private ArrayList<Integer> f_did=new ArrayList<Integer>();
	public long getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		int curid=0;
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select max(f_id) from farmersubsidy.farmer");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				curid=rs.getInt(1);
			}
			f_id=curid+1;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		this.f_id = f_id;	//from database
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public float getArea() {
		return area;
	}
	public void setArea(float area) {
		this.area = area;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public String getSoil() {
		return soil;
	}
	public void setSoil(String soil) {
		this.soil = soil;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Integer> getF_sid() {
		return f_sid;
	}
	public void setF_sid(ArrayList<Integer> f_sid) {
		this.f_sid = f_sid;
	}
	public ArrayList<Integer> getF_did() {
		return f_did;
	}
	public void setF_did(ArrayList<Integer> f_did) {
		this.f_did = f_did;
	}
	//to check whether mobile no. entered is valid
	public boolean checkMno(String mno)
	{
		if(mno.length()==10)
		{
			//DB: check database, shouldnt be already present
			try
			{
				Class.forName("org.postgresql.Driver");
				Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
				PreparedStatement stmt= con.prepareStatement("select mobile_no from farmersubsidy.farmer");
				ResultSet rs=stmt.executeQuery();
				while(rs.next())
				{
					if(mno==rs.getString(1))
						return false;
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			return true;
		}
		else
			return false;
	}
	//to check whether password entered is valid
	public boolean checkPwd(String pwd)
	{
		int a=0,n=0;
		if(pwd.length()>=6 && pwd.length()<=15)
		{
			for(int i=0;i<pwd.length();i++)
			{
				char c=pwd.charAt(i);
				if(Character.isDigit(c))
					++n;
				else
					++a;
					
			}
			if(a>0 && n>0)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public int application_form()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Registration Form");
		System.out.println("Enter name:");
		this.setF_name(sc.nextLine());
		System.out.println("Enter Mobile no.:");
		String mno=sc.nextLine();
		int f1=0;
		do
		{
			if(checkMno(mno))
				f1=1;
			else
			{
				System.out.println("Enter a valid mobile no.!!(Should have 10 digits)");
				mno=sc.nextLine();
			}
		}while(f1==0);
		this.setMobile_no(mno);
		System.out.println("Enter password:");
		String pwd=sc.nextLine();
		int f2=0;
		do
		{
			if(checkPwd(pwd))
				f2=1;
			else
			{
				System.out.println("Enter a valid password!!(6-15 chars, atleast 1 Numeric,atleast 1 alphabet)");
				pwd=sc.nextLine();
			}
		}while(f2==0);
		this.setPassword(pwd);	
		System.out.println("Enter gender: (M/F)");
		gender=sc.next();
		this.setGender(gender);
		System.out.println("Enter Land Area(in hectare):");
		area=sc.nextInt();
		this.setArea(area);
		sc.nextLine();
		System.out.println("Enter Farmer Category: (marginal/medium/large)");
		category=sc.nextLine();
		this.setCategory(category);
		System.out.println("Enter income:");
		income=sc.nextInt();
		this.setIncome(income);
		sc.nextLine();
		System.out.println("Enter soil type:");
		soil=sc.nextLine();
		this.setSoil(soil);
		System.out.println("Enter climate type:");
		climate=sc.nextLine();
		this.setClimate(climate);
		System.out.println("Enter state:");
		state=sc.nextLine();
		this.setState(state);
		System.out.println("Enter district:");
		district=sc.nextLine();
		this.setDistrict(district);
		System.out.println("Enter village:");
		village=sc.nextLine();
		this.setVillage(village);
		this.setF_id(f_id);
		//sc.close();
		//DB: add all the details to farmer database
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("insert into farmersubsidy.farmer(f_id,f_name,mobile_no,gender,area,category,income,soil,climate,state,district,village,pwd)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, f_id);
			stmt.setString(2, f_name);
			stmt.setString(3,mno);
			stmt.setString(4, gender);
			stmt.setFloat(5, area);
			stmt.setString(6, category);
			stmt.setInt(7, income);
			stmt.setString(8, soil);
			stmt.setString(9, climate);
			stmt.setString(10, state);
			stmt.setString(11, district);
			stmt.setString(12, village);
			stmt.setString(13, pwd);
			ResultSet rs=stmt.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return f_id;
	}
	
	public void update_profile(int fid)
	{
		//display previous details
		int choice;
		do
		{
			System.out.println("Enter the field you want to update from the following menu:");
			System.out.println("1. Name");
			System.out.println("2. Password");
			System.out.println("3. Mobile no.");
			System.out.println("4. Gender");
			System.out.println("5. Area");
			System.out.println("6. Category");
			System.out.println("7. Income");
			System.out.println("8. Soil");
			System.out.println("9. Climate");
			System.out.println("10.State");
			System.out.println("11.District");
			System.out.println("12.Village");
			System.out.println("13.Submit");
			Scanner sc=new Scanner(System.in);
			choice=sc.nextInt();
			switch(choice)
			{
			case 1: System.out.println("Enter name:");
					String n=sc.nextLine();
					this.setF_name(n);
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set f_name=?"+" where f_id=?");
						stmt.setString(1, n);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;
			case 2:	System.out.println("Enter password:");
					String pwd=sc.nextLine();
					int f2=0;
					do
					{
						if(checkPwd(pwd))
							f2=1;
						else
						{
							System.out.println("Enter a valid password!!(6-15 chars, atleast 1 Numeric,atleast 1 alphabet)");
							pwd=sc.nextLine();
						}
					}while(f2==0);
					this.setPassword(pwd);	
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set pwd=?"+" where f_id=?");
						stmt.setString(1, pwd);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;
			case 3:	System.out.println("Enter Mobile no.:");
					String mno=sc.nextLine();
					int f1=0;
					do
					{
						if(checkMno(mno))
							f1=1;
						else
						{
							System.out.println("Enter a valid mobile no.!!(Should have 10 digits)");
							mno=sc.nextLine();
						}
					}while(f1==0);
					this.setMobile_no(mno);
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set mobile_no=?"+" where f_id=?");
						stmt.setString(1, mno);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;
			case 4: System.out.println("Enter gender: (M/F)");
					String g=sc.next();
					this.setGender(g);//DB: update this field in database
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set gender=?"+" where f_id=?");
						stmt.setString(1, g);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					break;
			case 5:	System.out.println("Enter Land Area(in hectare):");
					float a=sc.nextInt();
					this.setArea(a);//DB: update this field in database
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set area=?"+" where f_id=?");
						stmt.setFloat(1, a);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					break;
			case 6:	System.out.println("Enter Farmer Category: (marginal/medium/large)");
					String c=sc.nextLine();
					this.setCategory(c);//DB: update this field in database
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set category=?"+" where f_id=?");
						stmt.setString(1, c);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					break;
			case 7:	System.out.println("Enter income:");
					int i=sc.nextInt();
					this.setIncome(i);
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set income=?"+" where f_id=?");
						stmt.setInt(1, i);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;
			case 8:	System.out.println("Enter soil type:");
					String s=sc.nextLine();
					this.setSoil(s);
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set soil=?"+" where f_id=?");
						stmt.setString(1, s);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;
			case 9:	System.out.println("Enter climate type:");
					String cl=sc.nextLine();
					this.setClimate(cl);
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set climate=?"+" where f_id=?");
						stmt.setString(1, cl);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;
			case 10:System.out.println("Enter state:");
					String sta=sc.nextLine();
					this.setState(sta);//DB: update this field in database
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set state=?"+" where f_id=?");
						stmt.setString(1, sta);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					break;
			case 11:System.out.println("Enter district:");
					String d=sc.nextLine();
					this.setDistrict(d);
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set district=?"+" where f_id=?");
						stmt.setString(1, d);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;
			case 12:System.out.println("Enter village:");
					String v=sc.nextLine();
					this.setVillage(v);
					try
					{
						Class.forName("org.postgresql.Driver");
						Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
						PreparedStatement stmt= con.prepareStatement("update farmersubsidy.farmer set village=?"+" where f_id=?");
						stmt.setString(1, v);
						stmt.setInt(2, fid);
						ResultSet rs=stmt.executeQuery();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}//DB: update this field in database
					break;			
			}
			//sc.close();
		}while(choice!=13);	
		//DB:display new details
		System.out.println("Your updated details are");
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select * from farmersubsidy.farmer where f_id=?");
			stmt.setInt(1, fid);
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				System.out.println("ID:"+rs.getInt(1));
				System.out.println("Name:"+rs.getString(2));
				System.out.println("Mobile No:"+rs.getString(3));
				System.out.println("Gender"+rs.getString(4));
				System.out.println("Area:"+rs.getFloat(5));
				System.out.println("Category:"+rs.getString(6));
				System.out.println("Income:"+rs.getInt(7));
				System.out.println("Soil:"+rs.getString(8));
				System.out.println("Climate:"+rs.getString(9));
				System.out.println("State:"+rs.getString(10));
				System.out.println("District:"+rs.getString(11));
				System.out.println("Village:"+rs.getString(12));
				System.out.println("Password:"+rs.getString(13));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	//Farmer gets notified whenever there is a new subsidy that has been added or if his document has been approved or if his subsidy has been sanctioned
	public void notifyFarmer(int id)
	{


	}
	//To track the status of subsidy request, it calls send_subsidyStatus() of subsidy class
	public void get_subsidy_status(int fid)
	{
		Subsidy s=new Subsidy();
		System.out.println("Enter the subsidy application id whose status you want to know from your subsidy applications:");
		/*for(int i=0;i<f_sid.size();i++)
		{
			System.out.println(f_sid.get(i));
		}*/
		
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select s_id from farmersubsidy.subsidyrequest where fid=?");
			stmt.setInt(1,fid);
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
		Scanner sc=new Scanner(System.in);
		int sid=sc.nextInt();
		String status=s.send_subsidyStatus(sid);
		System.out.println(sid+" : "+status);
		//sc.close();
		
	}
	//To get the subsidy information
	public void request_subsidyInfo()
	{
		Subsidy s=new Subsidy();
		s.display_subsidyInfo();
	}
	//To upload a document
	public void request_doc_upload(int fid)
	{
		Documents d=new Documents();
		f_did.add(d.doc_upload(fid));
	}
	//To get the status of document verification
	public void get_doc_status(int fid)
	{
		Documents d=new Documents();
		System.out.println("Enter the document id whose status you want to know from your uploaded documents:");
		//DB: display all dids from document request table corresponding to this fid
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select d_id from farmersubsidy.documents where fid=?");
			stmt.setInt(1, fid);
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
		Scanner sc=new Scanner(System.in);
		int did=sc.nextInt();
		String status=d.send_docStatus(did);
		System.out.println(did+" : "+status);
		//sc.close();
	}
	//to apply for a subsidy
	public void request_for_subsidy(int fid)
	{
		Subsidy s=new Subsidy();
		f_sid.add(s.apply_for_subsidy(fid));
	}
}


