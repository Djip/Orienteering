/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 *
 * @author jespe
 */
public class Api extends HttpServlet {

    private DatabaseManager databaseManager;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            databaseManager = new DatabaseManager();
            
            String apiRequest = request.getParameter("get");
            
            if (apiRequest != "" && apiRequest != null)
            {
                response.setContentType("text/xml;charset=UTF-8");
                
                String xml = "";
                switch(apiRequest)
                {
                    case "check_username":
                        String check_username = request.getParameter("username");
                        xml = checkUsername(check_username);
                        break;
                        
                    case "new_user":
                        String new_user = request.getParameter("username");
                        xml = newUser(new_user);
                        break;
                        
                    case "point_of_interest_list":
                        String route_id_parameter_1 = request.getParameter("route_id");
                        try
                        {
                            int route_id = Integer.parseInt(route_id_parameter_1);
                            xml = getPointOfInterestList(route_id);
                        }
                        catch (Exception e)
                        {
                            out.println("<error>Wrong question_id</error>");
                        }
                        break;
                        
                    case "route_list":
                        xml = getRouteList();
                        break;
                        
                    case "question_list":
                        xml = getQuestionList();
                        break;
                        
                    case "category_list":
                        xml = getCategoryList();
                        break;
                        
                    case "toughness_list":
                        xml = getToughnessList();
                        break;
                        
                    case "answer_list":
                        String question_id_parameter = request.getParameter("question_id");
                        try
                        {
                            int question_id = Integer.parseInt(question_id_parameter);
                            xml = getAnswerList(question_id);
                        }
                        catch (Exception e)
                        {
                            out.println("<error>Wrong question_id</error>");
                        }
                        break;
                        
                    case "points_list":
                        String user_id_parameter = request.getParameter("user_id");
                        String route_id_parameter_2 = request.getParameter("route_id");
                        try
                        {
                            int user_id = Integer.parseInt(user_id_parameter);
                            int route_id = Integer.parseInt(route_id_parameter_2);
                            
                            xml = getPointsList(user_id, route_id);
                        }
                        catch (Exception e)
                        {
                            out.println("<error>Wrong parameters</error>");
                        }
                        break;
                        
                }
                
                out.println(xml);
            }
            else
            {
                out.println("Invalid request");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private String getUserList()
    {
        String xml = "";
        
        UserList userList = new UserList(databaseManager.getUsers());

        try {
            XStream xstream = new XStream();
            xstream.alias("user", User.class);
            xstream.alias("users", UserList.class);
            xstream.addImplicitCollection(UserList.class, "users");

            xml = xstream.toXML(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String checkUsername(String username)
    {
        String xml = "";
        
        UserList userExists = new UserList(databaseManager.checkUsername(username, true));

        try {
            XStream xstream = new XStream();
            xstream.alias("user", User.class);
            xstream.alias("users", UserList.class);
            xstream.addImplicitCollection(UserList.class, "users");

            xml = xstream.toXML(userExists);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String newUser(String username)
    {
        String xml = "";
        
        UserList userExists = new UserList(databaseManager.newUser(username));

        try {
            XStream xstream = new XStream();
            xstream.alias("user", User.class);
            xstream.alias("users", UserList.class);
            xstream.addImplicitCollection(UserList.class, "users");

            xml = xstream.toXML(userExists);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String getPointOfInterestList(int route_id)
    {
        String xml = "";
        
        PointOfInterestList pointOfInterestList = new PointOfInterestList(databaseManager.getPointOfInterests(route_id));

        try {
            XStream xstream = new XStream();
            xstream.alias("point_of_interest", PointOfInterest.class);
            xstream.alias("point_of_interests", PointOfInterestList.class);
            xstream.addImplicitCollection(PointOfInterestList.class, "point_of_interests");

            xml = xstream.toXML(pointOfInterestList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String getRouteList()
    {
        String xml = "";
        
        RouteList routeList = new RouteList(databaseManager.getRoutes());

        try {
            XStream xstream = new XStream();
            xstream.alias("route", Route.class);
            xstream.alias("routes", RouteList.class);
            xstream.addImplicitCollection(RouteList.class, "routes");

            xml = xstream.toXML(routeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String getQuestionList()
    {
        String xml = "";
        
        QuestionList questionList = new QuestionList(databaseManager.getQuestions());

        try {
            XStream xstream = new XStream();
            xstream.alias("question", Question.class);
            xstream.alias("questions", QuestionList.class);
            xstream.addImplicitCollection(QuestionList.class, "questions");

            xml = xstream.toXML(questionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String getCategoryList()
    {
        String xml = "";
        
        CategoryList categoryList = new CategoryList(databaseManager.getCategories());

        try {
            XStream xstream = new XStream();
            xstream.alias("category", Category.class);
            xstream.alias("categories", CategoryList.class);
            xstream.addImplicitCollection(CategoryList.class, "categories");

            xml = xstream.toXML(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String getToughnessList()
    {
        String xml = "";
        
        ToughnessList toughnessList = new ToughnessList(databaseManager.getToughnessList());

        try {
            XStream xstream = new XStream();
            xstream.alias("toughness", Toughness.class);
            xstream.alias("toughness_list", ToughnessList.class);
            xstream.addImplicitCollection(ToughnessList.class, "toughness_list");

            xml = xstream.toXML(toughnessList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String getAnswerList(int question_id)
    {
        String xml = "";
        
        AnswerList answerList = new AnswerList(databaseManager.getAnswers(question_id));

        try {
            XStream xstream = new XStream();
            xstream.alias("answer", Answer.class);
            xstream.alias("answers", AnswerList.class);
            xstream.addImplicitCollection(AnswerList.class, "answers");

            xml = xstream.toXML(answerList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
    
    private String getPointsList(int user_id, int route_id)
    {
        String xml = "";
        
        PointsList answerList = new PointsList(databaseManager.getPoints(user_id, route_id));

        try {
            XStream xstream = new XStream();
            xstream.alias("points", Points.class);
            xstream.alias("points_list", PointsList.class);
            xstream.addImplicitCollection(PointsList.class, "points_list");

            xml = xstream.toXML(answerList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
}
