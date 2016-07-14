package crud10.view.navi;

import crud10.dto.PageParameters;
import crud10.dto.Data;
import crud10.dto.Item;
import crud14.model.*;
import crud10.utils.Helper;
import crud10.view.AbstractPageTemplate;

import java.util.Locale;

import static crud10.Constants.EDIT_MODE_ITEM_DELETED;
import static crud10.Constants.EDIT_MODE_ITEM_EDIT;
import static crud10.Constants.MODE_NAVI;
import static crud10.utils.Helper.href;
import static crud10.utils.Helper.queryBuilder;

/**
 * Shows items (page's body)
 */
public class Body extends AbstractPageTemplate {

    public Body(PageParameters parameters) {
        super(parameters);
    }

    public Body(PageParameters parameters, Data data) {
        super(parameters, data);
    }

    @Override
    public void showBody() {
        if (data.getItems().size() > 0) {
            for (Item itemToShow : data.getItems()) {
                if (EDIT_MODE_ITEM_EDIT.equals(editMode)) {
                    if ((editItemType == itemToShow.getItemType()) && (itemToShow.getId() == editId)) {
                        showEditItem(itemToShow);
                        continue;
                    }
                }
                showItem(itemToShow);
            }
        } else {
            print("There are no items in this category...");

        }
    }

    public void showItem(Item item) {
        String editQuery = queryBuilder(MODE_NAVI, pageNumber, item.getParentId(), EDIT_MODE_ITEM_EDIT, item.getItemType(), item.getId());
        String deleteQuery = queryBuilder(MODE_NAVI, pageNumber, item.getParentId(), EDIT_MODE_ITEM_DELETED, item.getItemType(), item.getId());
        print("<br>");
        String name = "<b>" + item.getName() + "</b>";
        if (item.getItemType() == ItemType.CATEGORY) {
            String categoryQuery = queryBuilder(MODE_NAVI, 1, item.getId(), null, null, -1);
            name = href(categoryQuery, name);
        }
        print(name);
        print("Description : " + item.getDescription());
        if (item.getItemType() == ItemType.PRODUCT) {
            print("Price : " + String.format(Locale.ENGLISH, "%.2f USD", 0.01 * item.getPrice()));
            print("Quantity : " + String.format(Locale.ENGLISH, "%d pcs", item.getQuantity()));
        }
        print("Item edit options : " + href(editQuery, "=EDIT=") + " &nbsp; " + href(deleteQuery, "=DELETE="));

    }

    public void showEditItem(Item item) {
        print("<br>");
        print("<hr color=blue size=1>");
        Helper.showEditForm(EDIT_MODE_ITEM_EDIT, item.getItemType(), parameters, item);
        print("<hr color=blue size=1>");

    }
}
