// Loading required libraries
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DatabaseManager extends HttpServlet{

  public ArrayList<User> getUsers()
  {
      // JDBC driver name and database URL
      final String JDBC_DRIVER="com.mysql.jdbc.Driver";
      final String DB_URL="jdbc:mysql://85.233.225.116:3306/orienteering";

      //  Database credentials
      final String USER = "root";
      final String PASS = "demo123";

      Connection conn = null;
      Statement stmt = null;
      
      ArrayList<User> users = new ArrayList<User>();;

      try{
         // Register JDBC driver
         Class.forName("com.mysql.jdbc.Driver");

         // Open a connection
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         
         // Execute SQL query
         stmt = conn.createStatement();

         String sql;
         sql = "SELECT id, username FROM user";
         ResultSet rs = stmt.executeQuery(sql);
         
         // Extract data from result set
         while(rs.next()){
            //Retrieve by column name
            int id  = rs.getInt("id");
            String username = rs.getString("username");

            //Display values
            users.add(new User(id, username));
            //out.println("ID: " + id + "<br>");
            //out.println(", Username: " + username + "<br>");
         }

         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         se.printStackTrace();
      }catch(Exception e){
         //Handle errors for Class.forName
         e.printStackTrace();
      }finally{
         //finally block used to close resources
         try{
            if(stmt!=null)
               stmt.close();
         }catch(SQLException se2){
         }// nothing we can do
         try{
            if(conn!=null)
            conn.close();
         }catch(SQLException se){
            se.printStackTrace();
         }//end finally try   
      } //end try
      
      return users;
   }
}