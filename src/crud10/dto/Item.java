package crud10.dto;

import crud14.service.model.ItemType;

/**
 * Interface of Item, which can represent category or product for view.
 * Some methods must return special not null values for categories
 */
public interface Item {
    int getId();

    int getParentId();

    String getName();

    String getDescription();

    ItemType getItemType();

    /**
     * Must return '-1' for categories
     */
    long getPrice();

    /**
     * Must return '-1' for categories
     */
    int getQuantity();

    void setId(int id);

    void setParentId(int parentId);

    void setItemType(ItemType itemType);

    void setName(String name);

    void setDescription(String description);

    void setPrice(long price);

    void setQuantity(int quantity);

}
