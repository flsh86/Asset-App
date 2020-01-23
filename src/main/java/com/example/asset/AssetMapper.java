package com.example.asset;

import com.example.category.Category;
import com.example.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetMapper {
    private CategoryRepository categoryRepository;

    @Autowired
    public AssetMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public AssetDTO toDTO(Asset asset) {
        AssetDTO dto = new AssetDTO();
        dto.setId(asset.getId());
        dto.setName(asset.getName());
        dto.setDescription(asset.getDescription());
        dto.setSerialNumber(asset.getSerialNumber());

        if(asset.getCategory() != null) {
            dto.setCategory(asset.getCategory().getName());
        }
        return dto;
    }

    public Asset toEntity(AssetDTO dto) {
        Asset entity = new Asset();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setSerialNumber(dto.getSerialNumber());

        Optional<Category> category = categoryRepository.findByNameIgnoreCase(dto.getCategory());
        category.ifPresent(entity::setCategory);

        return entity;
    }
}
