import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class S3ListFiles {
    String bucketName = "05213025";
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
    public void testListFiles() {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(new CreateBucketRequest(bucketName));
        }
        System.out.format("Objetos no S3 bucket %s:\n", bucketName);
        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new
                        SystemPropertiesCredentialsProvider())
                .withRegion(clientRegion).build();
        ListObjectsV2Result result = s3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
        }
    }
}