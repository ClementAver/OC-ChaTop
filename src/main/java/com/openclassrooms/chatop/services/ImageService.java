package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.entities.Image;
import com.openclassrooms.chatop.repositories.ImageRepo;
import com.openclassrooms.chatop.utils.ImageUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepo imageRepository;

    public ImageService(ImageRepo imageRepository) {
        this.imageRepository = imageRepository;
    }

//    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String imageFileName = file.getOriginalFilename();

        imageRepository.save(Image.builder()
                .name(imageFileName)
                .type(file.getContentType())
                .picByte(ImageUtility.compressImage(file.getBytes()))
                .build());

        return "Image uploaded successfully: " + imageFileName;
    }

    public Image getImageDetails(String name) throws FileNotFoundException {
        Optional<Image> imageInDB = imageRepository.findByName(name);
        Image image = imageInDB.orElse(null);

        if (image != null) {
            image = Image.builder()
                    .name(image.getName())
                    .type(image.getType())
                    .picByte(ImageUtility.compressImage(image.getPicByte()))
                    .build();
        } else {
            throw new FileNotFoundException("Image not found");
        }
        return image;
    }

    public byte[] getImage(String name) throws FileNotFoundException {
        Optional<Image> dbImage = imageRepository.findByName(name);
        byte[] imageBytes;

        if (dbImage.isPresent()) {
            imageBytes = ImageUtility.compressImage(dbImage.get().getPicByte());
        } else {
            throw new FileNotFoundException("Image non référencé : " + name);
        }
        return imageBytes;
    }
}
