package crud10.dto;

import crud14.model.ModelAction;

/**
 * Declares methods of Action DTO
 */
public interface Action {
    /**
     * ModelAction declares action for Model
     */
    void setModelAction(ModelAction modelAction);

    ModelAction getModelAction();

    /**
     * Item contains data to be stored in database or type and id of data to be deleted
     */
    void setItem(Item item);

    Item getItem();

    /**
     * parentId represents category id,  currently is being viewed by user
     */
    int getParentId();

    /**
     * fromNumberInItemSequense represents sequental number of item of current category in database,
     * from which starts current page
     */
    int getFromNumberInItemSequence();

    /**
     * itemQuantity represents how many items have to be displayed on one page
     */
    int getItemQuantity();
}
