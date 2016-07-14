package crud10.dto;

import crud14.model.ModelAction;

/**
 * Created by User on 03.07.2016.
 */
public class ActionDTO implements Action {
    private Item item;
    private int parentId;
    private int fromNumberInItemSequence;
    private int itemQuantity;
    private ModelAction modelAction;

    public ActionDTO(int parentId, int fromNumberInItemSequence, int itemQuantity) {
        this.parentId = parentId;
        this.fromNumberInItemSequence = fromNumberInItemSequence;
        this.itemQuantity = itemQuantity;
    }

    public ActionDTO(ModelAction modelAction) {
        this.modelAction = modelAction;
    }

    @Override
    public void setModelAction(ModelAction modelAction) {
        this.modelAction = modelAction;
    }

    @Override
    public ModelAction getModelAction() {

        return modelAction;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public int getFromNumberInItemSequence() {
        return fromNumberInItemSequence;
    }

    @Override
    public int getItemQuantity() {
        return itemQuantity;
    }
}
