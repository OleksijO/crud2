package crud19.settings;

/**
 * Defining constants of most used parameters and settings (to do: load from properties)
 */
public interface Constants {
    String TITLE = "Simple CRUD (ver.2).";
    String PROGRAM_NAME = "Based on SpringFramework, Hibernate and PrimeFaces";
    int ITEMS_PER_PAGE = 10;
    int ROOT_CATEGORY_ID = 0;
    String ROOT_CATEGORY_NAME = "Root";

    String SITE_HOME_ADDRESS = "";
    String SITE_CHAPTER_ADDRESS = SITE_HOME_ADDRESS + "/crud2";
    String PAGE_NOT_FOUND = "page-not-found";

    int DATABASE_MANAGEMENT_REFILL_MAX_DEPTH = 3;
    int DATABASE_MANAGEMENT_REFILL_MAX_IN_CATEGORY = 25;


}
