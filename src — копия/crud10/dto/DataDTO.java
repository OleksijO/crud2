package crud10.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03.07.2016.
 */
public class DataDTO implements Data {
    private String status;
    private List<Item> items=new ArrayList<>();
    private int totalNumberOfProducts;
    private int numberOfItemsInThisCategory;
    private List<Item> path=new ArrayList<>();
    private int editedItemId;
    private int totalNumberOfCategories;
    private long creationTime =System.currentTimeMillis();

    public void setEditedItemId(int editedItemId) {
        this.editedItemId = editedItemId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public void setTotalNumberOfCategories(int totalNumberOfCategories) {
        this.totalNumberOfCategories = totalNumberOfCategories;
    }

    @Override
    public int getTotalNumberOfCategories() {

        return totalNumberOfCategories;
    }

    @Override
    public boolean isStatusOK() {
        return "OK".equalsIgnoreCase(status);
    }

    @Override
    public void setStatusOK() {
        status="OK";
    }

    public int getEditedItemId() {
       return editedItemId;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    public int getTotalNumberOfProducts() {
        return totalNumberOfProducts;
    }

    @Override
    public int getNumberOfItemsInThisCategory() {
        return numberOfItemsInThisCategory;
    }

    @Override
    public List<Item> getPath() {
        return path;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setTotalNumberOfProducts(int totalNumberOfProducts) {
        this.totalNumberOfProducts = totalNumberOfProducts;
    }

    @Override
    public void setNumberOfItemsInThisCategory(int numberOfItemsInThisCategory) {
        this.numberOfItemsInThisCategory = numberOfItemsInThisCategory;
    }

    @Override
    public void setPath(List<Item> path) {
        this.path = path;
    }
}
