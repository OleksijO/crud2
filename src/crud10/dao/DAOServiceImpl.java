package crud10.dao;

import crud10.Constants;
import crud10.Logger.Logger;
import crud10.dto.Data;
import crud10.dto.DataDTO;
import crud10.dto.Item;
import crud10.dto.ItemDTO;
import crud10.model.ItemType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database direct methods
 */

public class DAOServiceImpl implements DAOService {
    private static final DAOService instance = new DAOServiceImpl();

    private DatabasePropertiesLoader databaseParameters;

    private DAOServiceImpl() {
        databaseParameters = new DatabasePropertiesLoader("resources/database.properties");
    }

    public static DAOService getInstance() {
        return instance;
    }

    @Override
    public Data deleteItem(Item item) {
        Data result = new DataDTO();
        String sqlQuery;
        if (item.getItemType() == ItemType.CATEGORY) {
            sqlQuery = " DELETE FROM category WHERE id = ? ;";
        } else if (item.getItemType() == ItemType.PRODUCT) {
            sqlQuery = " DELETE FROM product WHERE id = ? ; ";

        } else {
            result.setStatus("DATABASE: Wrong item type.");
            Logger.log("DATABASE: Adding new item. Wrong item type.");
            return result;
        }
        Logger.log("SQL: " + sqlQuery);
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logExceptionAndSetResultStatusMessage("SQL:", result, e);
            return result;
        }
        Logger.log("SQL: OK");
        result.setEditedItemId(item.getId());
        result.setStatusOK();
        return result;
    }

    @Override
    public Data updateItem(Item item) {
        Data result = new DataDTO();
        String sqlQuery;
        if (item.getItemType() == ItemType.CATEGORY) {
            sqlQuery = " SELECT * FROM category WHERE id = ? ;";
        } else if (item.getItemType() == ItemType.PRODUCT) {
            sqlQuery = " SELECT * FROM product WHERE id = ? ; ";

        } else {
            result.setStatus("DATABASE: Wrong item type.");
            Logger.log("DATABASE: Adding new item. Wrong item type.");
            return result;
        }
        Logger.log("SQL: " + sqlQuery);
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                statement.setInt(1, item.getId());
                statement.execute();
                try(ResultSet rs=statement.executeQuery()){
                    if (rs.next()){
                        rs.updateString(3,item.getName());
                        rs.updateString(4,item.getDescription());
                        if (item.getItemType()==ItemType.PRODUCT) {
                            rs.updateLong(5, item.getPrice());
                            rs.updateInt(6, item.getQuantity());
                        }
                        rs.updateRow();
                    }else {
                        logExceptionAndSetResultStatusMessage("SQL:", result, new RuntimeException("Queried id not found."));
                        return result;
                    }
                }
            }
        } catch (SQLException e) {
            logExceptionAndSetResultStatusMessage("SQL:", result, e);
            return result;
        }
        Logger.log("SQL: OK");
        result.setEditedItemId(item.getId());
        result.setStatusOK();
        return result;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseParameters.url, databaseParameters.user, databaseParameters.password);
    }

    @Override
    public Data addNewItem(Item item) {
        Data result = new DataDTO();
        String sqlQuery;
        if (item.getItemType() == ItemType.CATEGORY) {
            sqlQuery = "INSERT INTO category (parentid, name,description) VALUES (?, ?, ?) RETURNING id;";
        } else if (item.getItemType() == ItemType.PRODUCT) {
            sqlQuery = " INSERT INTO product (parentid, name,description,price,quantity) VALUES(?,?,?,?,?) RETURNING id; ";

        } else {
            result.setStatus("DATABASE: Wrong item type.");
            Logger.log("DATABASE: Adding new item. Wrong item type.");
            return result;
        }
        Logger.log("SQL: " + sqlQuery);
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

                statement.setInt(1, item.getParentId());
                statement.setString(2, item.getName());
                statement.setString(3, item.getDescription());
                if (item.getItemType() == ItemType.PRODUCT) {
                    statement.setLong(4, item.getPrice());
                    statement.setInt(5, item.getQuantity());
                }
                int count[] = executeAndGetCountsFromMultipleResultSets(statement);
                if (count.length == 1) {
                    result.setEditedItemId(count[0]);
                } else {
                    result.setStatus("Wrong item type.");
                    Logger.log("SQL: " + "Adding new item. Wrong item type.");
                }
            }
        } catch (SQLException e) {
            logExceptionAndSetResultStatusMessage("SQL:", result, e);
            return result;
        }
        Logger.log("SQL: OK");
        result.setStatusOK();
        return result;
    }

    @Override
    public Data getPath(int id) {
        Data result = new DataDTO();
        List<Item> resultList = new ArrayList<>();
        String sqlQuery = "" +
                " WITH RECURSIVE tree (_id, _parentid, _name, _description) " +
                " AS (" +
                " SELECT id, parentid, name, description " +
                " FROM category " +
                " WHERE id = ? " +
                " UNION ALL " +
                " SELECT id, parentid, name, description " +
                " FROM category cat " +
                " INNER JOIN tree t " +
                " ON t._parentid = cat.id) " +
                " SELECT * FROM tree " +
                " ORDER BY _id ; ";
        Logger.log("SQL: " + sqlQuery);
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, id);
                statement.execute();
                try (ResultSet rs = statement.getResultSet()) {
                    while (rs.next()) {
                        resultList.add(getCategoryItemFromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            logExceptionAndSetResultStatusMessage("SQL:", result, e);
            return result;
        }
        result.setPath(resultList);
        result.setStatusOK();
        Logger.log("SQL: OK");
        return result;
    }

    @Override
    public Data getTotalItemCountsOfTables() {
        Data result = new DataDTO();
        int count[];
        String sqlQuery = "" +
                " SELECT COUNT (id) FROM product; " +
                " SELECT COUNT (id) FROM category;";
        Logger.log("SQL: " + sqlQuery);
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.execute();
                count = executeAndGetCountsFromMultipleResultSets(statement);
            }
        } catch (SQLException e) {
            logExceptionAndSetResultStatusMessage("SQL:", result, e);
            return result;
        }
        if (count.length == 2) {
            result.setTotalNumberOfProducts(count[0]);
            result.setTotalNumberOfCategories(count[1]);
            result.setStatusOK();
            Logger.log("SQL: OK");
        } else {
            logExceptionAndSetResultStatusMessage("DATABASE: ", result, new RuntimeException("Wrong number of result sets while getting total item counts."));
        }
        return result;
    }

    @Override
    public Data refillDatabase(List<Item> itemsToAdd) {
        Data result = new DataDTO();
        String create_queries[] = {
                "DROP TABLE product",
                "DROP TABLE category;",
                "CREATE TABLE category(" +
                        "id SERIAL NOT NULL, " +
                        "parentid INT, " +
                        "name varchar(255) NOT NULL, " +
                        "description varchar(255), " +
                        "PRIMARY KEY (id)," +
                        "FOREIGN KEY (parentid) REFERENCES category(id)" +
                        "ON DELETE CASCADE" +
                        ");",
                "CREATE TABLE product(" +
                        "id SERIAL NOT NULL PRIMARY KEY, " +
                        "parentid INT NOT NULL REFERENCES category(id) " +
                        "ON DELETE CASCADE, " +
                        "name varchar(255) NOT NULL, " +
                        "description varchar(255), " +
                        "price BIGINT, " +
                        "quantity INT" +
                        ");",
        };
        try (Connection connection = getConnection()) {
            for (String query : create_queries) {
                try (Statement statement = connection.createStatement()) {
                    Logger.log("SQL: " + query);
                    try {
                        statement.execute(query);
                        Logger.log("SQL: OK");
                    } catch (SQLException e) {
                        Logger.log("SQL: " + e.getMessage());
                        ;
                    }
                }
            }
        } catch (SQLException e) {
            logExceptionAndSetResultStatusMessage("SQL Recreating tables error : ", result, e);
            return result;
        }
        ////////////////////////////////////////////////////
        int countProduct = 0;
        int countCategory = 0;
        try (Connection connection = getConnection()) {
            try (Statement createRoot = connection.createStatement();
                 PreparedStatement statementProduct = connection.prepareStatement(
                         "INSERT INTO product (parentid, name,description,price,quantity) VALUES(?,?,?,?,?);");
                 PreparedStatement statementCategory = connection.prepareStatement(
                         "INSERT INTO category (parentid, name,description) VALUES (?,?,?);")
            ) {
                createRoot.execute(
                        "INSERT INTO category (id, parentid, name,description) VALUES (0, NULL ,'" + Constants.ROOT_CATEGORY_NAME + "','');");
                for (Item item : itemsToAdd) {
                    if (item.getItemType() == ItemType.PRODUCT) {
                        statementProduct.setInt(1, item.getParentId());
                        statementProduct.setString(2, item.getName());
                        statementProduct.setString(3, item.getDescription());
                        statementProduct.setLong(4, item.getPrice());
                        statementProduct.setInt(5, item.getQuantity());
                        statementProduct.execute();
                        countProduct++;
                    } else if (item.getItemType() == ItemType.CATEGORY) {
                        statementCategory.setInt(1, item.getParentId());
                        statementCategory.setString(2, item.getName());
                        statementCategory.setString(3, item.getDescription());
                        statementCategory.execute();
                        countCategory++;
                    }
                }
            }
            Logger.log("SQL: OK. Added " + countCategory + " categories and " + countProduct + " products.");
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log("SQL : Error while refilling tables :" + e.getMessage());
            result.setStatus("SQL: Error while refilling tables : " + e.getMessage());
            return result;
        }
        result.setStatusOK();
        result.setTotalNumberOfProducts(countProduct);
        return result;


    }

    private void logExceptionAndSetResultStatusMessage(String message, Data result, Exception e) {
        e.printStackTrace();
        Logger.log(message + "\t" + e.getMessage());
        result.setStatus(message + "\t" + e.getMessage());
    }

    @Override
    public Data getMainListItems(int parentId, int startFromSequenceItemNumber, int itemQuantity) {
        Data result = new DataDTO();
        List<Item> resultList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sqlQuery1 = "" +
                " SELECT COUNT(id) FROM category " +
                " WHERE parentid=? ;" +
                " SELECT COUNT(id) FROM product " +
                " WHERE parentid=? ;";
        String sqlQuery2 = "" +
                " SELECT * FROM category " +
                " WHERE parentid=? " +
                " ORDER BY id DESC " +
                " OFFSET ? " +
                " LIMIT ?; " +
                " SELECT * FROM product " +
                " WHERE parentid=? " +
                " ORDER BY id DESC " +
                " OFFSET ? " +
                " LIMIT ?; ";
        Logger.log("SQL: " + sqlQuery1);
        try {
            connection = getConnection();
            try {
                statement = connection.prepareStatement(sqlQuery1);
                statement.setInt(1, parentId);
                statement.setInt(2, parentId);

                int count[] = executeAndGetCountsFromMultipleResultSets(statement);
                Logger.log("SQL: OK");
                statement.close();
                int countCategories = count[0];
                int countProducts = count[1];
                result.setNumberOfItemsInThisCategory(countCategories + countProducts);
                int offsetCategories;
                int limitCategories;
                int offsetProducts;
                int limitProducts;
                limitCategories = countCategories - startFromSequenceItemNumber + 1;
                if (limitCategories > itemQuantity) limitCategories = itemQuantity;
                if (limitCategories < 0) limitCategories = 0;
                offsetCategories = startFromSequenceItemNumber - 1;
                limitProducts = itemQuantity - limitCategories;
                offsetProducts = startFromSequenceItemNumber - countCategories - 1;
                if (offsetProducts < 0) offsetProducts = 0;
                statement = connection.prepareStatement(sqlQuery2);
                statement.setInt(1, parentId);
                statement.setInt(2, offsetCategories);
                statement.setInt(3, limitCategories);
                statement.setInt(4, parentId);
                statement.setInt(5, offsetProducts);
                statement.setInt(6, limitProducts);
                Logger.log("SQL: " + sqlQuery2);
                statement.execute();
                try {
                    rs = statement.getResultSet();
                    while (rs.next()) {
                        resultList.add(getCategoryItemFromResultSet(rs));
                    }
                } finally {
                    if (rs != null) rs.close();
                }
                try {
                    statement.getMoreResults();
                    rs = statement.getResultSet();
                    while (rs.next()) {
                        resultList.add(getPoductItemFromResultSet(rs));
                    }
                } finally {
                    if (rs != null) rs.close();
                }
                Logger.log("SQL: OK");
            } finally {
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            logExceptionAndSetResultStatusMessage("SQL:", result, e);
            return result;
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                logExceptionAndSetResultStatusMessage("SQL:", result, e);
                return result;
            }
        }
        result.setItems(resultList);
        result.setStatusOK();
        return result;
    }

    private Item getPoductItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = getCategoryItemFromResultSet(rs);
        item.setPrice(rs.getInt(5));
        item.setQuantity(rs.getInt(6));
        item.setItemType(ItemType.PRODUCT);
        return item;
    }

    private Item getCategoryItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = new ItemDTO();
        item.setId(rs.getInt(1));
        item.setParentId(rs.getInt(2));
        item.setName(rs.getString(3));
        item.setDescription(rs.getString(4));
        item.setItemType(ItemType.CATEGORY);
        return item;
    }

    private int[] executeAndGetCountsFromMultipleResultSets(PreparedStatement statement) throws SQLException {
        Boolean hasResult = statement.execute();
        List<Integer> list = new ArrayList<>();
        while (hasResult) {
            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
            hasResult = statement.getMoreResults();
        }
        int count[] = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            count[i] = list.get(i);
        }
        return count;
    }

}
