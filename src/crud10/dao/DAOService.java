package crud10.dao;

import crud10.dto.Data;
import crud10.dto.Item;

import java.util.List;

/**
 * Created by User on 07.07.2016.
 */
public interface DAOService {
    Data deleteItem(Item item);

    Data updateItem(Item item);

    Data addNewItem(Item item);

    Data getPath(int id);

    Data getTotalItemCountsOfTables();

    Data refillDatabase(List<Item> itemsToAdd);

    Data getMainListItems(int parentId, int startFromSequenceItemNumber, int itemQuantity);
}
