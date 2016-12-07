// Loading required libraries

import java.util.*;
import java.sql.*;

public class DatabaseManager {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://85.233.225.116:3306/orienteering";

    //  Database credentials
    final String USER = "root";
    final String PASS = "demo123";

    private Connection conn = null;
    private Statement stmt = null;

    public DatabaseManager() {
        startDatabaseConnection();
    }

    private void startDatabaseConnection() {
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    private void closeDatabaseConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se2) {
        }// nothing we can do

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            String sql = "SELECT * FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");

                users.add(new User(id, username));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return users;
    }

    public ArrayList<User> checkUsername(String username, boolean close_connection) {
        ArrayList<User> users = new ArrayList<User>();

        try {
            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String pulled_username = rs.getString("username");

                users.add(new User(id, pulled_username));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            if (close_connection) {
                closeDatabaseConnection();
            }
        }

        return users;
    }

    public ArrayList<User> newUser(String username) {
        ArrayList<User> users = new ArrayList<User>();

        ArrayList<User> check_users = checkUsername(username, false);

        if (check_users != null && check_users.size() != 1 && !username.matches("")) {
            try {
                String sql = "INSERT INTO user(username, online) VALUES(?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setInt(2, User.ONLINE);
                int rows = pstmt.executeUpdate();

                if (rows == 1) {
                    users = checkUsername(username, true);
                }
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                closeDatabaseConnection();
            }
        }

        return users;
    }

    public ArrayList<PointOfInterest> getPointOfInterests(int route_id) {
        ArrayList<PointOfInterest> point_of_interests = new ArrayList<PointOfInterest>();

        try {
            String sql = "SELECT * FROM point_of_interest AS poi JOIN route_point_of_interest_rel AS rpoi ON poi.id = rpoi.point_of_interest_id AND rpoi.route_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, route_id);
            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while (rs.next()) {
                int id = rs.getInt("id");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                point_of_interests.add(new PointOfInterest(id, latitude, longitude));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return point_of_interests;
    }

    public ArrayList<Route> getRouteFromCode(String code) {
        ArrayList<Route> route_list = new ArrayList<Route>();

        try {
            String sql = "SELECT * FROM route WHERE code = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int category_id = rs.getInt("category_id");
                int toughness_id = rs.getInt("toughness_id");
                int gametime = rs.getInt("gametime");
                boolean show_default_point_of_interest = rs.getBoolean("show_default_point_of_interest");
                boolean show_defined_questions = rs.getBoolean("show_defined_questions");

                route_list.add(new Route(id, code, user_id, category_id, toughness_id, gametime, show_default_point_of_interest, show_defined_questions));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return route_list;
    }

    public ArrayList<Question> getQuestions() {
        // FROM ROUTE_ID!!!!
        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            String sql = "SELECT * FROM question";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
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
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return questions;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();

        try {
            String sql = "SELECT * FROM category";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");

                categories.add(new Category(id, category));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return categories;
    }

    public ArrayList<Toughness> getToughnessList() {
        ArrayList<Toughness> toughnessList = new ArrayList<Toughness>();

        try {
            String sql = "SELECT * FROM toughness";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String toughness = rs.getString("toughness");

                toughnessList.add(new Toughness(id, toughness));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return toughnessList;
    }

    public ArrayList<Answer> getAnswers(int question_id) {
        ArrayList<Answer> answers = new ArrayList<Answer>();

        try {
            String sql = "SELECT * FROM answer WHERE question_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, question_id);
            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while (rs.next()) {

                int id = rs.getInt("id");
                String answer = rs.getString("answer");
                boolean correct = rs.getBoolean("correct");

                answers.add(new Answer(id, question_id, answer, correct));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return answers;
    }

    public ArrayList<Points> getPoints(int user_id, int route_id) {
        ArrayList<Points> points = new ArrayList<Points>();

        try {
            String sql = "SELECT * FROM points WHERE user_id = ? AND route_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (user_id == 0) {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(1, user_id);
            }

            if (route_id == 0) {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(1, route_id);
            }

            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while (rs.next()) {
                int user_id_data = rs.getInt("user_id");
                int route_id_data = rs.getInt("route_id");
                int point = rs.getInt("points");

                points.add(new Points(user_id_data, route_id_data, point));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return points;
    }

    public ArrayList<Route> newRoute(Route route) {
        ArrayList<Route> routes = new ArrayList<Route>();

        try {
            String sql = "INSERT INTO route(code, user_id, category_id, toughness_id, gametime, show_default_point_of_interest, show_defined_questions) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, route.getCode());
            pstmt.setInt(2, route.getUserId());
            pstmt.setInt(3, route.getCategoryId());
            pstmt.setInt(4, route.getToughnessId());
            pstmt.setInt(5, route.getGametime());
            pstmt.setBoolean(6, route.getShowDefaultPointOfInterest());
            pstmt.setBoolean(7, route.getShowDefinedQuestions());

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                routes.add(getLastRoute(route.getUserId()));
            }
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return routes;
    }

    public Route getLastRoute(int user_id) {
        Route route = new Route();

        try {
            String sql = "SELECT * FROM route WHERE user_id = ? ORDER BY id DESC LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while (rs.next()) {
                route.setId(rs.getInt("id"));
                route.setCode(rs.getString("code"));
                route.setCategoryId(rs.getInt("category_id"));
                route.setToughnessId(rs.getInt("toughness_id"));
                route.setGametime(rs.getInt("gametime"));
                route.setShowDefaultPointOfInterest(rs.getBoolean("show_default_point_of_interest"));
                route.setShowDefinedQuestions(rs.getBoolean("show_defined_questions"));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return route;
    }

    public Question newQuestion(Question question) {
        Question new_question = null;

        try {
            String sql = "INSERT INTO question(category_id, toughness_id, question, plus_point, minus_point, route_id) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, question.getCategoryId());
            pstmt.setInt(2, question.getToughnessId());
            pstmt.setString(3, question.getQuestion());
            pstmt.setInt(4, question.getPlusPoint());
            pstmt.setInt(5, question.getMinusPoint());
            pstmt.setInt(6, question.getRouteId());

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                new_question = getLatestQuestion(question.getRouteId());
            }
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return new_question;
    }

    public Question getLatestQuestion(int route_id) {
        Question question = new Question();

        try {
            String sql = "SELECT * FROM question WHERE route_id = ? ORDER BY id DESC LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, route_id);
            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while (rs.next()) {
                question.setId(rs.getInt("id"));
                question.setCategoryId(rs.getInt("category_id"));
                question.setToughnessId(rs.getInt("toughness_id"));
                question.setQuestion(rs.getString("question"));
                question.setPlusPoint(rs.getInt("plus_point"));
                question.setMinusPoint(rs.getInt("minus_point"));
                question.setRouteId(rs.getInt("route_id"));
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return question;
    }

    public String newAnswers(List<Answer> answers) {
        String status = "error";

        try {
            for (Answer answer : answers) {
                String sql = "INSERT INTO answer(question_id, answer, correct) VALUES(?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, answer.getQuestionId());
                pstmt.setString(2, answer.getAnswer());
                pstmt.setBoolean(3, answer.getCorrect());

                int rows = pstmt.executeUpdate();

                if (rows == 1) {
                    status = "success";
                } else {
                    status = "error";
                    break;
                }
            }
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return "error";
        } finally {
            closeDatabaseConnection();
        }

        return status;
    }

    public String newPointOfInterest(PointOfInterest point_of_interest, int route_id) {
        String status = "error";

        try {
            point_of_interest.setId(getPointOfInterest(false, point_of_interest.getLatitude(), point_of_interest.getLongitude()));
            
            if (point_of_interest.getId() == 0) {
                String sql = "INSERT INTO point_of_interest(latitude, longitude) VALUES(?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setDouble(1, point_of_interest.getLatitude());
                pstmt.setDouble(2, point_of_interest.getLongitude());

                int rows = pstmt.executeUpdate();
                
                if (rows == 1) {
                    point_of_interest.setId(getPointOfInterest(false, point_of_interest.getLatitude(), point_of_interest.getLongitude()));

                    if (point_of_interest.getId() != 0)
                    {
                        status = insertRoutePointOfInterestRel(route_id, point_of_interest.getId());
                    }
                }
            }
            else
            {
                status = insertRoutePointOfInterestRel(route_id, point_of_interest.getId());
            }
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            status = "error";
        } finally {
            closeDatabaseConnection();
        }

        return status;
    }

    private int getPointOfInterest(boolean close_connection, double latitude, double longitude) {
        int point_of_interest_id = 0;

        try {
            String sql = "SELECT id FROM point_of_interest WHERE latitude = ? AND longitude = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, latitude);
            pstmt.setDouble(2, longitude);

            ResultSet rs = pstmt.executeQuery();

            // Extract data from result set
            while (rs.next()) {
                point_of_interest_id = rs.getInt("id");
            }

            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            if (close_connection) {
                closeDatabaseConnection();
            }
        }

        return point_of_interest_id;
    }
    
    private String insertRoutePointOfInterestRel(int route_id, int point_of_interest_id)
    {
        String status = "error";
        
        try
        {
            String sql = "INSERT INTO route_point_of_interest_rel(route_id, point_of_interest_id) VALUES(?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, route_id);
            pstmt.setInt(2, point_of_interest_id);

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                status = "success";
            }
        }
        catch(Exception e)
        {
            status = "error";
        }
        
        return status;
    }

    public String removePointOfInterest(double latitude, double longitude, int route_id) {
        String status = "error";

        try {
            String sql = "DELETE FROM route_point_of_interest_rel WHERE point_of_interest_id = (SELECT id FROM point_of_interest WHERE latitude = ? AND longitude = ?) AND route_id = 5";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, latitude);
            pstmt.setDouble(2, longitude);
            pstmt.setInt(4, route_id);

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                status = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return status;
    }

    public String emptyPointsEntry(int user_id, int route_id) {
        boolean is_created = false;
        String status = "error";
        String sql_get = "";

        try {
            if (route_id == 0) {
                sql_get = "SELECT * FROM points where user_id = ? AND route_id IS ?";
            } else {
                sql_get = "SELECT * FROM points where user_id = ? AND route_id = ?";
            }
            PreparedStatement pstmt_get = conn.prepareStatement(sql_get);
            pstmt_get.setInt(1, user_id);
            if (route_id == 0) {
                pstmt_get.setNull(2, java.sql.Types.INTEGER);
            } else {
                pstmt_get.setInt(2, route_id);
            }

            ResultSet rs = pstmt_get.executeQuery();

            while (rs != null && rs.next()) {
                is_created = true;
                status = "success";
            }
            rs.close();

            if (is_created == false) {
                String sql = "INSERT INTO points(user_id, route_id, points) VALUES(?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, user_id);
                if (route_id == 0) {
                    pstmt.setNull(2, java.sql.Types.INTEGER);
                } else {
                    pstmt.setInt(2, route_id);
                }

                pstmt.setInt(3, 0);

                int rows = pstmt.executeUpdate();

                if (rows == 1) {
                    status = "success";
                } else {
                    status = "error";
                }
            }
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return "error";
        } finally {
            closeDatabaseConnection();
        }

        return status;
    }

    public String changeUserPoints(int user_id, int route_id, int points) {
        String status = "error";
        String is_or_equals = "=";
        try {
            String sql = "";

            if (route_id == 0) {
                is_or_equals = "IS";
            }

            if (points < 0) {
                sql = "UPDATE points SET points = points ? WHERE user_id = ? AND route_id " + is_or_equals + " ?";
            } else {
                sql = "UPDATE points SET points = points + ? WHERE user_id = ? AND route_id " + is_or_equals + " ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, points);
            pstmt.setInt(2, user_id);
            if (route_id == 0) {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(3, route_id);
            }

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                status = "success";
            } else {
                status = "error";
            }
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return "error";
        } finally {
            closeDatabaseConnection();
        }

        return status;
    }

    public String setPointTriggered(PointTriggered point_triggered) {
        String status = "error";

        try {
            String sql = "INSERT INTO point_triggered(route_id, user_id, latitude, longitude, timestamp) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (point_triggered.getRouteId() == 0) {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(1, point_triggered.getRouteId());
            }
            pstmt.setInt(2, point_triggered.getUserId());
            pstmt.setDouble(3, point_triggered.getLatitude());
            pstmt.setDouble(4, point_triggered.getLongitude());
            pstmt.setTimestamp(5, point_triggered.getTimestamp());

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                status = "success";
            } else {
                status = "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
