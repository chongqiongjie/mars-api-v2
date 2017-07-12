package com.spiderdt.mars.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject
import com.spiderdt.mars.util.Slog;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by kun on 2017/7/11.
 */
@Service
public class JhttpClient {
    private static Logger log = LoggerFactory.getLogger(JhttpClient.class);
    @Autowired
    Slog slog

    public static JSONArray httpPost(String url, JSONObject jsonParam) {
        return JhttpClient.httpPost(url, jsonParam, false);
    }

    public static JSONArray httpPost(String url, JSONObject params, Boolean noNeedResponse) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONArray jsonResult = null;
        HttpPost method = new HttpPost(url);
        log.info("params is " + params)
        try {
            if (null != params) {
                StringEntity entity = new StringEntity(params.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);

            log.info ("result is "+ result)
            log.info ("result is "+ result.toString())
            log.info("request:" + url + " and return:" + result.getStatusLine());
            url = URLDecoder.decode(url, "UTF-8");
            if (result.getStatusLine().getStatusCode() == 200) {
                def str
                try {
                    str = EntityUtils.toString(result.getEntity(), "UTF-8");
                    log.info("request return entity :" + str);
                    if (noNeedResponse) {
                        return null;
                    }
                    jsonResult = JSONArray.parseArray(str);
                } catch (Exception e) {
                    log.error("post request error:" + url, e);
                }
            }
        } catch (IOException e) {
            log.error("post request error:" + url, e);
        }
        log.debug("http post return:" + jsonResult);
        return jsonResult;
    }

    public static JSONObject httpGet(String url) {
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                jsonResult = JSONObject.parseObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                log.error("get request error:" + url);
            }
        } catch (IOException e) {
            log.error("get request error:" + url, e);
        }
        return jsonResult;
    }
}
