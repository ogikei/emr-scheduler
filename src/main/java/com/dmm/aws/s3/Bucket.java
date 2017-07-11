package com.dmm.aws.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import org.json.JSONObject;

public class Bucket {

  private static final Logger logger = Logger.getLogger(Bucket.class.getName());

  public InputStream getObject(AmazonS3 s3Client, String bucketName, String key) {
    InputStream inputStream = null;
    try {
      logger.info("downloading an object");
      S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, key));

      logger.info("Content-Type: " + s3Object.getObjectMetadata().getContentType());
      logger.info("Content-Length: " + s3Object.getObjectMetadata().getContentLength());
      GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key);

      S3Object objectPortion = s3Client.getObject(rangeObjectRequest);
      inputStream = objectPortion.getObjectContent();
    } catch (AmazonServiceException ase) {
      logger.warning(
          "caught an AmazonServiceException, which means your request made it to Amazon S3,"
              + " but was rejected with an error response for some reason.");

      logger.warning("Error Message:    " + ase.getMessage());
      logger.warning("HTTP Status Code: " + ase.getStatusCode());
      logger.warning("AWS Error Code:   " + ase.getErrorCode());
      logger.warning("Error Type:       " + ase.getErrorType());
      logger.warning("Request ID:       " + ase.getRequestId());
    } catch (AmazonClientException ace) {
      logger.warning("caught an AmazonClientException, which means "
          + "the client encountered an internal error while trying to "
          + "communicate with S3, such as not being able to access the network.");

      logger.warning("Error Message: " + ace.getMessage());
    }
    return inputStream;
  }

  public JSONObject getJSONObjectFromS3(InputStream inputStream) {
    JSONObject jsonObject = null;
    StringBuilder builder = new StringBuilder();
    String line;
    try {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
      jsonObject = new JSONObject(builder.toString());
    } catch (UnsupportedEncodingException uee) {
      logger.warning("caught an UnsupportedEncodingException, please check json format.");

      logger.warning("Error Message: " + uee.getMessage());
    } catch (IOException ioe) {
      logger.warning("caught an IOException.");

      logger.warning("Error Message: " + ioe.getMessage());
    }
    return jsonObject;
  }

}
