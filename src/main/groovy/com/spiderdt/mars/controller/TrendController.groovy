package com.spiderdt.mars.controller

import com.spiderdt.mars.service.TrendService
import com.spiderdt.mars.util.Sredis
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

/**
 * Created by kun on 2017/7/3.
 */
@Controller
class TrendController {

    @Autowired
    TrendService trendService

    @Autowired
    Sredis sredis

    @RequestMapping(value = "/trend/category", method = RequestMethod.GET)
    @ResponseBody
    def getCategory() {
        def data_source = "tutuanna"
        JSONObject json = new JSONObject()
        try {
            def data = trendService.getCategory(data_source)
            def all_product = trendService.queryAllProduct(data_source)
            def category_List = trendService.getCategoryList(data_source)
            json = [status       : "success",
                    data         : data,
                    category_list: category_List,
                    all_product  : all_product]
        } catch (Exception e) {
            json = [status : "failure",
                    message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }

    @RequestMapping(value = "/trend/trend_spline", method = RequestMethod.GET)
    @ResponseBody
    def getScore(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        try {
            def data_source = "tutuanna"
            def category_1 = request.getParameterValues("category_1[]")
            def category_2 = request.getParameterValues("category_2[]")
            def product_name = request.getParameterValues("product_name[]")
            def data = trendService.querySpline(data_source, category_1, category_2, product_name)
            json = [status: "success",
                    data  : data]
        } catch (Exception e) {
            json = [status : "failure",
                    message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }

    @RequestMapping(value = "/trend/quantity", method = RequestMethod.GET)
    @ResponseBody
    def getQuantityScore(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        try {
            def data_source = "tutuanna"
            def category_1 = request.getParameterValues("category_1[]")
            def category_2 = request.getParameterValues("category_2[]")
            def product_name = request.getParameterValues("product_name[]")
            def time = ["month", "week"]
            def data = time.collect {
                trendService.queryProductQuantity(data_source, category_1, category_2, product_name, it)
            }
            json = [status: "success",
                    data  : data]
        } catch (Exception e) {
            json = [status : "failure",
                    message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }

    @RequestMapping(value = "/trend/comparison", method = RequestMethod.GET)
    @ResponseBody
    def getComparisonScore(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        try {
            def data_source = "tutuanna"
            def category_1 = request.getParameterValues("category_1[]")
            def category_2 = request.getParameterValues("category_2[]")
            def product_name = request.getParameterValues("product_name[]")
            def time = ["month", "week"]
            def data = time.collect {
                trendService.queryComparison(data_source, category_1, category_2, product_name, it)
            }
            json = [status: "success",
                    data  : data]
        } catch (Exception e) {
            json = [status : "failure",
                    message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }

}

