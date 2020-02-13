package com.example.asset;

import com.example.category.Category;
import com.example.repositories.CategoryRepository;
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
        AssetDTO dto = new AssetDTO(
                asset.getId(),
                asset.getName(),
                asset.getDescription(),
                asset.getSerialNumber()
        );

        if(asset.getCategory() != null) {
            dto.setCategory(asset.getCategory().getName());
        }

        return dto;
    }

    public Asset toEntity(AssetDTO dto) {
        Asset entity = new Asset(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getSerialNumber()
        );

        Optional<Category> category = categoryRepository.findByNameIgnoreCase(dto.getCategory());
        category.ifPresent(entity::setCategory);

        return entity;
    }
}
