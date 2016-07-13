package crud11.dao;

import crud11.entities.Category;
import crud11.entities.Product;

public class DaoService {
    public static Dao<Product,Integer> getProductDaoService (){
        return new ProductDaoImpl();
    }
    public static Dao<Category,Integer> getCategoryDaoService (){
        return new CategoryDaoImpl();
    }
}

