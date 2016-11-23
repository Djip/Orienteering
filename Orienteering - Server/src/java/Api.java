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
                    case "userList":
                        xml = getUserList();
                        break;
                        
                    case "pointOfInterestList":
                        xml = getPointOfInterestList();
                        break;
                        
                    case "routeList":
                        xml = getRouteList();
                        break;
                        
                    case "questionList":
                        xml = getQuestionList();
                        break;
                        
                    case "categoryList":
                        xml = getCategoryList();
                        break;
                        
                    case "toughnessList":
                        xml = getToughnessList();
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
    
    private String getPointOfInterestList()
    {
        String xml = "";
        
        PointOfInterestList pointOfInterestList = new PointOfInterestList(databaseManager.getPointOfInterests());

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
            xstream.alias("categorys", CategoryList.class);
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
        
        ToughnessList toughnessList = new ToughnessList(databaseManager.getToughness());

        try {
            XStream xstream = new XStream();
            xstream.alias("toughness", Toughness.class);
            xstream.alias("toughnessList", ToughnessList.class);
            xstream.addImplicitCollection(ToughnessList.class, "toughnessList");

            xml = xstream.toXML(toughnessList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
}
