package crud1.controller;

import crud1.Logger.Logger;
import crud1.dto.PageParameters;
import crud1.dto.PageParametersDTO;
import crud1.model.RealModel;
import crud1.model.Model;
import crud1.view.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static crud1.Constants.*;

/**
 * Page MainController - calls show method on requeried page
 */

@javax.servlet.annotation.WebServlet(name = "MainController")
public class MainController extends javax.servlet.http.HttpServlet {
    private Model model;
    private PageParameters parameters;

    @Override
    public void init() throws ServletException {
        super.init();
        Logger.setServletContext(getServletContext());
        model = RealModel.getInstance();
        Logger.log("Application initiated...");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        parameters = new PageParametersDTO(request, response);
        Logger.log("HTTP\t"+request.getMethod()+"\t query string:\t"+request.getQueryString());
        Page resultPage = null;
        switch (parameters.getMode()) {
            case MODE_START:
                resultPage=new StartPageController(parameters).execute();
                break;
            case MODE_DATABASE_MANAGEMENT:
                resultPage=new DatabaseManagementController(parameters, model).execute();
                break;
            case MODE_NAVI:
                resultPage=new NavigationController(parameters, model).execute();
                break;
        }
        if (resultPage != null) {
            Logger.log("HTTP\t"+request.getMethod()+"\t query string:\t"+request.getQueryString()+"\tSHOWN:\t"+resultPage.getClass().getSimpleName());
            resultPage.show();

        } else {
            Logger.log("HTTP\t"+request.getMethod()+"\t query string:\t"+request.getQueryString()+"\tSHOWN:\t"+"PAGE NOT FOUND");
            parameters.getResponse().sendRedirect(PAGE_NOT_FOUND);
        }
   }
}
