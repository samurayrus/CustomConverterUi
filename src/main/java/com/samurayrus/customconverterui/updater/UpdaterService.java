package com.samurayrus.customconverterui.updater;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class UpdaterService {
    private final static String targetIp = "";
    private final static String urlForLoadNewVersion = "";
    private final static String urlForLoadCurrentVersion = "";
    private final static String urlForLoadCurrentVersionInfo = "";
    private final static String currentVersion = "0.2";

    public String getCurrentVersion() {
        return currentVersion;
    }

    public boolean checkNeedToUpdate() {
        if ("0.2".equals(currentVersion))
            return false;
        else
            return true;
    }

    String loadCurrentActualVersion() {
        return loadAnswerFromTarget(urlForLoadCurrentVersion);
    }

    String loadAnswerFromTarget(final String targetUrl) {
        try {
            final CloseableHttpClient httpclient = getCloseableHttpClientWithTrust();

            final HttpUriRequest httpGet = new HttpGet(targetIp + targetUrl);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            String answer = EntityUtils.toString(response.getEntity());
            response.close();
            httpGet.abort();
            httpclient.close();

            return answer;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public UpdaterVersionInfo loadNewVersionInfo() {
        return new ObjectMapper().convertValue(loadAnswerFromTarget(urlForLoadCurrentVersionInfo), UpdaterVersionInfo.class);
    }

    public void update() {

    }

    String loadNewVersion() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        final CloseableHttpClient httpclient = getCloseableHttpClientWithTrust();

        final HttpUriRequest httpGet = new HttpGet(targetIp + urlForLoadNewVersion);
        HttpResponse response = httpclient.execute(httpGet);
        InputStream source = response.getEntity().getContent();
        File file = new File("CustomConverterUi_new.jar");
        FileUtils.copyInputStreamToFile(source, file);

        return file.getAbsolutePath();
    }

    void createScriptsForUpdate() {

    }

    void reRunAfterUpdate() {
//        Runtime.getRuntime().exec("java -jar SMC" + serverVersion + ".jar ");
//        Thread.sleep(2000);
//        System.exit(0);
    }

    private CloseableHttpClient getCloseableHttpClientWithTrust() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return HttpClients.custom()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                                .build()
                        )
                ).build();
    }
}
