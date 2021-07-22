package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.Interface.IFileStore;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class FileStoreServiceImpl implements IFileStore {

    private final AmazonS3 s3;
    private final MessageSource messageSource;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.s3.bucket.endpointUrl}")
    private String bucketUrl;

    private static final String SEPARATOR = "-";

    @Autowired
    public FileStoreServiceImpl(AmazonS3 s3, MessageSource messageSource) {
        this.s3 = s3;
        this.messageSource = messageSource;
    }


    @Override
    public String save(Object object, MultipartFile file) {
        fileIsEmpty(file);
        isAnImage(file);

        String objectName = object.getClass().getSimpleName();
        Field privateObjectId = null;
        Long objectId = null;
        try {
            privateObjectId = object.getClass().getDeclaredField("id");
            privateObjectId.setAccessible(true);
            objectId = (Long) privateObjectId.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.getMessage();
        }


        ObjectMetadata metadata = new ObjectMetadata();
        //Metadata extraction from the file - Grabar metadata del archivo
        extractMetadata(file).ifPresent(map -> {
            if(!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try {
            String path = String.format("%s/%s", bucketName, objectName + SEPARATOR + objectId);
            String filename = String.format("%s" + SEPARATOR + "%s", Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s+", SEPARATOR), UUID.randomUUID());
            s3.putObject(path, filename, file.getInputStream(), metadata);
            return bucketUrl + objectName + SEPARATOR + objectId + "/" + filename;
        } catch (AmazonServiceException | IOException ex) {
            throw new IllegalStateException(messageSource.getMessage(
                    "s3bucket.error.upload.file" + " " + ex, null, Locale.getDefault()
            ));
        }
    }

    @Override
    public byte[] download(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void deleteFilesFromS3Bucket(Object object) {
        try {
            String objectName = object.getClass().getSimpleName();
            Field privateObjectId = object.getClass().getDeclaredField("id");
            privateObjectId.setAccessible(true);
            Long objectId = (Long) privateObjectId.get(object);
            for(S3ObjectSummary file : s3.listObjects(bucketName, objectName + SEPARATOR + objectId).getObjectSummaries()) {
                s3.deleteObject(bucketName, file.getKey());
            }
        } catch (SdkClientException | NoSuchFieldException | IllegalAccessException e) {
            e.getMessage();
        }
    }

}
