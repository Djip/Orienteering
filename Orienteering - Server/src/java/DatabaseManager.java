// Loading required libraries
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DatabaseManager extends HttpServlet{
    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://85.233.225.116:3306/orienteering";

    //  Database credentials
    final String USER = "root";
    final String PASS = "demo123";
        
    private Connection conn = null;
    private Statement stmt = null;
    
    public DatabaseManager()
    {
        startDatabaseConnection();
    }
    
    private void startDatabaseConnection()
    {
        try
        {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            stmt = conn.createStatement();
        }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    private void closeDatabaseConnection()
    {
        try
        {
            if(stmt != null)
            {
               stmt.close();
            }
        }
        catch(SQLException se2){ }// nothing we can do
        
        try
        {
            if(conn != null)
            {
                conn.close();
            }
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }
    
    public ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList<User>();;

        try
        {
            String sql = "SELECT id, username FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
               int id  = rs.getInt("id");
               String username = rs.getString("username");

               users.add(new User(id, username));
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally
        {
            closeDatabaseConnection();
        }
      
        return users;
    }
    
    public ArrayList<PointOfInterest> getPointOfInterests()
    {
        ArrayList<PointOfInterest> point_of_interests = new ArrayList<PointOfInterest>();;

        try
        {
            String sql = "SELECT id, username FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
               int id  = rs.getInt("id");
               double latitude = rs.getDouble("latitude");
               double longitude = rs.getDouble("longitude");
               String title = rs.getString("title");
               int route_id = rs.getInt("route_id");

               point_of_interests.add(new PointOfInterest(id, latitude, longitude, title, route_id));
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally
        {
            closeDatabaseConnection();
        }
      
        return point_of_interests;
    }
}