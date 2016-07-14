package crud10.view;

import crud10.dto.PageParameters;
import crud10.dto.Data;

/**
 * Declares methods for showing pages and transfer data to decorators
 */
public interface PageTemplate extends Page {
    /**
     * Generates body of html page. Have to be overriden by inheritants
     */
    void showBody();

    /**
     * Generates header of html page. Overriden in abstract template
     */
    void showHeader();

    /**
     * Generates footer of html page. Overriden in abstract template
     */
    void showFooter();

    @Override
    void show();

    /**
     * Returns page parameters for decorators
     */
    PageParameters getParameters();

    /**
     * Returns DataDTO for decorators
     */
    Data getData();

}
