package crud14.dao;

import crud14.entities.Category;
import crud14.entities.Product;
import crud14.spring.Context;
@SuppressWarnings("unchecked cast")
public class DaoService {
    public static Dao<Product,Integer> getProductDaoService (){
        return (Dao<Product,Integer>) Context.getBean("dao-product");
    }
    public static Dao<Category,Integer> getCategoryDaoService (){
        return (Dao<Category,Integer>) Context.getBean("dao-category");
    }
}

