package learn.fileupload.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@RestController
public class ImageUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile file) {

        try {
            S3Client s3 = S3Client
                    .builder()
                    .region(Region.US_EAST_2) // correct region
                    .build();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("dev10-image-upload-demo") // correct bucket
                    .key(file.getOriginalFilename())
                    .build();

            PutObjectResponse response = s3.putObject(request, RequestBody.fromBytes(file.getBytes()));

            return new ResponseEntity<>(response.eTag(), HttpStatus.CREATED);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);

    }
}
