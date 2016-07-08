package crud1.dto;

import crud1.model.ModelAction;

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
