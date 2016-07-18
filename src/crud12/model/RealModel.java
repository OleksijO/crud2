package crud12.model;

import crud10.Constants;
import crud10.Logger.Logger;
import crud10.dto.*;
import crud12.dao.Dao;
import crud12.dao.DaoService;
import crud12.entities.Category;
import crud12.entities.DomainObject;
import crud12.entities.Product;
import crud12.spring.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model implementation
 */
public class RealModel implements Model {

    private Dao<Category, Integer> categoryDao;
    private Dao<Product, Integer> productDao;

    public RealModel() {
        categoryDao = DaoService.getCategoryDaoService();
        productDao = DaoService.getProductDaoService();
        Logger.log("MODEL HAS BEEN LOADED.");
    }

    private void logExceptionAndSetResultStatusMessage(String serviceName, Data result, Exception e) {
        e.printStackTrace();
        Logger.log(serviceName + "\t" + e.getMessage());
        result.setStatus(serviceName + "\t" + e.getMessage());
    }

    @Override
    public synchronized Data doAction(Action action) {
        Long timeStamp = System.currentTimeMillis();
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
                actionData = (Data) Context.getBean("dataDTO");
                try {
                    new TablesRefiller(categoryDao).refillDatabase();
                    //((TablesRefiller) Context.getBean("refiller")).refillDatabase();
                    actionData.setTotalNumberOfCategories(categoryDao.getTotalCount(Category.class));
                    actionData.setTotalNumberOfProducts(categoryDao.getTotalCount(Product.class));
                    actionData.setStatusOK();
                } catch (Exception e) {
                    logExceptionAndSetResultStatusMessage("REFILLING DATABASE:", actionData, e);
                }
                Logger.log(
                        String.format("REFILLING DATABASE:\tadded categories - %d, products - %d. RESULT: %s",
                                actionData.getTotalNumberOfCategories(),
                                actionData.getTotalNumberOfProducts(),
                                actionData.getStatus()));
                return actionData;
            default:
                throw new NoSuchMethodError();
        }
        Data data = getCategoryData(action.getParentId(), action.getFromNumberInItemSequence(), action.getItemQuantity());
        if (actionData != null) {
            data.setStatus(actionData.getStatus());
            data.setEditedItemId(actionData.getEditedItemId());
        }
        data.setCreationTime(timeStamp);
        return data;

    }


    private Data deleteItem(Item item) {
        Data data = (Data) Context.getBean("dataDTO");
        data.setEditedItemId(item.getId());
        if ((item.getItemType() == ItemType.CATEGORY) && (item.getId() == 0)) {
            Data result = (Data) Context.getBean("dataDTO");
            result.setStatus("MODEL: CAN'T DELETE '" + Constants.ROOT_CATEGORY_NAME + "'");
            Logger.log("MODEL: CAN'T DELETE '" + Constants.ROOT_CATEGORY_NAME + "'");
            return result;
        }
        try {
            if (item.getItemType() == ItemType.PRODUCT) {
                productDao.delete(item.getId());
                data.setStatusOK();
            } else if (item.getItemType() == ItemType.CATEGORY) {
                categoryDao.delete(item.getId());
                data.setStatusOK();
            } else {
                data.setStatus("MODEL: No such type of item.");
            }

        } catch (Exception e) {
            logExceptionAndSetResultStatusMessage("DAO:", data, e);
        }
        Logger.log("MODEL:\tDELETE ITEM: '" + item.getItemType() + "' id=" + item.getId() + "\tRESULT: " + data.getStatus());
        return data;
    }

    private Data addNewItem(Item item) {
        Data data = (Data) Context.getBean("dataDTO");
        if (checkInputData(item)) {
            try {
                if (item.getItemType() == ItemType.PRODUCT) {
                    Product product = (Product) Context.getBean("entity-product");
                    copyDataFromViewItemToEntity(item, product);
                    product.setPrice(item.getPrice());
                    product.setQuantity(item.getQuantity());
                    data.setEditedItemId(productDao.create(product));
                    data.setStatusOK();
                } else if (item.getItemType() == ItemType.CATEGORY) {
                    Category category = (Category) Context.getBean("entity-category");
                    copyDataFromViewItemToEntity(item, category);
                    data.setEditedItemId(categoryDao.create(category));
                    data.setStatusOK();
                } else {
                    data.setStatus("MODEL: No such type of item.");
                }
            } catch (Exception e) {
                logExceptionAndSetResultStatusMessage("DAO:", data, e);
            }
        } else {
            data.setStatus("MODEL: Input data incorrect!");
        }
        Logger.log("MODEL:\tADD ITEM: '" + item.getItemType() + "' id=" + item.getId() + "\tRESULT: " + data.getStatus());
        return data;
    }

    private void copyDataFromViewItemToEntity(Item item, DomainObject domainObject) {
        domainObject.setId(item.getId());
        domainObject.setParent(categoryDao.read(item.getParentId()));
        domainObject.setName(item.getName());
        domainObject.setDescription(item.getDescription());
    }

    private Data updateItem(Item item) {
        Data data = (Data) Context.getBean("dataDTO");
        data.setEditedItemId(item.getId());
        if (checkInputData(item)) {
            try {
                if (item.getItemType() == ItemType.PRODUCT) {
                    Product product = (Product) Context.getBean("entity-product");
                    copyDataFromViewItemToEntity(item, product);
                    product.setPrice(item.getPrice());
                    product.setQuantity(item.getQuantity());
                    productDao.update(product);
                    data.setStatusOK();
                } else if (item.getItemType() == ItemType.CATEGORY) {
                    Category category = (Category) Context.getBean("entity-category");
                    copyDataFromViewItemToEntity(item, category);
                    categoryDao.update(category);
                    data.setStatusOK();
                } else {
                    data.setStatus("MODEL: No such type of item.");
                }
            } catch (Exception e) {
                logExceptionAndSetResultStatusMessage("DAO:", data, e);
            }
        } else {
            data.setStatus("MODEL: Input data incorrect!");
        }
        Logger.log("MODEL:\tUPDATE ITEM: '" + item.getItemType() + "' id=" + item.getId() + "\tRESULT: " + data.getStatus());
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


    private Data getCategoryData(int id, int startFromSequenceItemNumber, int itemQuantity) {
        Data data = (Data) Context.getBean("dataDTO");

        try {
            Category currentCategory = categoryDao.read(id);
            data.setTotalNumberOfProducts(categoryDao.getTotalCount(Product.class));
            data.setTotalNumberOfCategories(categoryDao.getTotalCount(Category.class));
            List<Item> pathList = new ArrayList<>();
            pathList.add(createItemFromCategory(currentCategory));
            Category parent = currentCategory.getParent();
            while (parent != null) {
                pathList.add(createItemFromCategory(parent));
                parent = parent.getParent();
            }
            Collections.reverse(pathList);
            data.setPath(pathList);
            List<Item> items = new ArrayList<>();
            int totalSubItemCount = 0;
            int addedSubItemCount = 0;
            for (Category cat : currentCategory.getSubCategories()) {
                totalSubItemCount++;
                if ((totalSubItemCount >= startFromSequenceItemNumber)
                        && (addedSubItemCount<itemQuantity)) {
                    items.add(createItemFromCategory(cat));
                    addedSubItemCount++;
                }
            }
            for (Product product : currentCategory.getProducts()) {
                totalSubItemCount++;
                if ((totalSubItemCount >= startFromSequenceItemNumber)
                        && (addedSubItemCount<itemQuantity)) {
                    items.add(createItemFromProduct(product));
                    addedSubItemCount++;
                }
            }
            data.setItems(items);
            data.setNumberOfItemsInThisCategory(totalSubItemCount);
            data.setStatusOK();
        } catch (Exception e) {
            logExceptionAndSetResultStatusMessage("RETRIEVE CATEGORY DATA:",data,e);
        }
        return data;
    }

    private Item createItemFromCategory(Category origin) {
        Item item = new ItemDTO();
        item.setItemType(ItemType.CATEGORY);
        copyDataFromDomainObjectToItem(origin, item);
        return item;
    }

    private Item createItemFromProduct(Product origin) {
        Item item = new ItemDTO();
        item.setItemType(ItemType.PRODUCT);
        copyDataFromDomainObjectToItem(origin, item);
        item.setPrice(origin.getPrice());
        item.setQuantity(origin.getQuantity());
        return item;
    }

    private void copyDataFromDomainObjectToItem(DomainObject domObj, Item item) {
        item.setId(domObj.getId());
        if (domObj.getParent()!=null) {
            item.setParentId(domObj.getParent().getId());
        }else{
            item.setParentId(-1);
        }
        item.setName(domObj.getName());
        item.setDescription(domObj.getDescription());
    }


}
