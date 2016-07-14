package crud10.view.navi.decorators;

import crud10.utils.Helper;
import crud10.view.PageTemplate;

import static crud10.Constants.*;

/**
 * Shows  'create item'  form.
 */

public class AddItem extends AbstractDecorator {

    public AddItem(PageTemplate original) {
        super(original);

    }

    private void showNewItem() {
        Helper.showEditForm(EDIT_MODE_ITEM_ADD_NEW, editItemType, parameters, null);
        print("<hr>");
    }

    @Override
    public void showBody() {
        showNewItem();
        original.showBody();

    }
}
