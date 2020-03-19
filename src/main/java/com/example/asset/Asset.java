package com.example.asset;

import com.example.assignment.Assignment;
import com.example.category.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "assets")
public class Asset {
    @Id
    private Long id;

    @Column(nullable = false, name = "asset_name")
    private String name;

    @Column(length = 2048)
    private String description;

    @Column(nullable = false, unique = true, name = "serial_number")
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "asset")
    private List<Assignment> assignments = new ArrayList<>();

    public Asset() {
    }

    public Asset(Long id, String name, String description, String serialNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asset)) return false;

        Asset asset = (Asset) o;

        if (getId() != null ? !getId().equals(asset.getId()) : asset.getId() != null) return false;
        if (getName() != null ? !getName().equals(asset.getName()) : asset.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(asset.getDescription()) : asset.getDescription() != null)
            return false;
        if (getSerialNumber() != null ? !getSerialNumber().equals(asset.getSerialNumber()) : asset.getSerialNumber() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(asset.getCategory()) : asset.getCategory() != null)
            return false;
        return getAssignments() != null ? getAssignments().equals(asset.getAssignments()) : asset.getAssignments() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getSerialNumber() != null ? getSerialNumber().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getAssignments() != null ? getAssignments().hashCode() : 0);
        return result;
    }
}
