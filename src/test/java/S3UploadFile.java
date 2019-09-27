import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
public class S3UploadFile {
    String bucketName = "05213025";
    String s3FileName = "pasta/cluster.txt";
    String localFileName = "C:\\temp\\cluster.txt";
    static AmazonS3 s3Client;
    static Regions clientRegion = Regions.US_EAST_1;
    @BeforeClass
    public static void init() {
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new SystemPropertiesCredentialsProvider())
                .withRegion(clientRegion)
                .build();
    }
    @Test
    public void testCreateFile() {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(new CreateBucketRequest(bucketName));
        }
        PutObjectRequest request = new PutObjectRequest(bucketName,
                s3FileName, new File(localFileName));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        metadata.addUserMetadata("x-amz-meta-title", "someTitle");
        request.setMetadata(metadata);
        s3Client.putObject(request);
        System.out.println("Arquivo enviado com sucesso para " + bucketName +
                ":/" + s3FileName);
    }
}