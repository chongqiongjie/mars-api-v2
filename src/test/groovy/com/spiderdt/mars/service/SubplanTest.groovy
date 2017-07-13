package com.spiderdt.mars.service

import com.alibaba.fastjson.JSONObject
import com.spiderdt.mars.util.Slog
import groovy.json.JsonOutput
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
        def data_source = "tutuanna"
        def category_1 = [1.0000000000000000]
//        def category_1 = 1.0000000000000000
//        def category_2 = 1.0000000000000000
        def category_2 = null
//        def product_name = "tutuanna 打底裤 秋冬厚款里起毛厚绒保暖裤300D"
        def product_name = null
        slog.info subplanService.queryLastMonthAvg(data_source, category_1, category_2, product_name)
    }

    @Test
    void insertSubplanData(){
//        name, user_id, category, group, product, start_time, end_time, discount, coupon, ln_baseprice, debut
        subplanService.createSubplan("test2", "tutuanna", 1, 1, "10", "2016-12-01", "2017-01-15",0.01,0.01,0.01,0.01)
    }


    @Test
    void createSubplanTest() {

        def drivers = [coupon      : 0.01,
                       ln_baseprice: 0.01,
                       debut       : 0.01,
                       discount    : 0.01]

//        slog.info "@@@@@@@@@@@@" +subplanService.executeSubplan("/predict/sub_promo_plan", "Jtest", "tutuanan", 1, 1, "10", "2016-12-01", "2017-01-15", drivers)
        slog.info subplanService.executeSubplan("http://192.168.1.19:9123/predict/sub_promo_plan", "test2", "tutuanna", 1, 1, "10", "2016-12-01", "2017-01-15", drivers)
    }

    @Test
    void JhttpClientTest() {
        JSONObject params = new JSONObject();
//        String json_string = "{ \"name\":\"test1234\",\"category\":1,\"group\":1,\"product\":\"10\", \"start_time\":\"2016-12-31\", \"end_time\":\"2017-01-20\", \"drivers\":{ \"coupon\":0.01," +
//                " \"ln_baseprice\":0.01, \"debut\":0.01, \"discount\":0.01 }}";
        String json_string = "{\"name\":\"Jtest\",\"category\":1,\"group\":1,\"product\":\"10\",\"start_time\":\"2016-12-31\",\"end_time\":\"2017-01-20\",\"drivers\":{\"debut\":0.01,\"coupon\":0.01,\"discount\":0.01,\"ln_baseprice\":0.01}}"
        params = JSONObject.parseObject(json_string);
        JhttpClient.httpPost("http://192.168.1.19:9123/predict/sub_promo_plan", params);
    }

    @Test
    void listSubplanTest() {
        def user_name = "tutuanna"
        slog.info subplanService.listSubplan(user_name)
    }

    @Test
    void showSubplanTest() {
        def name = "chong"
        def user_name = "tutuanna"
        slog.info subplanService.showSubplan(name, user_name)
    }


    @Test
    void deleteSubplanTest() {
        def name = "chong"
        def user_name = "tutuanna"
        subplanService.deleteSubplan(name, user_name)
    }

    @Test
    void test(){
        List a = ["a","b","c"]
        if(a.size()!=0){
            println "category_1 in" + a.toListString().replace("[","('").replace("]","')").replace(", ","','")
        }
    }
}
