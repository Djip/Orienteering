// Loading required libraries
import java.util.*;
import java.sql.*;

public class DatabaseManager{
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
        ArrayList<User> users = new ArrayList<User>();

        try
        {
            String sql = "SELECT * FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                int id  = rs.getInt("id");
                String username = rs.getString("username");

                users.add(new User(id, username));
            }
            
            rs.close();
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
        ArrayList<PointOfInterest> point_of_interests = new ArrayList<PointOfInterest>();

        try
        {
            String sql = "SELECT * FROM point_of_interest";
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
    
    public ArrayList<Route> getRoutes()
    {
        ArrayList<Route> routes = new ArrayList<Route>();

        try
        {
            String sql = "SELECT * FROM route";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                int id  = rs.getInt("id");
                String code = rs.getString("code");
                int user_id = rs.getInt("user_id");
                int gametime = rs.getInt("gametime");
                boolean show_default_point_of_interest = rs.getBoolean("show_deafult_point_of_interest");

                routes.add(new Route(id, code, user_id, gametime, show_default_point_of_interest));
            }
            
            rs.close();
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
      
        return routes;
    }
    
    public ArrayList<Question> getQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();

        try
        {
            String sql = "SELECT * FROM question";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                int id = rs.getInt("id");
                int category_id = rs.getInt("category_id");
                int toughness_id = rs.getInt("toughness_id");
                String question = rs.getString("question");
                int plus_point = rs.getInt("plus_point");
                int minus_point = rs.getInt("minus_point");
                int route_id = rs.getInt("route_id");

                questions.add(new Question(id, category_id, toughness_id, question, plus_point, minus_point, route_id));
            }
            
            rs.close();
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
      
        return questions;
    }
    
    public ArrayList<Category> getCategories()
    {
        ArrayList<Category> categories = new ArrayList<Category>();

        try
        {
            String sql = "SELECT * FROM category";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                int id = rs.getInt("id");
                String category = rs.getString("category");

                categories.add(new Category(id, category));
            }
            
            rs.close();
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
      
        return categories;
    }
    
    public ArrayList<Toughness> getToughnessList()
    {
        ArrayList<Toughness> toughnessList = new ArrayList<Toughness>();

        try
        {
            String sql = "SELECT * FROM toughness";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                int id = rs.getInt("id");
                String toughness = rs.getString("toughness");

                toughnessList.add(new Toughness(id, toughness));
            }
            
            rs.close();
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
      
        return toughnessList;
    }
    
    public ArrayList<Answer> getAnswers(int question_id)
    {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        
        try
        {
            String sql = "SELECT * FROM answer WHERE question_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, question_id);
            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while(rs.next()){
                
                int id = rs.getInt("id");
                String answer = rs.getString("answer");
                boolean correct = rs.getBoolean("correct");

                answers.add(new Answer(id, question_id, answer, correct));
            }
            
            rs.close();
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
      
        return answers;
    }
    
    public ArrayList<Points> getPoints(int user_id, int route_id)
    {
        ArrayList<Points> points = new ArrayList<Points>();
        
        try
        {
            String sql = "SELECT * FROM points WHERE user_id = ? AND route_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (user_id == 0)
            {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            else
            {
                pstmt.setInt(1, user_id);
            }
            
            if (route_id == 0)
            {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            else
            {
                pstmt.setInt(1, route_id);
            }
            
            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while(rs.next()){
                int user_id_data = rs.getInt("user_id");
                int route_id_data = rs.getInt("route_id");
                int point = rs.getInt("points");

                points.add(new Points(user_id_data, route_id_data, point));
            }
            
            rs.close();
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
      
        return points;
    }
}