package crud10.view.navi.decorators;

import crud10.view.PageTemplate;

/**
 * Shows 'item updated' message
 */
public class ItemUpdated extends AbstractDecorator {
    public ItemUpdated(PageTemplate original) {
        super(original);
    }

    @Override
    public void showBody() {
        showItemUpdatedMessage();
        original.showBody();
    }

    private void showItemUpdatedMessage() {
        if (data.isStatusOK()) {
            printCenter("<b>MESSAGE:</b> Item of type=" + editItemType.toString().toLowerCase() + " with id=" + data.getEditedItemId() + " have been updated in database.");
        } else {
            printCenterError("<b>MESSAGE:</b> Can't update " + editItemType.toString().toLowerCase() + " item! Please go back and check inputs.");
            printCenterError("Model: " + data.getStatus());
        }
        print("<hr>");
    }
}
