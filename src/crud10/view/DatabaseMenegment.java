package crud10.view;

import crud10.dto.PageParameters;
import crud10.dto.Data;

import static crud10.Constants.*;
import static crud10.utils.Helper.*;

/**
 * This page calls refilling of database.
 */
public class DatabaseMenegment extends AbstractPageTemplate {
    private boolean renew = false;

    public DatabaseMenegment(PageParameters parameters, Data data) {
        super(parameters,data);
        getAdditionalRequestParameters(parameters);
    }

    @Override
    public void showBody() {
        if (renew) {
            if ("OK".equals(data.getStatus())) {
                printCenter("<br>");
                printCenter("If You see this massage, database was successfully renewed by "
                        + data.getTotalNumberOfProducts() + " products.");
                printCenter("<br>");
                printCenter("Refilling took "+(1.0*(System.currentTimeMillis() - data.getCreationTime())/1000)+" seconds.");
                printCenter("<br>");
                printCenter(href("?" + QUERY_PARAMETER_MODE + "=" + MODE_NAVI, "Go to navigating") + " or " + href(SITE_CHAPTER_ADDRESS, "go back."));
                printCenter("<br>");
            }else{
                printCenter("<br>");
                printCenter("OOOPS...Something is broken while renewing database...");
                printCenter("<br>");
                printCenter("Model: "+data.getStatus());
                printCenter("<br>");
            }
        } else {
            printCenter("<br>");
            printCenter("Are You sure to refill database? (this may take some time, cause... Max_depth=3, items_in_category =25)");
            printCenter("<br>");
            printCenter(href("?" + QUERY_PARAMETER_MODE + "=" + MODE_DATABASE_MANAGEMENT
                    + "&" + MODE_DATABASE_MANAGEMENT_RENEW + "=true", "- Yes, do it. -")
                    + "&nbsp;" + href(SITE_CHAPTER_ADDRESS, "- NO, go back. -"));
            printCenter("<br>");
        }
    }

    private void getAdditionalRequestParameters(PageParameters parameters) {
        if (parameters.getStringParameter(MODE_DATABASE_MANAGEMENT_RENEW) != null) {
            renew = true;
        }
    }
}
