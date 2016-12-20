/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naver.matome.http.saver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ryoji
 */
public class HttpContentsCleaner {
    public static String clean(String contents) {
        List<String> arrayLines = Arrays.asList(contents.split("\n"));
        List<String> filtered = arrayLines.stream().filter(s -> !s.contains("詳細を見る</a>")).collect(Collectors.toList());
        return "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"></head><body>"  + StringUtils.join(filtered.toArray(), "\n") + "</body></html>";
    }
}
