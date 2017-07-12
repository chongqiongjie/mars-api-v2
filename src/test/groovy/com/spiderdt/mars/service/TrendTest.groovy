package com.spiderdt.mars.service

import com.spiderdt.mars.util.Slog
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@WebAppConfiguration
class TrendTest extends GroovyTestCase {

    @Autowired
    TrendService trendService

    @Autowired
    Slog slog

    @Test
    void categoryTest() {
        def data_source = "mars"
        slog.info trendService.getCategory(data_source)
    }

    @Test
    void queryProductQuantityTest() {
        def data_source = "tutuanna"
        def category_1 = null
//        def category_1 = [1.0000000000000000]
        def category_2 = [1.0000000000000000]
//        def category_2 = null
        def product_name = ["tutuanna袜子薄款短筒袜 日系短袜 春夏女士棉袜"]
//        def product_name = null
        def monthOrWeek = "month"
        slog.info trendService.queryProductQuantity(data_source, category_1, category_2, product_name, monthOrWeek)
    }

    @Test
    void queryComparisonTest() {
        def data_source = "tutuanna"
        def category_1 = null
//        def category_1 = [1.0000000000000000]
        def category_2 = [1.0000000000000000]
//        def category_2 = null
        def product_name = ["tutuanna 打底裤 秋冬厚款里起毛厚绒保暖裤300D"]
//        def product_name = null
        def monthOrWeek = "month"
        slog.info trendService.queryComparison(data_source, category_1, category_2, product_name, monthOrWeek)
    }

    @Test
    void querySplineTest() {
        def data_source = "tutuanna"
        def category_1 = null
//        def category_1 = [1.0000000000000000]
        def category_2 = [1.0000000000000000]
//        def category_2 = null
//        def product_name = ["tutuanna 打底裤 秋冬厚款里起毛厚绒保暖裤300D"]
        def product_name = null
        slog.info trendService.querySpline(data_source, category_1, category_2, product_name)
    }


}
