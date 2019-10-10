package com.example.imsample.utils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wapchief on 2017/9/25.
 */

public class HttpBasicUtils {

        /**
         * HTTP Basic Authentication
         *
         * @param address
         * @param username
         * @return
         * @throws Exception
         */
        public static int connection(String address, String username)
                throws Exception {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            String author = "Basic " + Base64Utils.getBase64(Base64Utils.getBase64("b47a37f342eba5f9fbcd1961" + ":masterSecret"));
            conn.setRequestProperty("Authorization", author);
            conn.connect();
            return conn.getResponseCode();
        }


}
