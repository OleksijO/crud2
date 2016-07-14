package crud10.dto;

import java.util.List;

/**
 * Interface of Data Object, containing info got from Model, and sent to view to be displayed
 */
public interface Data {
    /**
     * Returns status of applyed action in Model
     */
    String getStatus();

    /**
     * Returns list of subcategories and/or products to be displayed on this page number
     */
    List<Item> getItems();

    /**
     * Returns total count of products in database
     */
    int getTotalNumberOfProducts();

    /**
     * Returns total count of subitems in current category to count pages quantity
     */
    int getNumberOfItemsInThisCategory();

    /**
     * Returns list of category items, representing path from root to current category (including last)
     */
    List<Item> getPath();

    /**
     * Returns id of currently editing item
     */
    int getEditedItemId();

    /**
     * Returns creation time to count operation time in view modul
     */
    long getCreationTime();

    /**
     * Returns total count of categories in database
     */
    int getTotalNumberOfCategories();

    /**
     * Returns true if status of action is set to 'OK'
     */
    boolean isStatusOK();

    void setStatus(String status);

    void setItems(List<Item> items);

    void setTotalNumberOfProducts(int numberOfAllProducts);

    void setNumberOfItemsInThisCategory(int numberOfItemsInThisCategory);

    void setPath(List<Item> path);

    void setEditedItemId(int newItemId);

    void setTotalNumberOfCategories(int totalNumberOfCategories);

    void setStatusOK();

    void setCreationTime(long operationTime);


}
