package crud10.controller;

import crud10.Constants;
import crud10.dto.*;
import crud14.service.model.ItemType;
import crud14.service.model.Model;
import crud14.service.model.ModelAction;
import crud10.view.Page;
import crud10.view.PageTemplate;
import crud10.view.navi.Body;
import crud10.view.navi.decorators.*;

import java.io.IOException;

import static crud10.Constants.*;
import static crud14.service.model.ModelAction.*;

/**
 * Created by User on 03.07.2016.
 */
public class NavigationController extends AbstractController {
    private String mode;
    private ItemType editItemType;
    private int itemsPerPage;
    private int pageNumber;
    private int parentId;
    private String editMode;
    private int editId;

    public NavigationController(PageParameters parameters, Model model) {
        super(parameters, model);
        init();
    }

    private void init() {

        mode = parameters.getMode();
        editItemType = parameters.getEditItemType();
        itemsPerPage = parameters.getItemsPerPage();
        pageNumber = parameters.getPageNumber();
        parentId = parameters.getParentId();
        editMode = parameters.getEditMode();
        editId = parameters.getEditId();
    }

    @Override
    public Page execute() throws IOException {
        PageTemplate resultPage;
        Data data;

        if (EDIT_MODE_ITEM_DELETED.equals(editMode)) {
            data = model.doAction(getAction(ITEM_DELETE));
            if ((data.getItems().size() == 0) && (pageNumber > 1)) {
                pageNumber=pageNumber-1;
                Data dataList = model.doAction(getAction(GET_LIST));
                data.setItems(dataList.getItems());
                resultPage = new Body(parameters, data);
            }else{
                resultPage = new Body(parameters, data);
            }
            resultPage = new ItemDeleted(new NaviHeaderTab(resultPage));

        } else if (EDIT_MODE_ITEM_UPDATED.equals(editMode)) {
            data = model.doAction(getAction(ITEM_UPDATE));
            resultPage = new Body(parameters, data);
            resultPage = new ItemUpdated(new NaviHeaderTab(resultPage));
        } else if (EDIT_MODE_ITEM_ADDED.equals(editMode)) {
            data = model.doAction(getAction(ITEM_ADD));
            resultPage = new Body(parameters, data);
            resultPage = new ItemAdded(new NaviHeaderTab(resultPage));
        } else if (EDIT_MODE_ITEM_ADD_NEW.equals(editMode)) {
            data = model.doAction(getAction(GET_LIST));
            resultPage = new Body(parameters, data);
            resultPage = new NaviHeaderTab(new AddItem(resultPage));
        } else {
            data = model.doAction(getAction(GET_LIST));
            resultPage = new Body(parameters, data);
            resultPage = new NaviHeaderTab(resultPage);
        }


        return resultPage;
    }


    protected Item getNewItemWithData() {
        Item Item = new ItemDTO();
        Item.setName(parameters.getRequest().getParameter(Constants.QUERY_PARAMETER_EDIT_FORM_NAME));
        Item.setDescription(parameters.getRequest().getParameter(Constants.QUERY_PARAMETER_EDIT_FORM_DESCRIPTION));
        if (editItemType == ItemType.PRODUCT) {
            String price = parameters.getRequest().getParameter(Constants.QUERY_PARAMETER_EDIT_FORM_PRICE);
            if (price != null) {
                price.replace(",", ".");
                try {
                    Item.setPrice((long) (Float.parseFloat(price) * 100));
                } catch (Exception e) {
                    Item.setPrice(-1);
                }
            } else Item.setPrice(-1);
            String quantity = parameters.getRequest().getParameter(Constants.QUERY_PARAMETER_EDIT_FORM_QUANTITY);
            try {
                Item.setQuantity(Integer.parseInt(quantity));
            } catch (Exception e) {
                Item.setQuantity(-1);
            }
        }
        Item.setItemType(editItemType);
        Item.setParentId(parentId);
        return Item;
    }

    private int firstItemOnPageNumber() {
        return (pageNumber - 1) * itemsPerPage+1;
    }

    private Action getAction(ModelAction modelAction) {
        Action action = new ActionDTO(parentId, firstItemOnPageNumber(), itemsPerPage);
        action.setModelAction(modelAction);
        Item item;
        switch (modelAction) {
            case ITEM_ADD:
                item = getNewItemWithData();
                action.setItem(item);
                break;
            case ITEM_DELETE:
                item = getNewItemWithData();
                item.setId(editId);
                action.setItem(item);
                break;
            case ITEM_UPDATE:
                item = getNewItemWithData();
                item.setId(editId);
                action.setItem(item);
                break;
        }
        return action;

    }

}
