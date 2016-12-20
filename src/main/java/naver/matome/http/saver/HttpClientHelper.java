/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naver.matome.http.saver;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author ryoji
 */
public class HttpClientHelper {
    private static final Logger LOG = Logger.getLogger(HttpClientHelper.class.getName());
    public static boolean ifExistFetch(String url) {
        final RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
	try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(globalConfig).build()) {
            //httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
            final HttpGet getRequest = new HttpGet(url);
            final RequestConfig localConfig = RequestConfig.copy(globalConfig).setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
            getRequest.setConfig(localConfig);
            final HttpResponse response = httpClient.execute(getRequest);
            //int code = response.getStatusLine().getStatusCode();
            try (InputStream in = response.getEntity().getContent()) {
                final String contents = StringUtils.substringBetween(IOUtils.toString(in), "<!--CONTENTS-AREA-->", "<!--/ArMain01-->");
                final String md5      = DigestUtils.md5Hex(contents);
                LOG.log(Level.INFO, "URL {0}, MD5SUM: {1}, ALREADY REGISTERED: {2}", new Object[]{url, md5, DataHolder.INST.hasRegistered(md5)});
                if (DataHolder.INST.hasRegistered(md5)) return false;
                DataHolder.INST.registerPage(url, contents, md5);
                return true;//(code == 200);
            }
        } catch (IOException ex) {
            LOG.log(Level.WARNING, null, ex);
            return false;
        }
    }
}
