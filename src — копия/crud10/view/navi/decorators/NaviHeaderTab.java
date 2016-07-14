package crud10.view.navi.decorators;

import crud10.dto.Item;
import crud12.model.ItemType;
import crud10.view.PageTemplate;

import java.util.List;

import static crud10.Constants.*;
import static crud10.utils.Helper.href;
import static crud10.utils.Helper.queryBuilder;

/**
 * Shows naviation on pages, current category, "add item" hreference, some tech info...
 */
public class NaviHeaderTab extends AbstractDecorator {

    public NaviHeaderTab(PageTemplate original) {
        super(original);
    }

    @Override
    public void showBody() {
        showNavigationTree();
        showAddItemsReferences();
        original.showBody();
    }

    private void showNavigationTree() {
        List<Item> pathList = data.getPath();
        if (pathList.size()==0) {
            printCenterError("<b>MESSAGE:</b> OOPS. Something is broken...Can't find path in category tree...<br>");
            printCenter("Go back or to "+href(queryBuilder(MODE_NAVI, FIRST_PAGE_NUMBER, ROOT_CATEGORY_ID, null, null, -1), ROOT_CATEGORY_NAME));
            print("<hr>");
            return;
        }
        String subCategorySymbol = "&rarr;&nbsp;";
        StringBuilder subCategoryPrefix = new StringBuilder();
        for (Item item : pathList) {
            print(subCategoryPrefix.toString() + " "
                    + href(queryBuilder(MODE_NAVI, FIRST_PAGE_NUMBER, item.getId(), null, null, -1), item.getName())
                    + "<br>");
            subCategoryPrefix.append(subCategorySymbol);
        }
        showPagesTab();
        print("<hr>");
    }

    private void showPagesTab() {
        StringBuilder naviTab = new StringBuilder("Pages: ");
        int numberOfAllItems = data.getNumberOfItemsInThisCategory();
        int totalNumberOfPages = numberOfAllItems / itemsPerPage;
        if (numberOfAllItems % itemsPerPage > 0) totalNumberOfPages++;
        if (totalNumberOfPages <= 1) return;
        for (int i = 1; i <= totalNumberOfPages; i++) {
            String element;
            String query = queryBuilder(MODE_NAVI, i, parentId, null, null, -1);
            if (i == pageNumber) element = String.valueOf(i);
            else element = href(query, String.valueOf(i));
            naviTab.append(element + " ");
        }
        print(naviTab.toString());

    }

    private void showAddItemsReferences() {
        if (!((editMode != null) && (editMode.equals(EDIT_MODE_ITEM_ADD_NEW)))) {
            String createCategoryQuery = queryBuilder(MODE_NAVI, pageNumber, parentId, EDIT_MODE_ITEM_ADD_NEW, ItemType.CATEGORY, -1);
            String createProductQuery = queryBuilder(MODE_NAVI, pageNumber, parentId, EDIT_MODE_ITEM_ADD_NEW, ItemType.PRODUCT, -1);
            printCenter("Total: products - " + data.getTotalNumberOfProducts() + ", categories - " + data.getTotalNumberOfCategories() + ". &nbsp; " +
                    "Data obtained in " + (System.currentTimeMillis() - data.getCreationTime()) + " ms.<br>"
                    + "Add here new: "
                    + href(createCategoryQuery, "category")
                    + " or " + href(createProductQuery, "product")
                    + "<hr>");
        }
    }
}
