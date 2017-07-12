package com.spiderdt.mars.service

import com.alibaba.fastjson.JSONObject
import com.spiderdt.mars.dao.SubplanDao
import com.spiderdt.mars.util.Slog
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2017/7/10.
 */
@Service
class SubplanService {

    @Autowired
    Slog slog

    @Autowired
    SubplanDao subplanDao

    @Autowired
    CommonService commonService

    @Autowired
    HttpClientService httpClientService

    @Autowired
    JhttpClient jhttpClient

    def queryLastMonthAvg(data_source, category_1, category_2, product_name) {
        def last_date = commonService.dateRange(data_source).max
        def c_list = commonService.handelCategory(category_1, category_2, product_name)
        slog.info("category list is " + c_list)
        def map = [data_source: data_source,
                   list       : c_list.c_list,
                   last_date  : last_date,
                   var        : c_list.var]
        subplanDao.queryLastMonthAvg(map)
    }

    def createSubplan(name, user_id, category, group, product, start_time, end_time, discount, coupon, ln_baseprice, debut) {
        subplanDao.createSubplan(name, user_id, category, group, product, start_time, end_time, "1",
                discount, coupon, ln_baseprice, debut, "create")
    }

    def executeSubplan(subplan_url, name, user_name, category, group, product, start_time, end_time, drivers) {
        JSONObject json = new JSONObject()
        def jsonSlurper = new JsonSlurper()
        json = [name      : name,
                category  : category,
                group     : group,
                product   : product,
                start_time: start_time,
                end_time  : end_time,
                drivers   : drivers]
        try {
//        res 为JSONObject 类型
            def res = jhttpClient.httpPost(subplan_url, json)

            slog.info("######### executeSubplan return is " + res)
            slog.info("executeSubplan return size is " + res.size())
            if (res.size() >= 1) {
                subplanDao.updateSubplanResult(name, user_name, res.toString())
            } else {
                subplanDao.updateStatusTo0(name, user_name)
            }

        } catch (Exception e) {
            subplanDao.updateStatusTo0(name, user_name)
        }
    }

    def listSubplan(user_name) {
        subplanDao.listSubplan(user_name)
    }

    def showSubplan(name, user_name) {
        subplanDao.showSubplan(name, user_name)
    }

    def deleteSubplan(name, user_name) {
        subplanDao.updateStatusToDel(name, user_name)
    }
}
