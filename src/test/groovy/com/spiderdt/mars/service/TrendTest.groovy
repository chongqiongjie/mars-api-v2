package com.spiderdt.mars.service

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

    @Test
    void categoryTest() {
        def data_source = "mars"
        println trendService.getCategory(data_source)
    }

    @Test
    void queryProductQuantityTest() {
        def data_source = "mars"
        def category_1 = 1.0000000000000000
        def category_2 = 1.0000000000000000
        def product_name = null
        println trendService.queryProductQuantity(data_source, category_1, category_2, product_name)
    }

}
