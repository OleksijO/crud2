package crud2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Category extends  DomainObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Category> subCategories;
    private List<Product> products;

    public Category() {
    }

    public Category(Integer id) {
        super(id);
    }

    @Transient
    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    @Transient
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        return description != null ? description.equals(category.description) : category.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
    @Transient
    public List<Category> getPathList() {
        List<Category> pathList = new ArrayList<>();
        if (this instanceof Category) pathList.add((Category) this);
        Category parent = this.getParent();
        while (parent != null) {
            pathList.add(parent);
            parent = parent.getParent();
        }
        Collections.reverse(pathList);
        return pathList;
    }

}
