package com.success.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sender {

  private void get() {
    int counter = 0;
    try {

      Socket server = new Socket("localhost", 8888);
      while (true) {
        System.out.println("client - before receiving from server... ");
        DataInputStream dataIn = new DataInputStream(server.getInputStream());
        System.out.println("client - Received from server .. " + dataIn.readUTF());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    /*while (true) { // NOSONAR
      try {

        System.out.println("sender - after creating socket client ... ");

        //        OutputStream out = client.getOutputStream();
        //        DataOutputStream dataOs = new DataOutputStream(out);
        //        System.out.println("sender - before sending... ");
        //        dataOs.writeUTF("Hello from sender. counter " + counter);
        //        System.out.println("client - after sending... ");

        System.out.println("client - before receiving from server... ");
        DataInputStream dataIn = new DataInputStream(client.getInputStream());
        System.out.println("client - Received from server .. " + dataIn.readUTF());

        //        client.close();
        Thread.sleep(5000);
        counter++;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }*/
  }

  public static void main(String[] args) {

    for (int i = 0; i < 1; i++) {
      new Thread(() -> new Sender().get(), "Thread " + i).start();
    }
  }
}
