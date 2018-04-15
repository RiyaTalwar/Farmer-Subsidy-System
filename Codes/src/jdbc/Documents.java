package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Documents {
        private String type;
        private String status;
        private int d_id;
        Scanner sc=new Scanner(System.in);
        Subsidy sub=new Subsidy();
  
		public void setStatus(String stat,int did) 
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
				PreparedStatement stmt= con.prepareStatement("update farmersubsidy.documents set status=?"+"where d_id=?");
				stmt.setString(1, stat);
				stmt.setInt(2, did);
				ResultSet rs=stmt.executeQuery();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			this.status = status;	//DB: to db
		}
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public long getD_id() {
            return d_id;
        }
        public void setD_id(int d_id) {
            this.d_id = d_id;
        }
        public int doc_upload(int fid)
        {
            System.out.println("Enter the type of document being uploaded");
            type=sc.next();
            System.out.println("Document Uploaded!!");
            int curid=0,d_id=0;
            //DB: d_id + 1 =return a unique id after linking with database
            try
    		{
    			Class.forName("org.postgresql.Driver");
    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
    			PreparedStatement stmt= con.prepareStatement("select max(d_id) from farmersubsidy.documents");
    			ResultSet rs=stmt.executeQuery();
    			while(rs.next())
    			{
    				curid=rs.getInt(1);
    			}
    			d_id=curid+1;
    			PreparedStatement stmt1= con.prepareStatement("insert into farmersubsidy.documents (d_id,status,type,fid)"+" values(?,'pending',?,?)");
    			stmt1.setInt(1, d_id);
    			stmt1.setString(2,type);
    			stmt1.setInt(3, fid);
    			ResultSet rs1=stmt1.executeQuery();
    		}
    		catch(Exception e)
    		{
    			System.out.println(e.getMessage());
    		}
            //DB: then add this did and type of document with fid to database
            Notifications n=new Notifications();
            n.notifyUser(d_id, "document request");
             return d_id;
        }
        public void update_status(int d_id)
        {
            //DB: get fid corresponding to this did from db
        	int fid=0;
        	try
    		{
    			Class.forName("org.postgresql.Driver");
    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
    			PreparedStatement stmt= con.prepareStatement("select fid from farmersubsidy.documents where d_id=?");
    			stmt.setInt(1, d_id);
    			ResultSet rs=stmt.executeQuery();
    			while(rs.next())
    			{
    				fid=rs.getInt(1);
    			}
    		}
    		catch(Exception e)
    		{
    			System.out.println(e.getMessage());
    		}
            send_docStatus(d_id);		
            System.out.println("Enter status to be updated");
            String stat=sc.next();
            setStatus(stat,d_id);
            System.out.println("Status updated!!");
            Notifications n=new Notifications();
            n.notifyUser(d_id, "dUpdate", fid, "document");
            
        }
        public String send_docStatus(int d_id) 
        {
        	String stat=null;
        	try
    		{
    			Class.forName("org.postgresql.Driver");
    			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
    			PreparedStatement stmt= con.prepareStatement("select status from farmersubsidy.documents where d_id=?");
    			stmt.setInt(1, d_id);
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
        	return stat;		//DB: from db
		}
        public void generate_receipt(int id)
        {
        	String stat=sub.send_subsidyStatus(id);
            if(stat=="sanctioned")
            {
                System.out.println("Receipt generated");
                //DB: add receipt to database with type, id(subsidy applicaation) , content, fid, did
                int curid=0;
                int fid=0;
                String s=null;
                try
        		{
        			Class.forName("org.postgresql.Driver");
        			Connection con = DriverManager.getConnection("jdbc:postgresql://10.100.71.21:5432/201501140","201501140","201501140");
        			PreparedStatement stmt1= con.prepareStatement("select max(d_id) from farmersubsidy.documents");
        			ResultSet rs=stmt1.executeQuery();
        			while(rs.next())
        			{
        				curid=rs.getInt(1);
        			}
        			d_id=curid+1;
        			PreparedStatement stmt3= con.prepareStatement("select stat,fid from farmersubsidy.subsidyrequest where sid=?");
        			ResultSet rs3=stmt1.executeQuery();
        			stmt3.setInt(1,id);
        			while(rs3.next())
        			{
        				s=rs3.getString(1);
        				fid=rs3.getInt(2);
        			}
        			PreparedStatement stmt2= con.prepareStatement("insert into farmersubsidy.documents (d_id,type,status,fid)"+"values(?,'receipt',?,?");
        			stmt2.setInt(1, d_id);
        			stmt2.setString(2,stat );
        			stmt2.setInt(1, fid);
        		}
        		catch(Exception e)
        		{
        			System.out.println(e.getMessage());
        		}
            }
        }
        public void display_doc(long did)
        {
          System.out.println("Displaying "+did);
          //content 
        }
}

