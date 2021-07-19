package io.mavq.reimbursementbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import io.mavq.reimbursementbackend.dto.FirebaseCredential;

import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FirebaseStorageService{
    private final Environment environment;

    private StorageOptions storageOptions;
    private String bucketName;
    private String projectId;

    public FirebaseStorageService(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    private void initializeFirebase() throws Exception {
        bucketName = environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
        projectId = environment.getRequiredProperty("FIREBASE_PROJECT_ID");

        InputStream firebaseCredential = createFirebaseCredential();
        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(firebaseCredential)).build();

    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        System.out.println("bucket name====" + bucketName);
        String fileName = String.valueOf(new Date().getTime());
        String objectName = fileName + ".pdf";
//        File file = convertMultiPartToFile(multipartFile,objectName);
//        Path filePath = file.toPath();
        Storage storage = storageOptions.getService();
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", objectName);
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType(multipartFile.getContentType()).build();
        storage.create(blobInfo, multipartFile.getBytes());

        return fileName;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + ".pdf";
    }

    private InputStream createFirebaseCredential() throws Exception {
        FirebaseCredential firebaseCredential = new FirebaseCredential();
        //private key
        String privateKey = environment.getRequiredProperty("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

        firebaseCredential.setType(environment.getRequiredProperty("FIREBASE_TYPE"));
        firebaseCredential.setProject_id(projectId);
        firebaseCredential.setPrivate_key_id("FIREBASE_PRIVATE_KEY_ID");
        firebaseCredential.setPrivate_key(privateKey);
        firebaseCredential.setClient_email(environment.getRequiredProperty("FIREBASE_CLIENT_EMAIL"));
        firebaseCredential.setClient_id(environment.getRequiredProperty("FIREBASE_CLIENT_ID"));
        firebaseCredential.setAuth_uri(environment.getRequiredProperty("FIREBASE_AUTH_URI"));
        firebaseCredential.setToken_uri(environment.getRequiredProperty("FIREBASE_TOKEN_URI"));
        firebaseCredential.setAuth_provider_x509_cert_url(environment.getRequiredProperty("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
        firebaseCredential.setClient_x509_cert_url(environment.getRequiredProperty("FIREBASE_CLIENT_X509_CERT_URL"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(firebaseCredential);

        //convert jsonString string to InputStream using Apache Commons
        return IOUtils.toInputStream(jsonString);
    }
}
