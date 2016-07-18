package crud12.dao;

import crud12.entities.Category;
import crud12.entities.Product;
import crud12.spring.Context;
@SuppressWarnings("unchecked cast")
public class DaoService {
    public static Dao<Product,Integer> getProductDaoService (){
        return (Dao<Product,Integer>) Context.getBean("dao-product");
    }
    public static Dao<Category,Integer> getCategoryDaoService (){
        return (Dao<Category,Integer>) Context.getBean("dao-category");
    }
}

