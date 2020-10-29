package com.success.s3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.util.IOUtils;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

public class SimpleS3 {

  //  public static void main(String[] args) throws InterruptedException, ExecutionException {
  //
  //    //make sure credentials and config files are there under users/{username}/.aws directory..
  // with access key and security key and profile information
  ////    S3AsyncClient s3Client =
  // S3AsyncClient.builder().region(Region.US_EAST_1).credentialsProvider(ProfileCredentialsProvider.builder().profileName("selvan").build()).build();
  //    S3AsyncClient s3Client = S3AsyncClient.builder().region(Region.US_EAST_1).build(); // works
  // ..uses the default profile
  //    System.out.println(s3Client.toString());
  //    System.out.println(s3Client.listBuckets().get().buckets().size());
  //    s3Client.listBuckets().get().buckets().forEach(e -> System.out.println(e.name()));
  //
  //    StsClient sClient =
  // StsClient.builder().region(Region.US_EAST_1).credentialsProvider(ProfileCredentialsProvider.builder().profileName("selvan").build()).build();
  //
  //    StsAsyncClient stsClient =
  // StsAsyncClient.builder().region(Region.US_EAST_1).credentialsProvider(ProfileCredentialsProvider.builder().profileName("selvan").build()).build();
  //    AssumeRoleRequest assumeRoleRequest =
  // AssumeRoleRequest.builder().roleArn("arn:aws:iam::326286503284:role/some").roleSessionName("some").build();
  ////
  // System.out.println(stsClient.assumeRole(assumeRoleRequest).get().credentials().accessKeyId());
  //    System.out.println(sClient.assumeRole(assumeRoleRequest).credentials());
  //
  //
  ////    AWSSecurityTokenService tokenService =
  // AWSSecurityTokenServiceClientBuilder.standard().withCredentials().withRegion(Region.US_EAST_1.toString()).build();
  //
  //    //the IAM user principal should be in the trust relationship, also the role should have a
  // assumeRole permission policy
  //    /*AssumeRoleRequest request = new
  // AssumeRoleRequest().withRoleArn("arn:aws:iam::326286503284:role/some").withRoleSessionName("tamil")
  //        .withDurationSeconds(3600);
  //    AssumeRoleResult result = tokenService.assumeRole(request);
  //    System.out.println(result.getCredentials().getAccessKeyId());*/
  //  }

  private static void compareBuckets() {
    try {
      final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1).build();
      ListObjectsV2Result r1 = s3.listObjectsV2("recipe-original-pics-dev");
      r1 = s3.listObjectsV2("rpicsd");
      Set<String> rpicsd =
          r1.getObjectSummaries()
              .parallelStream()
              .filter(os -> os.getKey().contains("optimized") && os.getKey().split("/").length == 2)
              .map(S3ObjectSummary::getKey)
              .map(key -> key.split("/")[1])
              .collect(Collectors.toSet());

      r1 = s3.listObjectsV2("recipe-original-pics-dev");
      r1.getObjectSummaries()
          .parallelStream()
          .filter(os -> os.getKey().contains("original") && os.getKey().split("/").length == 2)
          .map(S3ObjectSummary::getKey)
          .map(key -> key.split("/")[1])
          .filter(key -> !rpicsd.contains(key))
          .collect(Collectors.toSet())
          .forEach(System.out::println);
      
//      Smoothie de frambuesa y chocolate para un corazon saludable.jpg
//      Smoothie de manzana y canela con indice glicemico bajo.jpg
//      Smoothie verde de mango para un corazon saludable.jpg
//      Smoothie desintoxicante citrico con frutos silvestres y jengibre.jpg
//      Smoothie cremoso de naranja para fortalecer el sistema inmunologico.jpg
//      crema de pesto a la menta.jpg
//      BLSTXP7003 batido pera y yogur.jpg
//      Smoothie de duraznos con crema para aumentar la masa muscular.jpg
//      Smoothie de fresa y durazno con indice glicemico bajo.jpg
//      Smoothie antioxidante de kiwi y mango para fortalecer el sistema inmunologico.jpg
//      Smoothie de mango y papaya para una piel saludable.jpg
//      Smoothie de proteina paraiso tropical para aumentar la masa muscular.jpg
//      Smoothie con fresas y cerezas para propiciar el sueno.jpg
//      BLSTXP7003Batido popeye.jpg
//      Smoothie energizante de maca y menta.jpg


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void listObjects() {

    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1).build();
    List<Bucket> buckets = s3.listBuckets();
    buckets.forEach(b -> System.out.println(b.getName()));

    ListObjectsV2Result r1 = s3.listObjectsV2("recipe-original-pics-dev/original");
    r1.getObjectSummaries().forEach(os -> System.out.println(os.getKey()));
    r1.getObjectSummaries().parallelStream().map(os -> os.getKey()).collect(Collectors.toSet());

    //    ListObjectsV2Result r1 = s3.listObjectsV2("");

    //    obj.getObjectContent();

    try {

      FileSystem fs = FileSystems.getDefault();
      Set<String> existingFiles =
          Arrays.asList(new File("T:\\\\temp\\\\rfiles").listFiles())
              .stream()
              .map(c -> c.getName().toLowerCase())
              .collect(Collectors.toSet());

      for (S3ObjectSummary os : r1.getObjectSummaries()) {
        if (os.getKey().split("/").length == 1) {
          continue;
        }
        String fileName = os.getKey().split("/")[1];
        if (existingFiles.contains(fileName.toLowerCase())) {
          continue;
        }
        S3Object obj = s3.getObject("recipe-original-pics-dev", os.getKey());
        existingFiles.add(fileName);
        System.out.println("downloading..." + fileName);
        byte[] content = IOUtils.toByteArray(obj.getObjectContent());
        System.out.println();
        Files.copy(new ByteArrayInputStream(content), Paths.get("T:\\temp\\rfiles", fileName));
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    /*
        S3Client s3 =
            S3Client.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(new ProfileCre)
                .build();
        ListObjectsRequest request =
            ListObjectsRequest.builder()
                .bucket("recipe-original-pics-dev")
                .delimiter("/")
                .bucket("original")
                .build();
        ListObjectsResponse response = s3.listObjects(request);

    */
    //		Amazon
    //		ListO
  }

  public static void main(String[] args) {
    System.out.println("testing");
    boolean flag = true;

    compareBuckets();
    //    listObjects();

    if (flag) {
      return;
    }

    List<KeyVersion> keyList = new ArrayList<KeyVersion>();

    AmazonS3 s3Client =
        AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .withCredentials(new ProfileCredentialsProvider("app_user"))
            .build();
    ListObjectsV2Request req =
        new ListObjectsV2Request()
            .withBucketName("applied-jobs")
            .withPrefix("dev/resumes/a6867334-2e61-46d6-8665-6a485511bb66");
    ListObjectsV2Result result = s3Client.listObjectsV2(req);
    for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
      System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());
      keyList.add(new KeyVersion(objectSummary.getKey()));
    }
    keyList.add(new KeyVersion("927b996f-89c0-4f13-9ba2-3e729a231a32"));

    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 60000;
    expiration.setTime(expTimeMillis);

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(
                "applied-jobs",
                "dev/resumes/a6867334-2e61-46d6-8665-6a485511bb66/a6867334-2e61-46d6-8665-6a485511bb66.pdf")
            .withMethod(HttpMethod.GET)
            .withExpiration(expiration);
    URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    System.out.println(url.toString());

    //         s3Client.deleteObjects(new DeleteObjectsRequest("applied-jobs1").withKeys(keyList));

  }
}
