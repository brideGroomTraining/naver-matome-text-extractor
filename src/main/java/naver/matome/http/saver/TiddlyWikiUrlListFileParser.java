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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author ryoji
 */
public class TiddlyWikiUrlListFileParser {
    private static final Logger LOG = Logger.getLogger(TiddlyWikiUrlListFileParser.class.getName());
    public static List<URL> generateUrlListFromFile() throws IOException {
        List<String> lines = FileUtils.readLines(new File("url_list.txt"), StandardCharsets.UTF_8);
        return (ArrayList<URL>) CollectionUtils.collect(lines, new Transformer() {
            @Override
            public URL transform(Object input) {
                try {
                    return new URL(UrlExtractor.extractUrls((String)input).get(0).replace("http:", "https:").replaceAll("\\?$", ""));
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                    throw new IllegalStateException(ex);
                }
            }
        });
    }
}
