package com.spiderdt.mars.controller

import com.spiderdt.mars.service.TrendService
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


    @RequestMapping(value = "/trend/category", method = RequestMethod.GET)
    @ResponseBody
    def getCategory() {
        def data_source = "mars"
        JSONObject json = new JSONObject()
        try{
        def data = trendService.getCategory(data_source)
        def all_product = trendService.queryAllProduct(data_source)
        json.put("status", "success")
        json.put("data", data)
        json.put("all_product", all_product)}
        catch (Exception e){
            json.put("status","failure")
            json.put("message",e.message)
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }


    @RequestMapping(value = "/trend/trend_spline", method = RequestMethod.POST)
    @ResponseBody
    def getScore(@RequestBody Map<String, String> params) {
        JSONObject json = new JSONObject()
        def category_1 = params.get("category_1");
        def category_2 = params.get("category_2");
        def product_name = params.get("product_name");

    }


    @RequestMapping(value = "/trend/quantity", method = RequestMethod.GET)
    @ResponseBody
    def getQuantityScore(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        def data_source = "mars"
        def category_1 = request.getParameter("category_1")
        def category_2 = request.getParameter("category_2")
        def product_name = request.getParameter("product_name")
        def time = ["month", "week"]
        def data = time.collect {
            trendService.queryProductQuantity(data_source, category_1, category_2, product_name, it)
        }
        json.put("status","success")
        json.put("data",data)
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }

 @RequestMapping(value = "/trend/comparison", method = RequestMethod.GET)
    @ResponseBody
    def getComparisonScore(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        def data_source = "mars"
        def category_1 = request.getParameter("category_1")
        def category_2 = request.getParameter("category_2")
        def product_name = request.getParameter("product_name")
        def time = ["month", "week"]
        def data = time.collect {
            trendService.queryComparison(data_source, category_1, category_2, product_name, it)
        }
        json.put("status","success")
        json.put("data",data)
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }



}


//@RequestMapping(value = "/trend/product_hb", method = RequestMethod.GET)
//@ResponseBody
//def getHbScore(HttpServletRequest request) {
//    JSONObject json = new JSONObject()
//    def data = new HashMap()
//    def category_1 = request.getParameter("category_1")
//    def category_2 = request.getParameter("category_2")
//    def product_name = request.getParameter("product_name")
//    def week = request.getParameter("week")
//    def month = request.getParameter("month")
//    if (category_1 != null & category_2 != null & product_name != null) {
//        def top10_week_hb = trendService.getSingleProductWeekHb(category_1, category_2, product_name, week)
//        def bottom10_week_hb = trendService.getSingleProductWeekHb(category_1, category_2, product_name, week)
//        def top10_month_hb = trendService.getSingleProductMonthHb(category_1, category_2, product_name, week)
//        def bottom_10_month_hb = trendService.getSingleProductMonthHb(category_1, category_2, product_name, week)
//
//        data.put("top10_week_hb", top10_week_hb)
//        data.put("bottom10_week_hb", bottom10_week_hb)
//        data.put("top10_month_hb", top10_month_hb)
//        data.put("bottom_10_month_hb", bottom_10_month_hb)
//        json.put("status", "success")
//        json.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
//    } else if (category_1 != null & category_2 != null) {
//        def top10_week_hb = trendService.getProductTop10WeekHb(category_1, category_2, week)
//        def bottom10_week_hb = trendService.getProductBottom10WeekHb(category_1, category_2, week)
//        def top10_month_hb = trendService.getProductTop10MonthHb(category_1, category_2, month)
//        def bottom_month_hb = trendService.getProductBottom10MonthHb(category_1, category_2, month)
//
//        data.put("top10_week_hb", top10_week_hb)
//        data.put("bottom10_week_hb", bottom10_week_hb)
//        data.put("top10_month_hb", top10_month_hb)
//        data.put("bottom10_month_hb", bottom_month_hb)
//        json.put("status", "success")
//        json.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
//    } else if (category_1 != null & category_2 == null) {
//        def top10_week_hb = trendService.getCategory2Top10WeekHb(category_1, week)
//        def bottom10_week_hb = trendService.getCategory2Bottom10WeekHb(category_1, week)
//        def top10_month_hb = trendService.getCategory2Top10MonthHb(category_1, month)
//        def bottom10_month_hb = trendService.getCategory2Bottom10MonthHb(category_1, month)
//        data.put("top10_week_hb", top10_week_hb)
//        data.put("bottom10_week_hb", bottom10_week_hb)
//        data.put("top10_month_hb", top10_month_hb)
//        data.put("bottom10_month_hb", bottom10_month_hb)
//        json.put("status", "success")
//        json.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
//    } else if (category_1 == null & category_2 == null) {
//        def top10_week_hb = trendService.getCategory1Top10WeekHb(month)
//        def bottom10_week_hb = trendService.getCategory1Bottom10WeekHb(month)
//        def top10_month_hb = trendService.getCategory1Top10MonthHb(month)
//        def bottom10_month_hb = trendService.getCategory1Bottom10MonthHb(month)
//
//        data.put("top10_week_hb", top10_week_hb)
//        data.put("bottom10_week_hb", bottom10_week_hb)
//        data.put("top10_month_hb", top10_month_hb)
//        data.put("bottom10_month_hb", bottom10_month_hb)
//        json.put("status", "success")
//        json.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
//    }
//}
//
//
//@RequestMapping(value = "/trend/product_tb", method = RequestMethod.GET)
//@ResponseBody
//def getTbScore(HttpServletRequest request) {
//    JSONObject json = new JSONObject()
//    def category_1 = request.getParameter("category_1");
//    def category_2 = request.getParameter("category_2");
//    if (category_1 != null & category_2 != null) {
//        def data = trendService.getProductTb(category_1, category_2)
//        json.put("status", "success")
//        json.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
//    } else if (category_1 != null & category_2 == null) {
//        def data = trendService.getCategory2Tb(category_1)
//        json.put("status", "success")
//        json.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
//    } else if (category_1 == null & category_2 == null) {
//        def data = trendService.getCategory1Tb()
//        json.put("status", "success")
//        json.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
//    }
//}
//
//
//}
