package crud10.view;

import crud10.dto.PageParameters;


import static crud14.Constants.*;
import static crud10.utils.Helper.*;

/**
 * Site's (projects) start  page.
 */
public class Start extends AbstractPageTemplate {


    public Start(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public void showBody() {
        print("SessionFactoryImpl implemented features in this project  are:");
        print("<br>");
        print(href("?" + QUERY_PARAMETER_MODE + "=" + MODE_NAVI, "- NAVIGATION through database with CRUD-functions"));
        print("<br>");
        print(href("?" + QUERY_PARAMETER_MODE + "=" + MODE_DATABASE_MANAGEMENT, "- DATABASE MANAGEMENT - refill database to initial values"));
        print("<br>");
        print("Database (PostgreSQL 9.4) represented hierarchical tree structure, like:");
        print("&nbsp;&nbsp; &rarr; Root category");
        print("&nbsp;&nbsp; &rarr; &rarr; Product");
        print("&nbsp;&nbsp; &rarr; &rarr; Category");
        print("&nbsp;&nbsp; &rarr; ... &rarr; Product");
        print("&nbsp;&nbsp; &rarr; ... &rarr; Category,");
        print("- stored in two tables: 'product' and 'category', because poduct items has additional fields (price & quantity).");
        print("<br>");
        print("Every category could include subcategories or products or <u>both</u> of them.");
        print("You can navigate through this tree structure, add, delete or edit elements.");
        print("<br>");
        print("Application based on MVC model. ");
        print("Used design patterns:");
        print(" - Controller - for controller package,");
        print(" - Singleton - for model, DAO packages,");
        print(" - Decorator - for view package");
        print(" - Template - for view package");
        print(" - Action - for commands from controller to model");
        print(" - DTO - for transfer data and messages from DAO to view through controller");
        print("<br>");
        print("Also implemented:");
        print(" - General actions logger");
        print("<br>");
        print("<br>");
        print("<br>");
        printCenter(href("https://github.com/OleksijO/crud10/", "Application sources here.."));
        print("<br>");

    }
}