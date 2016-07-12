package crud10.view.navi.decorators;

import crud10.view.PageTemplate;

/**
 * Shows 'item added' message
 */
public class ItemAdded extends AbstractDecorator {
    public ItemAdded(PageTemplate original) {
        super(original);
    }

    @Override
    public void showBody() {
        showCreateItemMessage();
        original.showBody();
    }

    private void showCreateItemMessage() {
        if (data.isStatusOK()) {
            printCenter("<b>MESSAGE:</b> New " + editItemType.toString().toLowerCase() + " item with id=" + data.getEditedItemId() + " have been added to database.");
        } else {
            printCenterError("<b>MESSAGE:</b> Can't add new " + editItemType.toString().toLowerCase() + " item! Please go back and check inputs.");
            printCenterError("Model: " + data.getStatus());
        }
        print("<hr>");
    }

}
