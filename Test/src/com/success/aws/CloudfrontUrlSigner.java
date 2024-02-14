package com.success.aws;

import static com.amazonaws.services.cloudfront.util.SignerUtils.buildCannedPolicy;
import static com.amazonaws.services.cloudfront.util.SignerUtils.loadPrivateKey;
import static com.amazonaws.services.cloudfront.util.SignerUtils.makeBytesUrlSafe;
import static com.amazonaws.services.cloudfront.util.SignerUtils.makeStringUrlSafe;
import static com.amazonaws.services.cloudfront.util.SignerUtils.signWithSha1RSA;
import static com.amazonaws.util.StringUtils.UTF8;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.stream.Stream;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.services.cloudfront.util.SignerUtils.Protocol;
import com.amazonaws.util.DateUtils;

public class CloudfrontUrlSigner {

  private static void signUrl() {

    Protocol protocol = Protocol.https;
    String distributionDomain = "ss4ydvtxnn30y3.cloudfront.net";
    File privateKeyFile = new File("/tmp/private_key.der");
    String s3ObjectKey = "IMG_7481.JPG";
    String keyPairId = "KJBKRICSLQOR8";
    Date dateLessThan = DateUtils.parseISO8601Date("2023-09-14T22:20:00.000Z");

    try {
      String url2 = CloudFrontUrlSigner.getSignedURLWithCustomPolicy(protocol, distributionDomain,
          privateKeyFile, s3ObjectKey, keyPairId, dateLessThan, null, null);
      System.out.println(url2);


      // SignerUtils.loadPrivateKey(url2);
      PrivateKey privateKey = loadPrivateKey(privateKeyFile);
      String policy = SignerUtils.buildCustomPolicy("https://ss4ydvtxnn30y3.cloudfront.net/*",
          dateLessThan, null, null);
      // String cannedPolicy = buildCannedPolicy(resourceUrlOrPath, dateLessThan);
      byte[] signatureBytes = signWithSha1RSA(policy.getBytes(UTF8), privateKey);
      String urlSafeSignature = makeBytesUrlSafe(signatureBytes);
      String urlSafePolicy = makeStringUrlSafe(policy);
      
      StringBuilder url = new StringBuilder();
      url.append("https://ss4ydvtxnn30y3.cloudfront.net/IMG_7481.JPG");
      url.append("?Policy=");
      url.append(urlSafePolicy);
      url.append("&Signature=");
      url.append(urlSafeSignature);
      url.append("&Key-Pair-Id=");
      url.append(keyPairId);
      
      System.out.println(url.toString());
      
//      Set-Cookie: CloudFront-Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cDovL2QxMTExMTFhYmNkZWY4LmNsb3VkZnJvbnQubmV0L2dhbWVfZG93bmxvYWQuemlwIiwiQ29uZGl0aW9uIjp7IklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIxOTIuMC4yLjAvMjQifSwiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE0MjY1MDAwMDB9fX1dfQ__; Domain=example.org; Path=/; Secure; HttpOnly
//      Set-Cookie: CloudFront-Signature=dtKhpJ3aUYxqDIwepczPiDb9NXQ_; Domain=example.org; Path=/; Secure; HttpOnly
//      Set-Cookie: CloudFront-Key-Pair-Id=K2JCJMDEHXQW5F; Domain=example.org; Path=/; Secure; HttpOnly
      
    } catch (InvalidKeySpecException | IOException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {
    signUrl();
  }

}
