package we.travel.s3;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class S3Provider {
    private final AwsCredentialsProvider credentials;
    private final S3Client s3Client;

    public S3Provider() {
        this.credentials = DefaultCredentialsProvider.create();
        this.s3Client = getS3Client();

    }
    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentials)
                .build();
    }
    public void puxarArquivo() throws IOException {
        ListObjectsRequest listRequest = ListObjectsRequest.builder()
                .bucket("nome-do-bucket") // Você precisa informar o bucket aqui
                .build();
        List<S3Object> objects = s3Client.listObjects(listRequest).contents();
        for (S3Object object : objects) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("nome-do-bucket")
                    .key(object.key())
                    .build();

            InputStream objectContent = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
            Files.copy(objectContent, new File(object.key()).toPath());
        }
    }

}
