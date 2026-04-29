package com.cg.genzstore.service;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public String uploadImage(MultipartFile file) throws IOException {
        ObjectId id = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );
        return id.toString();
    }

    public byte[] getImage(String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(id))
        );

        if (file == null) {
            throw new RuntimeException("File not found");
        }

        GridFsResource resource = gridFsTemplate.getResource(file);
        return resource.getInputStream().readAllBytes();
    }
}