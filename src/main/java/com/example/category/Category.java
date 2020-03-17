package com.example.category;

import com.example.asset.Asset;

import javax.persistence.*;
import java.util.*;

@Entity
public class Category {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "category_name")
    private String name;
    @Column
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Asset> assets = new HashSet<>();

    public Category() {
    }

    public Category(Long id, String name, String description) {
        if(id < 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id < 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (getId() != null ? !getId().equals(category.getId()) : category.getId() != null) return false;
        if (getName() != null ? !getName().equals(category.getName()) : category.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(category.getDescription()) : category.getDescription() != null)
            return false;
        return getAssets() != null ? getAssets().equals(category.getAssets()) : category.getAssets() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAssets() != null ? getAssets().hashCode() : 0);
        return result;
    }
}
