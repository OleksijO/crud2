package crud1.model;

import crud1.Constants;
import crud1.Logger.Logger;
import crud1.dao.DAOService;
import crud1.dto.Action;
import crud1.dao.DAOServiceImpl;
import crud1.dto.Data;
import crud1.dto.DataDTO;
import crud1.dto.Item;
import crud1.dto.ItemDTO;

import java.util.*;

/**
 * Model implementation
 */
public class RealModel implements Model {
    private static Model instance = new RealModel();
    private Map<Integer, Item> products = new HashMap<>();
    private Map<Integer, Item> categories = new TreeMap<>();
    private DAOService database;

    private RealModel() {
        database = DAOServiceImpl.getInstance();
    }

    public static Model getInstance() {
        return instance;
    }

    @Override
    public synchronized Data doAction(Action action) {
        Data actionData = null;
        switch (action.getModelAction()) {
            case GET_LIST:
                break;
            case ITEM_DELETE:
                actionData = deleteItem(action.getItem());
                break;
            case ITEM_ADD:
                actionData = addNewItem(action.getItem());
                break;
            case ITEM_UPDATE:
                actionData = updateItem(action.getItem());
                break;
            case DATABASE_RESTORE:
                return restoreTables();
            default:
                throw new NoSuchMethodError();
        }
        Data data = getListItems(action.getParentId(), action.getFromNumberInItemSequence(), action.getItemQuantity());
        if (actionData != null) {
            data.setStatus(actionData.getStatus());
            data.setEditedItemId(actionData.getEditedItemId());
        }
        return data;

    }


    private List<Item> getFakeTables() {
        List<Item> list = new ArrayList<>();
        Set<Integer> keySet = categories.keySet();
        for (int id : keySet) {
            list.add(categories.get(id));
        }
        keySet = products.keySet();
        for (int id : keySet) {
            list.add(products.get(id));
        }
        return list;
    }

    private Data deleteItem(Item item) {
        Data timedata = new DataDTO();
        if ((item.getItemType() == ItemType.CATEGORY) && (item.getId() == 0)) {
            Data result = new DataDTO();
            result.setStatus("MODEL: CAN'T DELETE '" + Constants.ROOT_CATEGORY_NAME + "'");
            Logger.log("MODEL: CAN'T DELETE '" + Constants.ROOT_CATEGORY_NAME + "'");
            return result;
        }
        Data data = database.deleteItem(item);
        data.setCreationTime(timedata.getCreationTime());
        return data;
    }

    private Data addNewItem(Item item) {
        Data data = new DataDTO();
        if (checkInputData(item)) {
            Data tmpData = database.addNewItem(item);
            if (tmpData.isStatusOK()) {
                data.setEditedItemId(tmpData.getEditedItemId());
                data.setStatusOK();
            } else {
                data.setStatus(tmpData.getStatus());
            }
        } else {
            data.setStatus("Input data incorrect!");
        }
        return data;
    }

    private Data updateItem(Item item) {
        Data data = new DataDTO();
        data.setEditedItemId(item.getId());
        if (checkInputData(item)) {
            Data tmpData = database.updateItem(item);
            if (tmpData.isStatusOK()) {
                data.setEditedItemId(tmpData.getEditedItemId());
                data.setStatusOK();
            } else {
                data.setStatus(tmpData.getStatus());
            }
        } else {
            data.setStatus("Input data incorrect!");
        }
        return data;
    }

    private boolean checkInputData(Item item) {
        if (item.getItemType() == ItemType.PRODUCT) {
            if (item.getPrice() < 0) item.setPrice(0);
            if (item.getQuantity() < 0) item.setQuantity(0);
        }
        if (item.getName().length() == 0) return false;
        if (item.getDescription().length() == 0) return false;
        return true;
    }

    private Map<Integer, Item> getTable(ItemType itemType) {
        switch (itemType) {
            case CATEGORY:
                return categories;
            case PRODUCT:
                return products;
            default:
                throw new RuntimeException("No such ItemType!");
        }
    }

    private Data getListItems(int parentId, int startFromSequenceItemNumber, int itemQuantity) {
        Data data = new DataDTO();
        Data tmpData = database.getTotalItemCountsOfTables();
        if (tmpData.isStatusOK()) {
            data.setTotalNumberOfProducts(tmpData.getTotalNumberOfProducts());
            data.setTotalNumberOfCategories(tmpData.getTotalNumberOfCategories());
        } else {
            data.setStatus(tmpData.getStatus());
            return data;
        }
        tmpData = database.getPath(parentId);
        if (tmpData.isStatusOK()) {
            data.setPath(tmpData.getPath());
        } else {
            data.setStatus(tmpData.getStatus());
            return data;
        }
        tmpData = database.getMainListItems(parentId, startFromSequenceItemNumber, itemQuantity);
        if (tmpData.isStatusOK()) {
            data.setItems(tmpData.getItems());
            data.setNumberOfItemsInThisCategory(tmpData.getNumberOfItemsInThisCategory());
        } else {
            data.setStatus(tmpData.getStatus());
            return data;
        }
        data.setStatusOK();
        return data;
    }

    ////////////////     Generating data for refill database       //////////////////////////

    private Data restoreTables() {
        Data data = new DataDTO();
        restoreTables(2, 25);
        Data result = database.refillDatabase(getFakeTables());
        result.setCreationTime(data.getCreationTime());
        return result;
    }

    private void restoreTables(int maxDepth, int maxInList) {
        categories.clear();
        products.clear();
        fillCategoryList(0, maxDepth, maxInList);
    }

    private void fillCategoryList(int categoryId, int maxDepth, int maxInList) {
        if ((Math.random() > 0.7) && (categoryId > 0)) fillCategoryListByProducts(categoryId, maxInList);
        else {
            fillCategoryListByCategories(categoryId, maxDepth, maxInList);

        }
    }

    private void fillCategoryListByCategories(int categoryId, int maxDepth, int maxInList) {
        int numberOfCategories = (int) (Math.random() * (maxInList + 1));
        for (int i = 0; i < numberOfCategories; i++) {
            Item cat = new ItemDTO();
            cat.setId(categories.size() + 1);
            cat.setParentId(categoryId);
            cat.setItemType(ItemType.CATEGORY);
            cat.setName("Category of products  id=" + cat.getId());
            cat.setDescription("Описание категории с именем: " + cat.getName());
            categories.put(cat.getId(), cat);
            if (getPathListDepth(cat.getId()) <= maxDepth - 1)
                fillCategoryList(cat.getId(), maxDepth, maxInList);
            else fillCategoryListByProducts(cat.getId(), maxInList);

        }
    }

    public int getPathListDepth(int itemId) {
        int depth = 0;
        while (itemId != 0) {
            Item viewItem = getItem(ItemType.CATEGORY, itemId);
            itemId = viewItem.getParentId();
            depth++;
        }

        return depth;
    }

    private void fillCategoryListByProducts(int categoryId, int maxInList) {
        int numberOfProducts = (int) (Math.random() * (maxInList + 1));
        for (int i = 0; i < numberOfProducts; i++) {
            Item product = new ItemDTO();
            product.setId(products.size() + 1);
            product.setItemType(ItemType.PRODUCT);
            product.setParentId(categoryId);
            product.setName("Product name - " + generateRandomString());
            product.setDescription("Product decscription: product in category id=" + categoryId + ". Further product description....bla-bla-bla.....");
            product.setPrice((long) (Math.random() * 100000) + 1);
            product.setQuantity((int) (Math.random() * 100));
            products.put(product.getId(), product);
        }

    }

    private String generateRandomString() {
        char[] name = new char[3 + (int) (Math.random() * 7)];
        for (int j = 0; j < name.length; j++) {
            name[j] = (char) ('a' + (int) (Math.random() * 24));
        }
        return new String(name);
    }

    private Item getItem(ItemType itemType, int itemId) {
        Map<Integer, Item> table = getTable(itemType);
        if (table.containsKey(itemId)) return table.get(itemId);
        else throw new NoSuchElementException();
    }
}
