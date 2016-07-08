package crud1.dto;

import crud1.model.ItemType;

import java.util.List;

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
