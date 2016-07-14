package crud10.utils;

import crud10.dto.PageParameters;
import crud10.dto.Item;
import crud14.model.ItemType;

import java.io.PrintWriter;
import java.util.Locale;

import static crud10.Constants.*;

/**
 *  HTML code helper generator
 */
public class Helper {
    /**
     *  Generates HTML HREF
     */
    public static String href(String href, String description) {
        return "<a href=\"" + href + "\">" + description + "</a>";
    }
    /**
     *  Generates HTML DIV into outputstream with specified TEXT and ALIGN
     */
    public static void printDiv(PrintWriter out, String text, String align) {
        out.println("        <div style=\"width: 768px; padding: 2px;text-align: " + align + "; background: #EEF4FF; \">\n"
                + "              " + text
                + "        </div>\n");
    }
    /**
     *  Generates GET QUERY for a reference
     */
    public static String queryBuilder(String mode, int pageNumber, int parentId, String editMode, ItemType editItemType, int editId) {
        StringBuilder query = new StringBuilder("?");
        if (mode != null) query.append(QUERY_PARAMETER_MODE + "=").append(mode);
        if (pageNumber >= FIRST_PAGE_NUMBER) query.append("&" + QUERY_PARAMETER_PAGE + "=").append(pageNumber);
        query.append("&" + QUERY_PARAMETER_PARENT_ID + "=").append(parentId);
        if (editMode != null) {
            query.append("&" + QUERY_PARAMETER_EDIT_MODE + "=").append(editMode);
        }
        if (editItemType != null) {
            query.append("&" + QUERY_PARAMETER_EDIT_ITEM_TYPE + "=").append(editItemType.toString().toLowerCase());
        }
        if (editId > 0) {
            query.append("&" + QUERY_PARAMETER_EDIT_ITEM_ID + "=").append(editId);
        }
        return query.toString();
    }
    /**
     *  Generates HTML edit or create item form
     */
    public static void showEditForm(String formEditMode, ItemType formEditItemType, PageParameters parameters, Item viewItem) {
        int pageNumber = parameters.getPageNumber();
        int parentId = parameters.getParentId();
        int formEditId = parameters.getEditId();
        PrintWriter out = parameters.getOut();

        StringBuilder createForm = new StringBuilder();
        String name = "";
        String descriprion = "";
        String price = "";
        String quantity = "";
        String submitButtonName = "";
        String queryNextPageMode = "";
        if (EDIT_MODE_ITEM_ADD_NEW.equals(formEditMode)) {
            printDiv(out, "<b>Creating new " + formEditItemType.toString().toLowerCase() + ":</b><br>", "left");
            submitButtonName = "Add new " + formEditItemType.toString().toLowerCase();
            queryNextPageMode = EDIT_MODE_ITEM_ADDED;
        } else if (EDIT_MODE_ITEM_EDIT.equals(formEditMode)) {
            printDiv(out, "<b>Update " + formEditItemType.toString().toLowerCase() + " with id=" + formEditId + ":</b><br>", "left");
            name = viewItem.getName();
            descriprion = viewItem.getDescription();
            price = String.format(Locale.ENGLISH, "%.2f", 0.01 * viewItem.getPrice());
            quantity = String.valueOf(viewItem.getQuantity());
            submitButtonName = "Update " + formEditItemType.toString().toLowerCase();
            queryNextPageMode = EDIT_MODE_ITEM_UPDATED;
        }
        String editFormQuery = queryBuilder(MODE_NAVI, pageNumber, parentId, queryNextPageMode, formEditItemType, formEditId);
        createForm.append("<form align=center action=\"").append(editFormQuery).append("\" method=\"post\">\n")
                .append("<table style=\"margin:0 auto; width:90%\">\n")
                .append("<tr>\n")
                .append("<td>").append(formEditItemType.toString().toLowerCase()).append(" name:</td>\n")
                .append("<td style=\"width:75%\"><input style=\"width:100%\" type=\"text\" name=\"").append(QUERY_PARAMETER_EDIT_FORM_NAME).append("\" value=\"").append(name).append("\"></td>\n")
                .append("</tr>\n")
                .append("<tr>\n")
                .append("<td>").append(formEditItemType.toString().toLowerCase()).append(" description:</td>\n")
                .append("<td style=\"width:75%\"><input style=\"width:100%\"type=\"text\" name=\"").append(QUERY_PARAMETER_EDIT_FORM_DESCRIPTION).append("\" value=\"").append(descriprion).append("\"></td>\n")
                .append("</tr>\n");
        if (formEditItemType == ItemType.PRODUCT) {
            createForm.append("<tr>\n" + "<td>").append(formEditItemType.toString().toLowerCase()).append(" price:</td>\n")
                    .append("<td><input type=\"text\" name=\"").append(QUERY_PARAMETER_EDIT_FORM_PRICE).append("\" value=\"").append(price).append("\"> USD</td>\n")
                    .append("</tr>\n")
                    .append("<tr>\n")
                    .append("<td>").append(formEditItemType.toString().toLowerCase()).append(" quantity left:</td>\n")
                    .append("<td><input type=\"text\" name=\"").append(QUERY_PARAMETER_EDIT_FORM_QUANTITY).append("\" value=\"").append(quantity).append("\"> pcs</td>\n")
                    .append("</tr>\n");
        }
        createForm.append("" + "</table>\n" + "<p style=\"text-align: center\">" + "<input type = \"submit\" value = \"")
                .append(submitButtonName).append("\" >").append("</p >\n")
                .append("</form >");
        printDiv(out, createForm.toString(), "center");
    }
}
