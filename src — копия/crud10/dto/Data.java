package crud10.dto;

import java.util.List;

/**
 * Created by User on 03.07.2016.
 */
public interface Data {
    String getStatus();

    List<Item> getItems();

    int getTotalNumberOfProducts();

    int getNumberOfItemsInThisCategory();

    List<Item> getPath();

    int getEditedItemId();

    void setStatus(String status);

    void setItems(List<Item> items);

    void setTotalNumberOfProducts(int numberOfAllProducts);

    void setNumberOfItemsInThisCategory(int numberOfItemsInThisCategory);

    void setPath(List<Item> path);

    void setEditedItemId(int newItemId);

    void setTotalNumberOfCategories(int totalNumberOfCategories);

    int getTotalNumberOfCategories();

    boolean isStatusOK();

    void setStatusOK();

    long getCreationTime();

    void setCreationTime(long operationTime);


}
