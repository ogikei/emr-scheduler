package com.dmm.aws;

import com.dmm.aws.emr.Cluster;
import java.io.InputStream;

import org.json.JSONObject;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.dmm.aws.auth.Credential;
import com.dmm.aws.s3.Bucket;


class Main {

  public static void main(String... args) {
    String region = System.getProperty("region");
    String profile = System.getProperty("profile");
    String bucketName = System.getProperty("bucketName");
    String key = System.getProperty("key");

    Credential.createCredential(profile);
    Credential.createConfiguration();

    AmazonS3 s3Client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(Credential.credential)
        .withClientConfiguration(Credential.clientConfig)
        .withRegion(region)
        .build();

    Bucket bucket = new Bucket();
    InputStream inputStream = bucket.getObject(s3Client, bucketName, key);

    JSONObject jsonObject = bucket.getJSONObjectFromS3(inputStream);

    AmazonElasticMapReduce emrClient = AmazonElasticMapReduceClientBuilder
        .standard()
        .withCredentials(Credential.credential)
        .withClientConfiguration(Credential.clientConfig)
        .withRegion(region)
        .build();

    Cluster cluster = new Cluster(jsonObject);
    cluster.createEMRCluster(emrClient, jsonObject);
  }

}
