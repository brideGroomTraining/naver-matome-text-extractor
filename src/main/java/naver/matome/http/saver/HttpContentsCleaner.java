/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naver.matome.http.saver;

/**
 *
 * @author ryoji
 */
public class HttpContentsCleaner {
    public static String clean(String contents) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                "<html xmlns:og=\"http://ogp.me/ns#\" xmlns:fb=\"http://www.facebook.com/2008/fbml\" dir=\"ltr\">\n" +
                "<head><meta charset=\"UTF-8\"></head><body>");
        String[] lines = contents.split("\n");
        for (String line : lines) {
            if (!line.contains("詳細を見る</a>")) {
                sb.append(line);
            }
        }
        return sb.append("</body></html>").toString();
    }
}
