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
            if (apiRequest != "" && apiRequest != null) {
                String xml = "";
                switch (apiRequest) {
                    case "user_list":
                        xml = getUserList();
                        break;

                    case "check_username":
                        String check_username = request.getParameter("username");
                        xml = checkUsername(check_username);
                        break;

                    case "new_user":
                        String new_username = request.getParameter("username");
                        xml = newUser(new_username);
                        break;

                    case "point_of_interest_list":
                        String route_id_parameter_1 = request.getParameter("route_id");
                        try {
                            int route_id = Integer.parseInt(route_id_parameter_1);
                            xml = getPointOfInterestList(route_id);
                        } catch (Exception e) {
                            out.println("<error>Wrong route_id</error>");
                        }
                        break;

                    case "route":
                        String code = request.getParameter("route_code");
                        xml = getRouteFromCode(code);
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
                        try {
                            int question_id = Integer.parseInt(question_id_parameter);
                            xml = getAnswerList(question_id);
                        } catch (Exception e) {
                            out.println("<error>Wrong question_id</error>");
                        }
                        break;

                    case "points_list":
                        String user_id_parameter = request.getParameter("user_id");
                        String route_id_parameter_2 = request.getParameter("route_id");
                        try {
                            int user_id = Integer.parseInt(user_id_parameter);
                            int route_id = Integer.parseInt(route_id_parameter_2);

                            xml = getPointsList(user_id, route_id);
                        } catch (Exception e) {
                            out.println("<error>Wrong parameters</error>");
                        }
                        break;

                    case "create_route":
                        String route_xml = request.getParameter("route");
                        xml = newRoute(route_xml);
                        break;

                    case "create_question":
                        String question_xml = request.getParameter("question");
                        xml = newQuestion(question_xml);
                        break;

                    case "create_answers":
                        String answers_xml = request.getParameter("answers");
                        xml = newAnswers(answers_xml);
                        break;

                    case "create_point_of_interest":
                        String point_of_interest_xml = request.getParameter("point_of_interest");
                        String route_id_parameter3 = request.getParameter("route_id");
                        try {
                            int route_id = Integer.parseInt(route_id_parameter3);

                            xml = newPointOfInterest(point_of_interest_xml, route_id);
                        } catch (Exception e) {
                            out.println("error");
                        }
                        break;

                    case "remove_point_of_interest":
                        String latitude_parameter = request.getParameter("latitude");
                        String longitude_parameter = request.getParameter("longitude");
                        String route_id_parameter4 = request.getParameter("route_id");
                        try {
                            double latitude = Double.parseDouble(latitude_parameter);
                            double longitude = Double.parseDouble(longitude_parameter);
                            int route_id = Integer.parseInt(route_id_parameter4);
                            
                            xml = removePointOfInterest(latitude, longitude, route_id);
                        } catch (Exception e) {
                            xml = "error";
                        }
                        break;

                    case "highscore_list":

                    case "empty_point_entry":
                        String user_id_parameter2 = request.getParameter("user_id");
                        String route_id_parameter = request.getParameter("route_id");
                        try {
                            int user_id = Integer.parseInt(user_id_parameter2);
                            int route_id = Integer.parseInt(route_id_parameter);

                            xml = createEmptyPointEntry(user_id, route_id);
                        } catch (Exception e) {
                            out.println("<error>Wrong parameters</error>");
                        }
                        break;

                    case "change_user_points":
                        String user_id_parameter3 = request.getParameter("user_id");
                        String route_id_parameter2 = request.getParameter("route_id");
                        String points_parameter = request.getParameter("points");

                        try {
                            int user_id = Integer.parseInt(user_id_parameter3);
                            int route_id = Integer.parseInt(route_id_parameter2);
                            int points = Integer.parseInt(points_parameter);

                            xml = changeUserPoints(user_id, route_id, points);
                        } catch (Exception e) {
                            out.println("<error>Wrong parameters</error>");
                        }
                        break;
                    case "set_point_triggered":
                        String point_triggered_xml = request.getParameter("point_triggered");

                        try {
                            xml = setPointTriggered(point_triggered_xml);
                        } catch (Exception e) {
                            out.println("<error>Wrong parameters</error>");
                        }
                        break;
                    case "get_point_triggered":

                        break;
                }

                if (!xml.equals("success") && !xml.equals("error")) {
                    response.setContentType("text/xml;charset=UTF-8");
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                }

                out.println(xml);
            } else {
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

    private String getUserList() {
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

    private String checkUsername(String username) {
        String xml = "";

        UserList userList = new UserList(databaseManager.checkUsername(username, true));

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

    private String newUser(String username) {
        String xml = "";

        UserList userList = new UserList(databaseManager.newUser(username));

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

    private String getPointOfInterestList(int route_id) {
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

    private String getRouteFromCode(String code) {
        String xml = "";

        RouteList route = new RouteList(databaseManager.getRouteFromCode(code));

        try {
            XStream xstream = new XStream();
            xstream.alias("route", Route.class);
            xstream.alias("routes", RouteList.class);
            xstream.addImplicitCollection(RouteList.class, "routes");

            xml = xstream.toXML(route);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }

    private String getQuestionList() {
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

    private String getCategoryList() {
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

    private String getToughnessList() {
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

    private String getAnswerList(int question_id) {
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

    private String getPointsList(int user_id, int route_id) {
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

    private String newRoute(String route_xml) {
        String xml = "";

        try {
            XStream xstream = new XStream();
            xstream.alias("route", Route.class);
            xstream.alias("routes", RouteList.class);
            xstream.addImplicitCollection(RouteList.class, "routes");

            RouteList route_list = (RouteList) xstream.fromXML(route_xml);
            Route route = route_list.getRoutes().get(0);

            RouteList new_route = new RouteList(databaseManager.newRoute(route));
            xml = xstream.toXML(new_route);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }

    private String newQuestion(String question_xml) {
        String xml = "";

        try {
            XStream xstream = new XStream();
            xstream.alias("question", Question.class);

            Question question = (Question) xstream.fromXML(question_xml);

            Question new_question = databaseManager.newQuestion(question);
            xml = xstream.toXML(new_question);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }

    private String newAnswers(String answers_xml) {
        String xml = "";

        try {
            XStream xstream = new XStream();
            xstream.alias("answer", Answer.class);
            xstream.alias("answers", AnswerList.class);
            xstream.addImplicitCollection(AnswerList.class, "answers");

            AnswerList answer_list = (AnswerList) xstream.fromXML(answers_xml);

            String status = databaseManager.newAnswers(answer_list.getAnswers());
            xml = status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }

    private String newPointOfInterest(String point_of_interest_xml, int route_id) {
        String xml = "";

        try {
            XStream xstream = new XStream();
            xstream.alias("point_of_interest", PointOfInterest.class);

            PointOfInterest point_of_interest = (PointOfInterest) xstream.fromXML(point_of_interest_xml);

            String status = databaseManager.newPointOfInterest(point_of_interest, route_id);
            xml = status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }

    private String removePointOfInterest(double latitude, double longitude, int route_id) {
        String xml = "";

        try {
            String status = databaseManager.removePointOfInterest(latitude, longitude, route_id);
            xml = status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }

    private String createEmptyPointEntry(int user_id, int route_id) {
        String xml = "";

        try {
            String status = databaseManager.emptyPointsEntry(user_id, route_id);
            xml = status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }

    private String changeUserPoints(int user_id, int route_id, int points) {
        String xml = "";

        try {
            String status = databaseManager.changeUserPoints(user_id, route_id, points);
            xml = status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;

    }

    private String setPointTriggered(String point_triggered_xml) {
        String xml = "";

        try {
            
            XStream xstream = new XStream();
            xstream.alias("point_triggered", PointTriggered.class);

            PointTriggered point_triggered = (PointTriggered) xstream.fromXML(point_triggered_xml);
            
            String status = databaseManager.setPointTriggered(point_triggered);
            xml = status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return xml;
    }
}
