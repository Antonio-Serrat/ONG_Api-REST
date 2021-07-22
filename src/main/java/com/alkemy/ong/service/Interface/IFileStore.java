package com.alkemy.ong.service.Interface;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.http.entity.ContentType.*;

public interface IFileStore {

    String save(Object object, MultipartFile file);

    byte[] download(String path, String key);

    default Optional<Map<String, String>> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return Optional.of(metadata);
    }

    default void isAnImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("El archivo debe ser una imagen valida (JPEG, JPG, PNG, GIF).");
        }
    }

    default void fileIsEmpty(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalStateException("No se puede subir un archivo vacio.");
        }
    }

    void deleteFilesFromS3Bucket(Object object);

}
