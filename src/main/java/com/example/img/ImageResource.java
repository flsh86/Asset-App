package com.example.img;

import com.example.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/image")
public class ImageResource {
    private ImageService imageService;
    private UserService userService;

    @Autowired
    public ImageResource(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    private ResponseEntity<InputStreamResource> getImage(@PathVariable  Long id) throws FileNotFoundException {
        ImageDTO imageDTO = userService.getImage(id);
        InputStream inputStream = new FileInputStream(imageDTO.getPath());
        return ResponseEntity.ok().body(new InputStreamResource(inputStream));
    }
}
