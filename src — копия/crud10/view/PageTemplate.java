package crud10.view;

import crud10.dto.PageParameters;
import crud10.dto.Data;

/**
 *  Declares methods for showing pages and transfer data to decorators
 */
public interface PageTemplate extends Page {
    void showBody();

    void showHeader();

    void showBottom();

    @Override
    void show();

    PageParameters getParameters();

    Data getData();

    void setData(Data data);

}
