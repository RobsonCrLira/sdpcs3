import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

public class S3Download {
    String bucketName = "05213025";
    String s3FileName = "pasta/cluster.txt";
    String dwFileName = "C:\\temp\\cluster-dw.txt";
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
    public void testDownloadFile() throws Exception {
        S3Object o = s3Client.getObject(bucketName, s3FileName);
        S3ObjectInputStream s3is = o.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File(dwFileName));
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.close();
    }
}