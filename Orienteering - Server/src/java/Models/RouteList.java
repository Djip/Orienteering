import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class RouteList {
    private List<Route> routes;
    
    public RouteList()
    {
        routes = new ArrayList<Route>();
    }
    
    public RouteList(ArrayList<Route> routes)
    {
        setRoutes(routes);
    }
    
    public void add(Route route)
    {
        routes.add(route);
    }
    
    public List<Route> getRoutes()
    {
        return this.routes;
    }
    
    public void setRoutes(ArrayList<Route> routes)
    {
        this.routes = routes;
    }
}