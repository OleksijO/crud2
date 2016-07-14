package crud10.controller;

import crud10.Logger.Logger;
import crud10.dto.PageParameters;
import crud10.dto.PageParametersDTO;
import crud12.model.RealModel;
import crud12.model.Model;
import crud10.view.*;
import crud12.spring.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static crud10.Constants.*;

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
        Context.setServletContext(getServletContext());
        Logger.log("Application initiated...");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger.log("HTTP\t" + request.getMethod() + "\t query string:\t" + request.getQueryString());
        model = (Model) Context.getBean("model");
        parameters = Context.getPageParameters(request,response);
        Page resultPage = null;
        switch (parameters.getMode()) {
            case MODE_START:
                resultPage = new StartPageController(parameters).execute();
                break;
            case MODE_DATABASE_MANAGEMENT:
                resultPage = new DatabaseManagementController(parameters, model).execute();
                break;
            case MODE_NAVI:
                resultPage = new NavigationController(parameters, model).execute();
                break;
        }
        if (resultPage != null) {
            Logger.log("HTTP\t" + request.getMethod() + "\t query string:\t" + request.getQueryString() + "\tSHOWN:\t" + resultPage.getClass().getSimpleName());
            resultPage.show();

        } else {
            Logger.log("HTTP\t" + request.getMethod() + "\t query string:\t" + request.getQueryString() + "\tSHOWN:\t" + "PAGE NOT FOUND");
            parameters.getResponse().sendRedirect(PAGE_NOT_FOUND);
        }
    }
}
