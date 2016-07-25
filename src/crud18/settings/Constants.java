package crud18.settings;

/**
 * Defining constants of most used parameters and settings (to do: load from properties)
 */
public interface Constants {
    String TITLE = "Simple CRUD (ver.2).";
    String PROGRAM_NAME = "Based on SpringFramework, Hibernate and PrimeFaces";
    int ITEMS_PER_PAGE = 10;
    int FIRST_PAGE_NUMBER = 1;
    int ROOT_CATEGORY_ID = 0;
    String ROOT_CATEGORY_NAME = "Root";

    String SITE_HOME_ADDRESS = "";
    String SITE_CHAPTER_ADDRESS = SITE_HOME_ADDRESS + "/crud2";
    String PAGE_NOT_FOUND = "page-not-found";

    String MODE_START = "start";
    String MODE_DATABASE_MANAGEMENT = "database-management";
    String MODE_DATABASE_MANAGEMENT_RENEW = "renew";
    String MODE_NAVI = "navi";

    String EDIT_MODE_ITEM_ADD_NEW = "add-new";
    String EDIT_MODE_ITEM_DELETED = "deleted";
    String EDIT_MODE_ITEM_ADDED = "added";
    String EDIT_MODE_ITEM_UPDATED = "updated";
    String EDIT_MODE_ITEM_EDIT = "edit";

    String QUERY_PARAMETER_MODE = "mode";
    String QUERY_PARAMETER_PARENT_ID = "parent-id";
    String QUERY_PARAMETER_PAGE = "page";
    String QUERY_PARAMETER_EDIT_MODE = "edit-mode";
    String QUERY_PARAMETER_EDIT_ITEM_TYPE = "edit-item-type";
    String QUERY_PARAMETER_EDIT_ITEM_ID = "edit-item-id";
    String QUERY_PARAMETER_ITEMS_PER_PAGE = "items-per-page";
    String QUERY_PARAMETER_EDIT_FORM_NAME = "name";
    String QUERY_PARAMETER_EDIT_FORM_DESCRIPTION = "description";
    String QUERY_PARAMETER_EDIT_FORM_PRICE = "price";
    String QUERY_PARAMETER_EDIT_FORM_QUANTITY = "quantity";

    int DATABASE_MANAGEMENT_REFILL_MAX_DEPTH = 3;
    int DATABASE_MANAGEMENT_REFILL_MAX_IN_CATEGORY = 25;


}
