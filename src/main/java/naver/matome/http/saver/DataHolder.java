/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naver.matome.http.saver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ryoji
 */
public enum DataHolder {
    INST;
    private final Map<String, String> urlContentsMap = new HashMap<>();
    private final Set<String> hash = new HashSet<>();
    public Map<String, String> getContentsMap() {
        return urlContentsMap;
    }
    public boolean hasRegistered(String md5) {
        return hash.contains(md5);
    }
    public void registerPage(String url, String contents, String md5) {
        urlContentsMap.put(url, contents);
        hash.add(md5);
    }
}
