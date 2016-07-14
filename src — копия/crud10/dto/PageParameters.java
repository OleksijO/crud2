package crud10.dto;

import crud12.model.ItemType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by User on 07.07.2016.
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
