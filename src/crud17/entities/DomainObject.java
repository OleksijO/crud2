package crud17.entities;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class DomainObject implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    protected String name;
    protected String description;
    private Category parent;

    public DomainObject() {
    }

    public DomainObject(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "parentid")
    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }


}
