package com.spiderdt.mars.service

import com.spiderdt.mars.dao.CommonDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2017/4/6.
 */
@Service
class CommonService {
    @Autowired
    CommonDao commonDao

    def dateRange(data_source) {
        commonDao.dateRange(data_source)
    }

    def promo_dateRange(data_source) {
        commonDao.promo_dateRange(data_source)
    }

    def listToString(list) {
        list.toString().replace("[", "('").replace("]", "')").replace(",", "','").replace("' ", "'").replace(" '", "'")
    }

    def handelCategory(category_1, category_2, product_name) {
        def var = "category_1"
        def c_list = new ArrayList()
        if (category_1 != null && category_1.size() != 0) {
            c_list.add("category_1 in " + listToString(category_1))
            var = "category_2"
        }
        if (category_2 != null && category_2.size() != 0) {
            c_list.add("category_2 in " + listToString(category_2))
            var = "product_name"
        }
        if (product_name != null && product_name.size() != 0) {
            c_list.add("product_name in " + listToString(product_name))
            var = "product_name"
        }
        [c_list: c_list,
         var   : var]
    }


    def calculateSelectorCondition(category1, category2, product_name) {

        // 一共有四种情况
        // 1. 1 个或多个product
        // 2. 所有的product
        // 3. category1 下的具体某个 category2
        // 4. 具体的 category1

        // 0 0 0 -> 0
        // 0 0 1 -> 1
        // 0 1 0 -> 3
        // 0 1 1 -> 1
        // 1 0 0 -> 4
        // 1 0 1 -> 1
        // 1 1 0 -> 3
        // 1 1 1 -> 1

        int condition = 2

        if (product_name != null) {
            condition = 1
        } else if (category2 != null && category2 != '') {
            condition = 3
        } else if (category1 != null && category1 != '') {
            condition = 4
        } else {
            condition = 2
        }
        condition
    }
}
