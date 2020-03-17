package com.example.img;

import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ImageService {
    private ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public ImageDTO findById(Long id) {
        Optional<Image> image = imageRepository.findById(id);

        if(image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

            return image.map(ImageMapper::toDTO).orElse(null);
    }

}
