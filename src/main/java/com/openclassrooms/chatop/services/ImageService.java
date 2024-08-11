package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.entities.Image;
import com.openclassrooms.chatop.repositories.ImageRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService implements ImageInterface{

    private final ImageRepo imageRepository;

    public ImageService(ImageRepo imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String imageFileName = file.getOriginalFilename();

        Optional<Image> imageOptional = imageRepository.findByName(imageFileName);
        if (imageOptional.isEmpty()) {
            imageRepository.save(Image.builder()
                    .name(imageFileName)
                    .type(file.getContentType())
                    .picByte(file.getBytes())
                    .build());
        }

        return imageFileName;
    }

    @Override
    public byte[] getImage(String name) throws FileNotFoundException {
        Optional<Image> dbImage = imageRepository.findByName(name);
        byte[] imageBytes;

        if (dbImage.isPresent()) {
            imageBytes = dbImage.get().getPicByte();
        } else {
            throw new FileNotFoundException("Image non référencé : " + name);
        }
        return imageBytes;
    }
}