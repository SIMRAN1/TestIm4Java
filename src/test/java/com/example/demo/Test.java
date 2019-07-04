package com.example.demo;

import org.apache.commons.logging.*;
import org.apache.commons.io.FileUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.core.Stream2BufferedImage;
import org.im4java.process.Pipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.util.Scanner;

public class Test {


    public static final String FORWARD_SLASH = "/";
    public static final String tmpdir = System.getProperty("java.io.tmpdir")+FORWARD_SLASH;

    //private static final Logger LOG = LoggerFactory.getLogger(Test.class);
    Logger logger = LoggerFactory.getLogger(Test.class);
    String write_Command = "magick mogrify -write temp targetFile";
    public static void main(String args[]) {
        Test t = new Test();
        t.uploadImage();
    }

    public void uploadImage() {
        System.setProperty("java.io.tmpdir", "/Users/simranpreetkaur/Desktop/images/");
        File tempFile = null;

        File initialFile = new File("/Users/simranpreetkaur/Desktop/images/rose.jpeg");
        File outFile = new File("/Users/simranpreetkaur/Desktop/images/rose3.jpeg");

        FileInputStream fis = null;
        FileOutputStream fos=null;
        InputStream targetStream = null;
        try {
            tempFile = File.createTempFile("rose5", ".jpeg");
             targetStream = FileUtils.openInputStream(initialFile);
            FileUtils.copyInputStreamToFile(targetStream,tempFile);
            String mimeType = URLConnection.guessContentTypeFromStream(targetStream);
            System.out.println(mimeType);
        }catch(Exception e) {
            e.printStackTrace();
        }
        String imageDire = "/Users/simranpreetkaur/Desktop/images/";

      //  File targetFile = new File(tmpdir+"temp.tmp");
        //FileUtils.copyInputStreamToFile(targetStream,targetFile);
       // Runtime.getRuntime().exec(write_Command);
        IMOperation op = new IMOperation();
        op.addImage("-");
        op.resize(540);
        op.resize(450);
       // Scanner sc = new Scanner(System.in);
      //  String format = sc.nextLine();
        op.addImage(":-");
        try {
            fis = new FileInputStream(imageDire + "rose.jpeg");
            fos = new FileOutputStream(imageDire + "rose1.jpeg");
        } catch(FileNotFoundException f) {
            f.printStackTrace();;
        }
        Pipe pipeIn = new Pipe(fis,null);
        Pipe pipeOut = new Pipe(null,fos);
        Stream2BufferedImage s2b = new Stream2BufferedImage();
        ConvertCmd convert = new ConvertCmd();
        convert.setInputProvider(pipeIn);
        convert.setOutputConsumer(pipeOut);

        //convert.setOutputConsumer(s2b);
        try {
            convert.run(op);
            //convert.setInputProvider(pipeOut);
            //convert.setOutputConsumer(s2b);
            fis.close();
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        //System.out.println("Hello");
        BufferedImage img = s2b.getImage();
            if(img!=null) {

               System.out.println("Image converted");
                //LOG.debug("Image Converted");
            } else {
                System.out.println("Failed to convert Image");
                //LOG.debug("Failed to convert images");
            }
    }
}
