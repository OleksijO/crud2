package crud10.dto;

import crud14.service.model.ItemType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Interface of Data Object, which contains info from request and respond,
 * necessary for controller and view
 */
public interface PageParameters {
    void init(HttpServletRequest request, HttpServletResponse response);

    String getStringParameter(String param);

    int getIntParameter(String param);

    HttpServletRequest getRequest();

    HttpServletResponse getResponse();

    String getMode();

    int getItemsPerPage();

    int getPageNumber();

    int getParentId();

    String getEditMode();

    int getEditId();

    PrintWriter getOut();

    ItemType getEditItemType();
}
