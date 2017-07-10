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
class SubplanTest extends GroovyTestCase {

    @Autowired
    SubplanService subplanService

    @Autowired
    Slog slog

    @Test
    void queryDefaultSplineTest() {
        def data_source = "mars"
        def category_1 = null
//        def category_1 = 1.0000000000000000
//        def category_2 = 1.0000000000000000
        def category_2 = null
//        def product_name = "tutuanna 打底裤 秋冬厚款里起毛厚绒保暖裤300D"
        def product_name = null
        slog.info subplanService.queryLastMonthAvg(data_source, category_1, category_2, product_name)
    }

}
