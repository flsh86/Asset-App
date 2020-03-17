package com.example.img;

public class ImageMapper {

    public static Image toEntity(ImageDTO dto) {
        return new Image(
                dto.getId(),
                dto.getPath()
        );
    }

    public static ImageDTO toDTO(Image entity) {
        return new ImageDTO(
                entity.getId(),
                entity.getPath()
        );
    }

}
