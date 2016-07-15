package todo;

import java.io.InputStream;
import java.util.Properties;

public class Settings {

    public static final String TITLE;
    public static final int ITEMS_PER_PAGE;
    public static final int FIRST_PAGE_NUMBER;
    public static final int ROOT_CATEGORY_ID;
    public static final String ROOT_CATEGORY_NAME;
    public static final String SITE_HOME_ADDRESS;
    public static final String SITE_CHAPTER_ADDRESS;
    public static final String PAGE_NOT_FOUND;
    public static final String MODE_START;
    public static final String MODE_DATABASE_MANAGEMENT;
    public static final String MODE_DATABASE_MANAGEMENT_RENEW;
    public static final String MODE_NAVI;
    public static final String EDIT_MODE_ITEM_ADD_NEW;
    public static final String EDIT_MODE_ITEM_DELETED;
    public static final String EDIT_MODE_ITEM_ADDED;
    public static final String EDIT_MODE_ITEM_UPDATED;
    public static final String EDIT_MODE_ITEM_EDIT;
    public static final String QUERY_PARAMETER_MODE;
    public static final String QUERY_PARAMETER_PARENT_ID;
    public static final String QUERY_PARAMETER_PAGE;
    public static final String QUERY_PARAMETER_EDIT_MODE;
    public static final String QUERY_PARAMETER_EDIT_ITEM_TYPE;
    public static final String QUERY_PARAMETER_EDIT_ITEM_ID;
    public static final String QUERY_PARAMETER_ITEMS_PER_PAGE;
    public static final String QUERY_PARAMETER_EDIT_FORM_NAME;
    public static final String QUERY_PARAMETER_EDIT_FORM_DESCRIPTION;
    public static final String QUERY_PARAMETER_EDIT_FORM_PRICE;
    public static final String QUERY_PARAMETER_EDIT_FORM_QUANTITY;
    public static final int DATABASE_MANAGEMENT_REFILL_MAX_DEPTH;
    public static final int DATABASE_MANAGEMENT_REFILL_MAX_IN_CATEGORY;

    static {
        Properties props = new Properties();
        try {
            InputStream input = Settings.class.getClassLoader().getResourceAsStream("WEB-INF/properties/settings.properties");
            props.load(input);

        } catch (Exception e) {
            throw new Error("FATAL ERROR! Can't load setting!");
        }
        TITLE = props.getProperty("TITLE");
        ITEMS_PER_PAGE = Integer.parseInt(props.getProperty("ITEMS_PER_PAGE"));
        FIRST_PAGE_NUMBER = Integer.parseInt(props.getProperty("FIRST_PAGE_NUMBER"));
        ROOT_CATEGORY_ID = Integer.parseInt(props.getProperty("ROOT_CATEGORY_ID"));
        ROOT_CATEGORY_NAME = props.getProperty("ROOT_CATEGORY_NAME");
        SITE_HOME_ADDRESS = props.getProperty("SITE_HOME_ADDRESS");
        SITE_CHAPTER_ADDRESS = props.getProperty("SITE_CHAPTER_ADDRESS");
        PAGE_NOT_FOUND = props.getProperty("PAGE_NOT_FOUND");
        MODE_START = props.getProperty("MODE_START");
        MODE_DATABASE_MANAGEMENT = props.getProperty("MODE_DATABASE_MANAGEMENT");
        MODE_DATABASE_MANAGEMENT_RENEW = props.getProperty("MODE_DATABASE_MANAGEMENT_RENEW");
        MODE_NAVI = props.getProperty("MODE_NAVI");
        EDIT_MODE_ITEM_ADD_NEW = props.getProperty("EDIT_MODE_ITEM_ADD_NEW");
        EDIT_MODE_ITEM_DELETED = props.getProperty("EDIT_MODE_ITEM_DELETED");
        EDIT_MODE_ITEM_ADDED = props.getProperty("EDIT_MODE_ITEM_ADDED");
        EDIT_MODE_ITEM_UPDATED = props.getProperty("EDIT_MODE_ITEM_UPDATED");
        EDIT_MODE_ITEM_EDIT = props.getProperty("EDIT_MODE_ITEM_EDIT");
        QUERY_PARAMETER_MODE = props.getProperty("QUERY_PARAMETER_MODE");
        QUERY_PARAMETER_PARENT_ID = props.getProperty("QUERY_PARAMETER_PARENT_ID");
        QUERY_PARAMETER_PAGE = props.getProperty("QUERY_PARAMETER_PAGE");
        QUERY_PARAMETER_EDIT_MODE = props.getProperty("QUERY_PARAMETER_EDIT_MODE");
        QUERY_PARAMETER_EDIT_ITEM_TYPE = props.getProperty("QUERY_PARAMETER_EDIT_ITEM_TYPE");
        QUERY_PARAMETER_EDIT_ITEM_ID = props.getProperty("QUERY_PARAMETER_EDIT_ITEM_ID");
        QUERY_PARAMETER_ITEMS_PER_PAGE = props.getProperty("QUERY_PARAMETER_ITEMS_PER_PAGE");
        QUERY_PARAMETER_EDIT_FORM_NAME = props.getProperty("QUERY_PARAMETER_EDIT_FORM_NAME");
        QUERY_PARAMETER_EDIT_FORM_DESCRIPTION = props.getProperty("QUERY_PARAMETER_EDIT_FORM_DESCRIPTION");
        QUERY_PARAMETER_EDIT_FORM_PRICE = props.getProperty("QUERY_PARAMETER_EDIT_FORM_PRICE");
        QUERY_PARAMETER_EDIT_FORM_QUANTITY = props.getProperty("QUERY_PARAMETER_EDIT_FORM_QUANTITY");
        DATABASE_MANAGEMENT_REFILL_MAX_DEPTH = Integer.parseInt(props.getProperty("DATABASE_MANAGEMENT_REFILL_MAX_DEPTH"));
        DATABASE_MANAGEMENT_REFILL_MAX_IN_CATEGORY = Integer.parseInt(props.getProperty("DATABASE_MANAGEMENT_REFILL_MAX_IN_CATEGORY"));
    }

   private Settings(){}
}
