package com.success.aws;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class S3UploadUsingPresignedURL {

  /* Use the JDK HttpURLConnection (since v1.1) class to do the upload. */
  public static void useHttpUrlConnectionToPut() {
    try {
      String presignedUrlString = "https://s3.amazonaws.com/rd";
      
      File fileToPut = new File("/tmp/abc.pdf");
      
      URL presignedUrl = new URL(presignedUrlString);
      HttpURLConnection connection = (HttpURLConnection) presignedUrl.openConnection();
      connection.setDoOutput(true);
//      metadata.forEach((k, v) -> connection.setRequestProperty("x-amz-meta-" + k, v));
      connection.setRequestMethod("PUT");
      OutputStream out = connection.getOutputStream();

      try (RandomAccessFile file = new RandomAccessFile(fileToPut, "r");
          FileChannel inChannel = file.getChannel()) {
        ByteBuffer buffer = ByteBuffer.allocate(8192); // Buffer size is 8k

        while (inChannel.read(buffer) > 0) {
          buffer.flip();
          for (int i = 0; i < buffer.limit(); i++) {
            out.write(buffer.get());
          }
          buffer.clear();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      out.close();
      connection.getResponseCode();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    useHttpUrlConnectionToPut();
  }

}
