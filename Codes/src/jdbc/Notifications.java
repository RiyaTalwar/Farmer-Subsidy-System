package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class Notifications 
{
    private String message;
    private ArrayList<Long>f_id=new ArrayList<Long>();
    private ArrayList<Long>g_id=new ArrayList<Long>();
    
    public ArrayList<Long> getF_id() {
		return f_id;
	}
	public void setF_id(ArrayList<Long> f_id) {
		this.f_id = f_id;
	}
	public ArrayList<Long> getG_id() {
		return g_id;
	}
	public void setG_id(ArrayList<Long> g_id) {
		this.g_id = g_id;
	}
	public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void notifyUser(int id,String message)	//government
    {   
        int curid=0;    
    	if(message.equals("document request"))		//when document is uploaded, request to government for verification
            {
            	//??db last index=index+1,gid from random g_id from goverment table,message="d_id + document reqest"
            	try
        		{
        			Class.forName("org.postgresql.Driver");
        			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
        			PreparedStatement stmt= con.prepareStatement("select max(index) from farmersubsidy.alerts");
        			ResultSet rs=stmt.executeQuery();
        			while(rs.next())
        			{
        				curid=rs.getInt(1);
        			}
        			curid=curid+1;
        			PreparedStatement stmt1= con.prepareStatement("select g_id from farmersubsidy.government");
        			ResultSet rs1=stmt1.executeQuery();
        			ArrayList<Integer>a=new ArrayList<Integer>();
        			while(rs1.next())
        			{
        				System.out.println(rs1.getInt(1));
        				a.add(rs1.getInt(1));
        			}
        			Random r= new Random();
        			int gid=r.nextInt(a.size());
        			//System.out.println(gid+message+curid);
        			
        			message="document request added : "+id;
        			PreparedStatement stmt2= con.prepareStatement("insert into farmersubsidy.alerts(id,message,index)"+"values(?,?,?)" );
        			stmt2.setInt(1, a.get(gid));
        			stmt2.setString(2,message);
        			stmt2.setInt(3, curid);
        			ResultSet rs2=stmt2.executeQuery();
        		}
        		catch(Exception e)
        		{
        			System.out.println(e.getMessage());
        		}
            }
            else if(message.equals("subsidy request"))
            {
            	//??db last index=index+1,gid from random g_id from goverment table,message="d_id + document reqest"
            	try
        		{
        			Class.forName("org.postgresql.Driver");
        			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
        			PreparedStatement stmt= con.prepareStatement("select max(index) from farmersubsidy.alerts");
        			ResultSet rs=stmt.executeQuery();
        			while(rs.next())
        			{
        				curid=rs.getInt(1);
        			}
        			curid=curid+1;
        			PreparedStatement stmt1= con.prepareStatement("select g_id from farmersubsidy.government");
        			ResultSet rs1=stmt1.executeQuery();
        			ArrayList<Integer>a=new ArrayList<Integer>();
        			while(rs1.next())
        			{
        				a.add(rs.getInt(1));
        			}
        			Random r= new Random();
        			int gid=r.nextInt(a.size());
        			message="subsidy request added : "+id;
        			PreparedStatement stmt2= con.prepareStatement("insert into farmersubsidy.alerts(id,message,index)"+"values(?,?,?)" );
        			stmt2.setInt(1, a.get(gid));
        			stmt2.setString(2,message);
        			stmt2.setInt(3, curid);
        			ResultSet rs2=stmt2.executeQuery();
        		}
        		catch(Exception e)
        		{
        			System.out.println(e.getMessage());
        		}

            }
    }
    public void notifyUser(int id,String message,int fid,String type)	//farmer
    {
    		Farmer f=new Farmer();// how to select farmer of fid
    		if(message=="sNew")
    		{
    			//need to notify all
    			//db select all fids from farmer table and insert in alerts table with msg="New scheme added with id for type"
    			message="New scheme added with id "+id+" and type "+type;
    			int curid=0;
    			try
        		{
        			Class.forName("org.postgresql.Driver");
        			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
        			PreparedStatement stmt= con.prepareStatement("select f_id from farmersubsidy.farmer");
        			ResultSet rs=stmt.executeQuery();
        			while(rs.next())
        			{
        				int Id=rs.getInt(1);
        				//System.out.println(Id);
        				PreparedStatement stmt1= con.prepareStatement("select max(index) from farmersubsidy.alerts");
        				ResultSet rs1=stmt1.executeQuery();
        				while(rs1.next())
        				{
        					curid=rs1.getInt(1);
        				}
        				curid=curid+1;
        				//System.out.println("a"+Id);
        				PreparedStatement stmt2= con.prepareStatement("insert into farmersubsidy.alerts(id,message,index)"+"values(?,?,?)" );
            			stmt2.setInt(1, Id);
            			stmt2.setString(2,message);
            			stmt2.setInt(3, curid);
            			ResultSet rs2=stmt2.executeQuery();
        			}
        			
        		}
        		catch(Exception e)
        		{
        			System.out.println(e.getMessage());
        		}
    			
    		}
    		else	//notify regarding doc status update or subsidy request update
    		{
    			//db alerts insert fid,index+1
    			int curid=0;
    			if(message.equals("sUpdate"))
    			{
    				try
            		{
            			message="Your subsidy id:"+id+"has been updated";
    					Class.forName("org.postgresql.Driver");
            			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
            			PreparedStatement stmt= con.prepareStatement("select max(index) from farmersubsidy.alerts");
            			ResultSet rs=stmt.executeQuery();
            			while(rs.next())
            			{
            				curid=rs.getInt(1);
            			}
            			curid=curid+1;
            			PreparedStatement stmt1= con.prepareStatement("insert into farmersubsidy.alerts(id,message,index)"+"values(?,?,?)");
            			stmt1.setInt(1, fid);
            			stmt1.setString(2,message);
            			stmt1.setInt(3, curid);
            			ResultSet rs1=stmt1.executeQuery();
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            		}

    				//System.out.println("Alert!The status of your subsidy request "+ id+" has been updated");
    			}
    			else if(message.equals("dUpdate"))
    			{
    				try
            		{
            			message="Your document id:"+id+"has been updated";
    					Class.forName("org.postgresql.Driver");
            			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
            			PreparedStatement stmt= con.prepareStatement("select max(index) from farmersubsidy.alerts");
            			ResultSet rs=stmt.executeQuery();
            			while(rs.next())
            			{
            				curid=rs.getInt(1);
            			}
            			curid=curid+1;
            			PreparedStatement stmt1= con.prepareStatement("insert into farmersubsidy.alerts(id,message,index)"+"values(?,?,?)");
            			stmt1.setInt(1, fid);
            			stmt1.setString(2,message);
            			stmt1.setInt(3, curid);
            			ResultSet rs1=stmt1.executeQuery();
            		}
            		catch(Exception e)
            		{
            			System.out.println(e.getMessage());
            		}
    			}
    			
    		}
    }
}


