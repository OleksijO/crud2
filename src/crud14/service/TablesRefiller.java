package crud14.service;

import crud14.entities.DomainObject;
import crud14.service.dao.Dao;
import crud14.entities.Category;
import crud14.entities.Product;
import crud14.spring.Context;

import java.util.*;

import static crud14.Constants.*;

public class TablesRefiller {
    private Map<Integer, Product> products = new HashMap<>();
    private Map<Integer, Category> categories = new TreeMap<>();
    private Dao<DomainObject, Integer> daoService;
    private int maximumDepth = DATABASE_MANAGEMENT_REFILL_MAX_DEPTH;
    private int maximumInCategory = DATABASE_MANAGEMENT_REFILL_MAX_IN_CATEGORY;


    public TablesRefiller() {
    }

    public TablesRefiller(Dao<DomainObject, Integer> daoService) {
        this.daoService = daoService;
    }

    public void refillDatabase() {
        fillFakeTables(maximumDepth, maximumInCategory);
        daoService.recreateTables();
        daoService.createList(categories.values());
        daoService.createList(products.values());
    }

    public void refillDatabase(int maxDepth, int maxInCat) {
        maximumDepth = maxDepth;
        maximumInCategory = maxInCat;
        refillDatabase();
    }

    private void fillFakeTables(int maxDepth, int maxInList) {
        categories.clear();
        Category cat = Context.getBean("entity-category");
        cat.setId(ROOT_CATEGORY_ID);
        cat.setParent(null);
        cat.setName(ROOT_CATEGORY_NAME);
        cat.setDescription("");
        products.clear();
        fillCategoryList(cat, maxDepth, maxInList);
    }

    private void fillCategoryList(Category category, int maxDepth, int maxInList) {
        if ((Math.random() > 0.7) && (category.getId() > 0)) fillCategoryListByProducts(category, maxInList);
        else {
            fillCategoryListByCategories(category, maxDepth, maxInList);
        }
    }

    private void fillCategoryListByCategories(Category parentCategory, int maxDepth, int maxInList) {
        int numberOfCategories = (int) (Math.random() * (maxInList + 1));
        for (int i = 0; i < numberOfCategories; i++) {
            Category cat = Context.getBean("entity-category");
            cat.setId(categories.size() + 1);
            cat.setParent(parentCategory);
            cat.setName("Category of products  id=" + cat.getId());
            cat.setDescription("Описание категории с именем: " + cat.getName());
            categories.put(cat.getId(), cat);
            if (getPathListDepth(cat.getId()) <= maxDepth - 1)
                fillCategoryList(cat, maxDepth, maxInList);
            else fillCategoryListByProducts(cat, maxInList);

        }
    }

    private int getPathListDepth(int itemId) {
        int depth = 0;
        while (itemId != 0) {
            itemId = categories.get(itemId).getParent().getId();
            depth++;
        }

        return depth;
    }

    private void fillCategoryListByProducts(Category parentCategory, int maxInList) {
        int numberOfProducts = (int) (Math.random() * (maxInList + 1));
        for (int i = 0; i < numberOfProducts; i++) {
            Product product = Context.getBean("entity-product");
            product.setId(products.size() + 1);
            product.setParent(parentCategory);
            product.setName("Product name - " + generateRandomString());
            product.setDescription("Product decscription: product in category id=" + parentCategory.getId() + ". Further product description....bla-bla-bla.....");
            product.setPrice((long) (Math.random() * 100000) + 1);
            product.setQuantity((int) (Math.random() * 100));
            products.put(product.getId(), product);
        }
    }

    private String generateRandomString() {
        char[] name = new char[3 + (int) (Math.random() * 7)];
        for (int j = 0; j < name.length; j++) {
            name[j] = (char) ('a' + (int) (Math.random() * 24));
        }
        return new String(name);
    }

    public void setDaoService(Dao<DomainObject, Integer> daoService) {
        this.daoService = daoService;
    }
}
