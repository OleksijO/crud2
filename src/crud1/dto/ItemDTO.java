package crud1.dto;

import crud1.model.ItemType;

/**
 * Created by User on 30.06.2016.
 */
public class ItemDTO implements Item {
    protected int id;
    protected int parentId;
    protected ItemType itemType;
    protected String name;
    protected String description;
    protected long price; // -1 for categories
    protected int quantity;

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
        if (itemType == ItemType.CATEGORY) return -1;
        return quantity;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public long getPrice() {
        if (itemType == ItemType.CATEGORY) return -1;
        else if (itemType == ItemType.PRODUCT) return price;
        throw new RuntimeException("No such ItemType!");
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setPrice(long price) {
        this.price = price;
    }


}
