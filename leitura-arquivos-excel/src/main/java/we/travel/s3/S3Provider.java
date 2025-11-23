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
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class S3Provider {
    private final AwsCredentialsProvider credentials;
    private final S3Client s3Client;
    private final String bucketName;

    public S3Provider(String name) {
        this.credentials = DefaultCredentialsProvider.create();
        this.s3Client = getS3Client();
        this.bucketName = name;

    }
    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentials)
                .build();
    }
    public void puxarArquivo() throws IOException {
        ListObjectsRequest listRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
        List<S3Object> objects = s3Client.listObjects(listRequest).contents();
        for (S3Object object : objects) {
            if (object.key().endsWith("/")) {
            // pra pular o diretorio que ta os arquivos
                continue;
            }
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(object.key())
                    .build();
            try (InputStream objectContent = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream())) {
                String fileName = object.key().substring(object.key().lastIndexOf("/") + 1);
                // a linha de cima é para salvar no diretorio normal, pq sem ela ele salva no arquivos-base-dados/nome.xlsx
                Files.copy(objectContent, new File(fileName).toPath());
                System.out.println("Arquivo baixado: " + object.key());
            }
        }

    }

    public List<String> listarArquivos() {
        ListObjectsRequest listRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();

        List<S3Object> objects = s3Client.listObjects(listRequest).contents();
        List<String> arquivos = new ArrayList<>();

        for (S3Object object : objects) {
            arquivos.add(object.key());
        }
        return arquivos;
    }

}
