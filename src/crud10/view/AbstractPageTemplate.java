package crud10.view;

import crud10.Constants;
import crud10.dto.PageParameters;
import crud10.dto.Data;
import crud14.service.model.ItemType;
import crud10.utils.Helper;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * This is template of usual this site chapter page.
 */

public abstract class AbstractPageTemplate implements PageTemplate {
    protected PrintWriter out;
    protected String title;
    protected String mode;
    protected ItemType editItemType;
    protected int itemsPerPage;
    protected int pageNumber;
    protected int parentId;
    protected String editMode;
    protected int editId;
    protected HttpServletRequest request;
    protected PageParameters parameters;
    protected Data data;

    public AbstractPageTemplate(PageParameters parameters) {
        this.parameters = parameters;
        init();
    }

    public AbstractPageTemplate(PageParameters parameters, Data data) {
        this.parameters = parameters;
        this.data = data;
        init();
    }

    private void init() {
        out = parameters.getOut();
        mode = parameters.getMode();
        editItemType = parameters.getEditItemType();
        itemsPerPage = parameters.getItemsPerPage();
        pageNumber = parameters.getPageNumber();
        parentId = parameters.getParentId();
        editMode = parameters.getEditMode();
        editId = parameters.getEditId();
        title = Constants.TITLE;
    }

    @Override
    public void showHeader() {
        out.println("<html>\n" +
                "<head>\n" +
                "<title>" + title + "</title>\n" +
                "</head>\n" +
                "<body style=\" background: #EEEEEE; \">\n" +
                "<div align=\"center\" style=\" background: #EEEEEE; \">\n" +
                "    <div align=\"center\" style=\"width: 1024px;  padding-top: 25px; background: #EEF4FF; \">\n" +
                "        <div style=\"width: 768px; padding: 2px;text-align: center; background: #EEF4FF; \">\n" +
                "            <h3>" + title + "</h3><br>\n" +
                "            " + Helper.href(Constants.SITE_CHAPTER_ADDRESS, "back to home page") +
                "        </div>\n" +
                "        <hr>\n");
    }

    @Override
    public void showFooter() {
        out.println(
                "        <hr>\n" +
                        "        <br\n" +

                        "    </div>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>");
    }


    @Override
    public void show() {
        showHeader();
        showBody();
        showFooter();
    }

    protected void print(String text) {
        Helper.printDiv(out, text, "left");
    }

    protected void printError(String text) {
        Helper.printDiv(out, "<font color=red>" + text + "</font>", "left");
    }

    protected void printCenter(String text) {
        Helper.printDiv(out, text, "center");
    }

    protected void printCenterError(String text) {
        Helper.printDiv(out, "<font color=red>" + text + "</font>", "center");
    }

    @Override
    public PageParameters getParameters() {
        return parameters;
    }

    @Override
    public Data getData() {
        return data;
    }

}
