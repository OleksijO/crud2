package crud1.view.navi;

import crud1.dto.PageParameters;
import crud1.dto.Data;
import crud1.dto.Item;
import crud1.model.*;
import crud1.utils.Helper;
import crud1.view.AbstractPageTemplate;

import java.util.Locale;

import static crud1.Constants.EDIT_MODE_ITEM_DELETED;
import static crud1.Constants.EDIT_MODE_ITEM_EDIT;
import static crud1.Constants.MODE_NAVI;
import static crud1.utils.Helper.href;
import static crud1.utils.Helper.queryBuilder;

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


    @Override
    public void setData(Data data) {
        this.data=data;
    }
}
