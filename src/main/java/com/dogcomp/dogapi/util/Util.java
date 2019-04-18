package com.dogcomp.dogapi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String extractBreedFromUrl(String inputUrl) {
        String url = inputUrl.replace("\\/", "/");
        Pattern p = Pattern.compile("(?<=\\/breeds\\/)(\\w|-)*");
        Matcher m = p.matcher(url);
        String output = "Breed could not be identified";
        if (m.find()) output = m.group(0);
        return output;
    }


    public static File downloadFileToTemp(String fileUrl, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;

        String tempDir = System.getProperty("java.io.tmpdir");
        String outputPath = tempDir + "/" + localFilename;

        try {
            URL url = new URL(fileUrl);
            //connect
            URLConnection urlConn = url.openConnection();

            //get inputstream from connection
            is = urlConn.getInputStream();
            fos = new FileOutputStream(outputPath);

            // 4KB buffer
            byte[] buffer = new byte[4096];
            int length;

            // read from source and write into local file
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return new File(outputPath);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

}
