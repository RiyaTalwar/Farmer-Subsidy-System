package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Account 
{
	private int aadhaar_no;	//Stores aadhaar no. of the farmer
	private String account_no;	//Specifies the account number
	public int getAadhaar_no() {
		return aadhaar_no;
	}
	public void setAadhaar_no(int aadhaar_no,int fid) {
		this.aadhaar_no = aadhaar_no;	//DB: set also to db
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("insert  into farmersubsidy.account(aadhar_no,account_no,fid)"+"values(?,?,?)");
			stmt.setInt(1,aadhaar_no);
			stmt.setString(2,null);
			stmt.setInt(3,fid);
			ResultSet rs=stmt.executeQuery();
		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public String getAccount_no() {
		return account_no;			//DB: from db
	}
	public void setAccount_no(String account_no,int fid) {
		if(verifyAccount(account_no))
		{
			this.account_no = account_no;
			try
			{
				Class.forName("org.postgresql.Driver");
				Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
				/*PreparedStatement stmt= con.prepareStatement("insert account_no into farmersubsidy.account where fid=?");
				stmt.setInt(1,fid);
				ResultSet rs=stmt.executeQuery();*/
				PreparedStatement stmt= con.prepareStatement("insert  into farmersubsidy.account(aadhar_no,account_no,fid)"+"values(?,?,?)");
				stmt.setString(1,null);
				stmt.setString(2,account_no);
				stmt.setInt(3,fid);
				ResultSet rs=stmt.executeQuery();
		
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		//DB: set also to db
		else
			System.out.println("Enter a valid account no. This account is not linked to your aadhaar card.");
	}
	public void transaction() 
	{
		//The government employee calls this method to send subsidy amount 
		//to the farmer’s account by accessing the account_no of that farmer

	}
	//DB: To verify that the account no. and corresponding Aadhaar number entered are genuine by looking at the database for this combination
	public boolean verifyAccount(String accno)
	{
		String ac=null;
		String ad=null;
		String ad1=null;
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
			PreparedStatement stmt= con.prepareStatement("select aadhaar_no,account_no from farmersubsidy.linkaccount");
			PreparedStatement stmt1= con.prepareStatement("select aadhaar_no from farmersubsidy.linkaccount where account_no=?");
			stmt1.setString(1, accno);
			ResultSet rs=stmt.executeQuery();
			ResultSet rs1=stmt1.executeQuery();
			while(rs.next())
			{
				ad=rs.getString(1);
				ac=rs.getString(2);
			}
			while(rs.next())
			{
				ad1=rs.getString(1);
			}
			if(ad==ad1 && ac==accno)
				return false;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return true;		
	}
}

