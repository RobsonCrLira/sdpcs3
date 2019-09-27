import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import org.junit.BeforeClass;
import org.junit.Test;

public class S3CreateBucket {
    String bucketName = "05213025"; // SUBSTITUA PELO SEU RA
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
    public void testCreateBucket() {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(new CreateBucketRequest(bucketName));
        }
        String bucketLocation = s3Client.getBucketLocation(new
                GetBucketLocationRequest(bucketName));
        System.out.println("Localizacao do bucket " + bucketName + ": " +
                bucketLocation);
    }
}