package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.entities.Image;
import com.openclassrooms.chatop.repositories.ImageRepo;
import com.openclassrooms.chatop.services.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uploadImage(file);
    }

    @GetMapping(path = {"/get/image/info/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws FileNotFoundException {
        return imageService.getImageDetails(name);
    }

    @GetMapping(path = {"/get/image/{name}"})
    public byte[] getImage(@PathVariable("name") String name) throws FileNotFoundException {
        return imageService.getImage(name);
    }
}