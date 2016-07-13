package crud10.dto;

import crud12.model.ModelAction;

/**
 *  Declares methods of Action DTO
 */
public interface Action {
    void setModelAction(ModelAction modelAction);

    ModelAction getModelAction();

    void setItem(Item item);

    Item getItem();

    int getParentId();

    int getFromNumberInItemSequence();

    int getItemQuantity();
}
