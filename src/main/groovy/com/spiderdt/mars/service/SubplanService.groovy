package com.spiderdt.mars.service

import com.spiderdt.mars.dao.CommonDao
import com.spiderdt.mars.dao.SubplanDao
import com.spiderdt.mars.util.Slog
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

    def queryLastMonthAvg(data_source, category_1, category_2, product_name) {
        def last_date = commonService.dateRange(data_source).max
        def var = "category_1"
        def c_list = new ArrayList()
        if (category_1 != null) {
            c_list.add("category_1 = '$category_1'")
            var = "category_2"
        }
        if (category_2 != null) {
            c_list.add("category_2 = '$category_2'")
            var = "product_name"
        }
        if (product_name != null) {
            c_list.add("product_name = '$product_name'")
            var = "product_name"
        }
        slog.info("category list is " + c_list)
        def map = [data_source: data_source,
                   list       : c_list,
                   last_date  : last_date,
                   var        : var]
        subplanDao.queryLastMonthAvg(map)
    }

    def createSubplan(data_source, name, user_id, category, group, product, start_time, end_time, discount, coupon, ln_baseprice, debut) {
        subplanDao.createSubplan(data_source, name, user_id, category, group, product, start_time, end_time, "1",
                discount, coupon, ln_baseprice, debut, "create")
    }
    def executeSubplan(subplan_url, name, category, group, product, start_time, end_time, drivers){
        httpClientService.execuSubplan(subplan_url, name, category, group, product, start_time, end_time, drivers)
    }
}
