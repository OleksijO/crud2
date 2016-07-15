package crud10.dto;

import crud14.service.model.ItemType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static crud14.Constants.*;

/**
 * Page query parameters DTO.
 */
public class PageParametersDTO implements PageParameters {
    private Map<String, String[]> parametersMap;
    private String mode;
    private ItemType editItemType;
    private int itemsPerPage;
    private int pageNumber;
    private int parentId;
    private String editMode;
    private int editId;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;

    public PageParametersDTO() {
    }

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response=response;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException("Can't get output stream!");
        }
        parametersMap = request.getParameterMap();
        mode = getStringParameter(QUERY_PARAMETER_MODE);
        if (mode == null) mode = MODE_START;
        itemsPerPage = getIntParameter(QUERY_PARAMETER_ITEMS_PER_PAGE);
        if (itemsPerPage == -1) itemsPerPage = ITEMS_PER_PAGE;
        pageNumber = getIntParameter(QUERY_PARAMETER_PAGE);
        if (pageNumber < FIRST_PAGE_NUMBER) pageNumber = FIRST_PAGE_NUMBER;
        parentId = getIntParameter(QUERY_PARAMETER_PARENT_ID);
        if (parentId < ROOT_CATEGORY_ID) parentId = ROOT_CATEGORY_ID;
        editMode = getStringParameter(QUERY_PARAMETER_EDIT_MODE);
        editItemType = getEditItemTypeParameter(QUERY_PARAMETER_EDIT_ITEM_TYPE);
        if (editItemType == null) editItemType = ItemType.CATEGORY;
        editId = getIntParameter(QUERY_PARAMETER_EDIT_ITEM_ID);
    }

    @Override
    public String getStringParameter(String param) {
        if (parametersMap.containsKey(param)) {
            return parametersMap.get(param)[0];
        } else {
            return null;
        }
    }

    @Override
    public int getIntParameter(String param) {
        String stringValue = getStringParameter(param);
        if (stringValue == null) return -1;
        try {
            return Integer.parseInt(stringValue);
        } catch (Exception e) {
            return -1;
        }
    }

    private ItemType getEditItemTypeParameter(String param) {
        String stringValue = getStringParameter(param);
        if (stringValue == null) return null;
        try {
            return ItemType.valueOf(stringValue.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public HttpServletResponse getResponse() {
        return response;
    }

    @Override
    public String getMode() {
        return mode;
    }

    @Override
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public String getEditMode() {
        return editMode;
    }

    @Override
    public int getEditId() {
        return editId;
    }

    @Override
    public PrintWriter getOut() {
        return out;
    }

    @Override
    public ItemType getEditItemType() {
        return editItemType;
    }
}
