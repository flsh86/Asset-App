package com.example.asset;


public class AssetDTO {
    private Long id;
    private String name;
    private String description;
    private String serialNumber;
    private String category;

    public AssetDTO() {
    }

    public AssetDTO(Long id, String name, String description, String serialNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
    }

    public AssetDTO(Long id, String name, String description, String serialNumber, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssetDTO)) return false;

        AssetDTO assetDTO = (AssetDTO) o;

        if (getId() != null ? !getId().equals(assetDTO.getId()) : assetDTO.getId() != null) return false;
        if (getName() != null ? !getName().equals(assetDTO.getName()) : assetDTO.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(assetDTO.getDescription()) : assetDTO.getDescription() != null)
            return false;
        if (getSerialNumber() != null ? !getSerialNumber().equals(assetDTO.getSerialNumber()) : assetDTO.getSerialNumber() != null)
            return false;
        return getCategory() != null ? getCategory().equals(assetDTO.getCategory()) : assetDTO.getCategory() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getSerialNumber() != null ? getSerialNumber().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        return result;
    }
}
