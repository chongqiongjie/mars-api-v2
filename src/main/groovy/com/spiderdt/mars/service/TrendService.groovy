package com.spiderdt.mars.service

import com.spiderdt.mars.dao.CommonDao
import com.spiderdt.mars.dao.TrendDao
import com.spiderdt.mars.util.Slog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2017/4/6.
 */
@Service
class TrendService {
    @Autowired
    TrendDao trendDao

    @Autowired
    CommonService commonService

    @Autowired
    Slog slog

    def getCategory(data_source) {
        def data = trendDao.queryCategory(data_source)
        def result = data.groupBy {
            it.category_1
        }.collectEntries { k, v ->
            [item    : [name: (k)],
             children: v.groupBy {
                 it.category_2
             }.collectEntries { k2, v2 ->
                 [item    : [name: (k2)],
                  children: v2.collect {
                      [name: it.product_name]
                  }]
             }]
        }
        result
    }

    def queryAllProduct(data_source) {
        trendDao.queryAllProduct(data_source)
    }

    def queryProductQuantity(data_source, category_1, category_2, product_name) {
        def condition = commonService.calculateSelectorCondition(category_1, category_2, product_name)
        slog.info("queryProductQuantity condition is " + condition)
        def map = [data_source : data_source,
                   category_1  : category_1,
                   category_2  : category_2,
                   product_name: product_name]
        trendDao.queryProductQuantity(map)
    }
}
