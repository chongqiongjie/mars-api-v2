package com.spiderdt.mars.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.spiderdt.mars.exception.ChangePasswordException
import com.spiderdt.mars.exception.CommonException
import com.spiderdt.mars.exception.PasswordErrorException
import com.spiderdt.mars.exception.UnAuthException
import com.spiderdt.mars.util.Slog
import groovyx.net.http.AsyncHTTPBuilder
import groovyx.net.http.HttpResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*

@Service
public class HttpClientService {


    @Autowired
    UnAuthException unAuthException

    @Autowired
    Slog slog;


    @Value('${url.chainVersion}')
    String chainVersion
    @Value('${rules.basic}')
    String basic
    @Value('${rules.scopes}')
    String scopes
    @Value('${rules.tokenExpiredTime}')
    String tokenExpiredTime

    private AsyncHTTPBuilder http = null
    private AsyncHTTPBuilder httpScheduler = null
    private String clientPortUrl = ""
    private int poolsize
    private int sleepTime = 50
    private int timeout = 1000;

    HttpClientService() {
    }

    HttpClientService(String host, int poolsize) {
        this.clientPortUrl = host
        this.poolsize = poolsize
        http = new AsyncHTTPBuilder(poolSize: this.poolsize, uri: this.clientPortUrl)
        //    http.ignoreSSLIssues()
    }

    // 创建Chronos job
    def executeSubplan(url, name, category, group, product, start_time, end_time, drivers) {
        def future = http.request(POST, JSON) { req ->
            uri.path = url
            body = [name      : name,
                    category  : category,
                    group     : group,
                    product   : product,
                    start_time: start_time,
                    end_time  : end_time,
                    drivers   : drivers.collectEntries { [it.key, it.value / 100] }
            ]
            slog.info "url is " + uri
            slog.info "body is " + body
        }
        slog.info "execute subplan dirvers is " + drivers
        while (!future.done) {
            Thread.sleep(sleepTime)
        }
        future.get()
    }

}
