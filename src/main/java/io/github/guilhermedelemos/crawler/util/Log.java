package io.github.guilhermedelemos.crawler.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private Log() {}

    public static void log(String message) {
        log(message, true);
    }

    public static void log(String message, boolean date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        if(date) {
            sb.append(dateFormat.format(new Date()));
            sb.append(" ");
        }
        sb.append(message);
        System.out.println(sb.toString());
    }

}
