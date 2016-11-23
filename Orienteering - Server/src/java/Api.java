/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
