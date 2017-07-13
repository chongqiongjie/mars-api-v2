package com.spiderdt.mars.service

import com.spiderdt.mars.dao.CommonDao
import com.spiderdt.mars.dao.TrendDao
import com.spiderdt.mars.util.Slog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.concurrent.ArrayBlockingQueue

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
        def data1 = data.collect {
            [category_1  : [name: it.category_1, id: it.cat1_id],
             category_2  : [name: it.category_2, id: it.cat2_id],
             product_name: [name: it.product_name, id: it.ppg_id]]
        }
        def result = data1.groupBy {
            it.category_1
        }.collectEntries { k, v ->
            [item    : k,
             children: v.groupBy {
                 it.category_2
             }.collectEntries { k2, v2 ->
                 [item    : k2,
                  children: v2.collect {
                      it.product_name
                  }]
             }]
        }
        println("result:" + result)
        result


    }


    def getCategoryList(data_source) {
        def data = trendDao.queryCategory(data_source)
        Set category2_list = data.collect {
            [category_1: it.category_1,
             cat1_id   : it.cat1_id,
             category_2: it.category_2,
             cat2_id   : it.cat2_id]
        }
        Set category1_list = category2_list.collect {
            [category_1: it.category_1,
             cat1_id   : it.cat1_id]
        }
        [category1_list: category1_list,
         category2_list: category2_list,
         product_list  : data]
    }


    def queryAllProduct(data_source) {
        trendDao.queryAllProduct(data_source)
    }

    def queryProductQuantity(data_source, category_1, category_2, product_name, monthOrWeek) {
        def last_date = commonService.dateRange(data_source).max
        def c_list = commonService.handelCategory(category_1, category_2, product_name)
        slog.info("category list is " + c_list)
        def map = [data_source: data_source,
                   list       : c_list.c_list,
                   last_date  : last_date,
                   var        : c_list.var,
                   monthOrWeek: monthOrWeek]
        def data
        if (product_name == null) {
            data = trendDao.queryCategoryQuantity(map)
        } else {
            data = trendDao.queryProductQuantity(map)
        }
        def top10
        def bottom10
        def other
        if (data.size() > 10) {
            top10 = data[0..9]
            bottom10 = data[data.size() - 1..data.size() - 10]
            other = data - top10 - bottom10
        } else if (data.size() == 0) {
            top10 = null
            bottom10 = null
            other = null
        } else {
            top10 = data[0..data.size() - 1]
            bottom10 = data[data.size() - 1..0]
            other = null
        }
        ["top10_${monthOrWeek}_quantity"   : top10,
         "other_${monthOrWeek}_quantity"   : other,
         "bottom10_${monthOrWeek}_quantity": bottom10]
    }


    def queryComparison(data_source, category_1, category_2, product_name, monthOrWeek) {
        def last_date = commonService.dateRange(data_source).max
        def c_list = commonService.handelCategory(category_1, category_2, product_name)
        slog.info("category list is " + c_list)

        def map = [data_source: data_source,
                   list       : c_list.c_list,
                   last_date  : last_date,
                   var        : c_list.var,
                   monthOrWeek: monthOrWeek]
        def hb_data = trendDao.queryHBComparison(map)
        def tb_data = trendDao.queryTBComparison(map)
//        去除同比、环比中null情况
        hb_data.collect {
            if (it.hbresult == null) {
                hb_data -= it
            }
        }
        tb_data.collect {
            if (it.tbresult == null) {
                tb_data -= it
            }
        }
        def hb_top10
        def tb_top10
        def hb_bottom10
        def tb_bottom10
        if (hb_data.size() > 10) {
            hb_top10 = hb_data[0..9]
            hb_bottom10 = hb_data[hb_data.size() - 1..hb_data.size() - 10]
        } else if (hb_data.size() == 0) {
            hb_top10 = null
            hb_bottom10 = null
        } else {
            hb_top10 = hb_data[0..hb_data.size() - 1]
            hb_bottom10 = hb_data[hb_data.size() - 1..0]
        }
        if (tb_data.size() > 10) {
            tb_top10 = tb_data[0..9]
            tb_bottom10 = tb_data[tb_data.size() - 1..tb_data.size() - 10]
        } else if (tb_data.size() == 0) {
            tb_top10 = null
            tb_bottom10 = null
        } else {
            tb_top10 = tb_data[0..tb_data.size() - 1]
            tb_bottom10 = tb_data[tb_data.size() - 1..0]
        }

        ["top10_${monthOrWeek}_hb"  : hb_top10,
         "bottom10_${monthOrWeek}_hb": hb_bottom10,
         "top10_${monthOrWeek}_tb"   : tb_top10,
         "bottom10_${monthOrWeek}_tb": tb_bottom10]
    }

    def querySpline(data_source, category_1, category_2, product_name) {
        def last_date = commonService.dateRange(data_source).max
        def promo_last_date = commonService.promo_dateRange(data_source).max
        def c_list = commonService.handelCategory(category_1, category_2, product_name)

        slog.info("category list is " + c_list)

        def map = [data_source    : data_source,
                   list           : c_list.c_list,
                   last_date      : last_date,
                   promo_last_date: promo_last_date,
                   var            : c_list.var]
        def data = trendDao.querySpline(map)
        def promo_data = trendDao.queryPromoSpline(map)
//        data += promo_data.collect {
//            it.put("actual", 0.00)
//            it.put("quantity", 0.00)
//        }
//        data
        data + promo_data
    }


}
