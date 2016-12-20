/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naver.matome.http.saver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ryoji
 */
public class NaverMatomeHttpSaver {
    
    private static final Logger LOG = Logger.getLogger(NaverMatomeHttpSaver.class.getName());
    
    public static void main(String[] args) throws IOException {
        NaverMatomeHttpSaver naverMatomeHttpSaver = new NaverMatomeHttpSaver();
        naverMatomeHttpSaver.process();
    }

    private void process() throws IOException {
        List<URL> urls = TiddlyWikiUrlListFileParser.generateUrlListFromFile();
        for (URL url : urls) {
            LOG.log(Level.INFO, "From URL: {0}", url.toString());
            List<String> pageList = popUrlList(url);
            LOG.log(Level.INFO, "List of pages fetched: {0}", StringUtils.join(pageList.toArray(), "\n"));
            StringBuilder sb = new StringBuilder();
            for (String urlStr : pageList) {
                sb.append(DataHolder.INST.getContentsMap().get(urlStr));
            }
            FileUtils.writeStringToFile(new File(StringUtils.substringAfterLast(url.toString(), "/") + ".html"), HttpContentsCleaner.clean(sb.toString()), StandardCharsets.UTF_8);
        }
    }
    
    private List<String> popUrlList(URL url) {
        List<String> pageList = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            final String urlWithPageNumber = url.toString() + "?&page=" + Integer.toString(i);
            if (HttpClientHelper.ifExistFetch(urlWithPageNumber)) {
                pageList.add(urlWithPageNumber);
            } else {
                break;
            }
        }
        return pageList;
    }
}
