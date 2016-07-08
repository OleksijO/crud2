package crud1.view.navi.decorators;


import crud1.view.PageTemplate;

/**
 * Shows 'item deleted' message
 */
public class ItemDeleted extends AbstractDecorator {


    public ItemDeleted(PageTemplate original) {
        super(original);
    }

    @Override
    public void showBody() {
        showItemDeletedMessage();
        original.showBody();
    }

    private void showItemDeletedMessage() {
        if (data.isStatusOK()) {
            printCenter("<b>MESSAGE:</b> Selected item with type [" + editItemType.toString().toLowerCase() + "] and id=" + data.getEditedItemId() + " have been deleted.");
        } else {
            printCenterError("<b>MESSAGE:</b> Can't delete " + editItemType.toString().toLowerCase() + " item with id=" + data.getEditedItemId() + " ! Please go back and check inputs.");
            printCenterError("Model: " + data.getStatus());
        }
        print("<hr>");
    }


}
