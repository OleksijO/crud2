package crud10.dto;

import crud12.model.ItemType;

/**
 * Created by User on 30.06.2016.
 */
public interface Item {
    int getId();

    int getParentId();

    String getName();

    String getDescription();

    ItemType getItemType();

    long getPrice();

    int getQuantity();

    void setId(int id);

    void setParentId(int parentId);

    void setItemType(ItemType itemType);

    void setName(String name);

    void setDescription(String description);

    void setPrice(long price);

    void setQuantity(int quantity);

}
