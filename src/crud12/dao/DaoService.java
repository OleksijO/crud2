package crud12.dao;

import crud12.entities.Category;
import crud12.entities.Product;

public class DaoService {
    public static Dao<Product,Integer> getProductDaoService (){
        return new ProductDaoImpl();
    }
    public static Dao<Category,Integer> getCategoryDaoService (){
        return new CategoryDaoImpl();
    }
}

