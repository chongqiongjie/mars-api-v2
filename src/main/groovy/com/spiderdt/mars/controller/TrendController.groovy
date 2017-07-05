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
        JSONObject response = new JSONObject()
        def data = trendService.getCategory(data_source)
        def all_product = trendService.queryAllProduct(data_source)
        response.put("status", "success")
        response.put("data", data)
        response.put("all_product", all_product)
        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
    }










    @RequestMapping(value = "/trend/trend_spline", method = RequestMethod.POST)
    @ResponseBody
    def getScore(@RequestBody Map<String, String> params) {
        JSONObject response = new JSONObject()
        def category_1 = params.get("category_1");
        def category_2 = params.get("category_2");
        def product_name = params.get("product_name");


        if (category_1 == null & category_2 == null & product_name == null) {
            def data = trendService.getAllScore()
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 != null & product_name != null) {
            def data = trendService.getProductScore(category_1, category_2, product_name)
            // println(data)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 != null & product_name == null) {
            def data = trendService.getCategory2Score(category_1, category_2)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 == null) {
            def data = trendService.getCategory1Score(category_1)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        }
    }


    @RequestMapping(value = "/trend/quantity", method = RequestMethod.GET)
    @ResponseBody
    def getQuantityScore(HttpServletRequest request) {
        JSONObject response = new JSONObject()
        def data = new HashMap()
        def category_1 = request.getParameter("category_1")
        def category_2 = request.getParameter("category_2")
        def product_name = request.getParameter("product_name")
        def week = request.getParameter("week")
        def month = request.getParameter("month")

        if (category_1 != null & category_2 != null & product_name != null) {
            def top10_week_quantity = trendService.getSingleProductTop10WeekQuantity(category_1, category_2, product_name, week)
            def bottom10_week_quantity = trendService.getSingleProductBottom10WeekQuantity(category_1, category_2, product_name, week)
            def top10_month_quantity = trendService.getSingleProductTop10MonthQuantity(category_1, category_2, product_name, month)
            def bottom10_month_quantity = trendService.getSingleProductBottom10MonthQuantity(category_1, category_2, product_name, month)

            data.put("top10_week_quantity", top10_week_quantity)
            data.put("bottom10_week_quantity", bottom10_week_quantity)
            data.put("top10_month_quantity", top10_month_quantity)
            data.put("bottom10_month_quantity", bottom10_month_quantity)

            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 != null) {
            def top10_week_quantity = trendService.getProductTop10WeekQuantity(category_1, category_2, week)
            def bottom10_week_quantity = trendService.getProductBottom10WeekQuantity(category_1, category_2, week)
            def other_week_quantity = trendService.getProductOtherWeekQuantity(category_1, category_2, week)
            def top10_month_quantity = trendService.getProductTop10MonthQuantity(category_1, category_2, month)
            def bottom10_month_quantity = trendService.getProductBottom10MonthQuantity(category_1, category_2, month)
            def other_month_quantity = trendService.getProductOtherMonthQuantity(category_1, category_2, month)

            data.put("top10_week_quantity", top10_week_quantity)
            data.put("bottom10_week_quantity", bottom10_week_quantity)
            data.put("other_week_quantity", other_week_quantity)
            data.put("top10_month_quantity", top10_month_quantity)
            data.put("bottom10_month_quantity", bottom10_month_quantity)
            data.put("other_month_quantity", other_month_quantity)

            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 == null) {
            def top10_week_quantity = trendService.getCategory2Top10WeekQuantity(category_1, week)
            def bottom10_week_quantity = trendService.getCategory2Bottom10WeekQuantity(category_1, week)
            def other_week_quantity = trendService.getCategory2OtherWeekQuantity(category_1, week)
            def top10_month_quantity = trendService.getCategory2Top10MonthQuantity(category_1, month)
            def bottom10_month_quantity = trendService.getCategory2Bottom10MonthQuantity(category_1, month)
            def other_month_quantity = trendService.getCategory2OtherMonthQuantity(category_1, month)


            data.put("top10_week_quantity", top10_week_quantity)
            data.put("bottom10_week_quantity", bottom10_week_quantity)
            data.put("other_week_quantity", other_week_quantity)
            data.put("top10_month_quantity", top10_month_quantity)
            data.put("bottom10_month_quantity", bottom10_month_quantity)
            data.put("other_month_quantity", other_month_quantity)

            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 == null & category_2 == null) {
            def top10_week_quantity = trendService.getCategory1Top10WeekQuantity(week)
            def bottom10_week_quantity = trendService.getCategory1Bottom10WeekQuantity(week)
            def other_week_quantity = trendService.getCategory1OtherWeekQuantity(week)
            def top10_month_quantity = trendService.getCategory1Top10MonthQuantity(month)
            def bottom10_month_quantity = trendService.getCategory1Bottom10MonthQuantity(month)
            def other_month_quantity = trendService.getCategory1OtherMonthQuantity(month)

            data.put("top10_week_quantity", top10_week_quantity)
            data.put("bottom10_week_quantity", bottom10_week_quantity)
            data.put("other_week_quantity", other_week_quantity)
            data.put("top10_month_quantity", top10_month_quantity)
            data.put("bottom10_month_quantity", bottom10_month_quantity)
            data.put("other_month_quantity", other_month_quantity)

            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        }
    }


    @RequestMapping(value = "/trend/product_hb", method = RequestMethod.GET)
    @ResponseBody
    def getHbScore(HttpServletRequest request) {
        JSONObject response = new JSONObject()
        def data = new HashMap()
        def category_1 = request.getParameter("category_1")
        def category_2 = request.getParameter("category_2")
        def product_name = request.getParameter("product_name")
        def week = request.getParameter("week")
        def month = request.getParameter("month")
        if (category_1 != null & category_2 != null & product_name != null) {
            def top10_week_hb = trendService.getSingleProductWeekHb(category_1, category_2, product_name, week)
            def bottom10_week_hb = trendService.getSingleProductWeekHb(category_1, category_2, product_name, week)
            def top10_month_hb = trendService.getSingleProductMonthHb(category_1, category_2, product_name, week)
            def bottom_10_month_hb = trendService.getSingleProductMonthHb(category_1, category_2, product_name, week)

            data.put("top10_week_hb", top10_week_hb)
            data.put("bottom10_week_hb", bottom10_week_hb)
            data.put("top10_month_hb", top10_month_hb)
            data.put("bottom_10_month_hb", bottom_10_month_hb)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 != null) {
            def top10_week_hb = trendService.getProductTop10WeekHb(category_1, category_2, week)
            def bottom10_week_hb = trendService.getProductBottom10WeekHb(category_1, category_2, week)
            def top10_month_hb = trendService.getProductTop10MonthHb(category_1, category_2, month)
            def bottom_month_hb = trendService.getProductBottom10MonthHb(category_1, category_2, month)

            data.put("top10_week_hb", top10_week_hb)
            data.put("bottom10_week_hb", bottom10_week_hb)
            data.put("top10_month_hb", top10_month_hb)
            data.put("bottom10_month_hb", bottom_month_hb)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 == null) {
            def top10_week_hb = trendService.getCategory2Top10WeekHb(category_1, week)
            def bottom10_week_hb = trendService.getCategory2Bottom10WeekHb(category_1, week)
            def top10_month_hb = trendService.getCategory2Top10MonthHb(category_1, month)
            def bottom10_month_hb = trendService.getCategory2Bottom10MonthHb(category_1, month)
            data.put("top10_week_hb", top10_week_hb)
            data.put("bottom10_week_hb", bottom10_week_hb)
            data.put("top10_month_hb", top10_month_hb)
            data.put("bottom10_month_hb", bottom10_month_hb)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 == null & category_2 == null) {
            def top10_week_hb = trendService.getCategory1Top10WeekHb(month)
            def bottom10_week_hb = trendService.getCategory1Bottom10WeekHb(month)
            def top10_month_hb = trendService.getCategory1Top10MonthHb(month)
            def bottom10_month_hb = trendService.getCategory1Bottom10MonthHb(month)

            data.put("top10_week_hb", top10_week_hb)
            data.put("bottom10_week_hb", bottom10_week_hb)
            data.put("top10_month_hb", top10_month_hb)
            data.put("bottom10_month_hb", bottom10_month_hb)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        }
    }


    @RequestMapping(value = "/trend/product_tb", method = RequestMethod.GET)
    @ResponseBody
    def getTbScore(HttpServletRequest request) {
        JSONObject response = new JSONObject()
        def category_1 = request.getParameter("category_1");
        def category_2 = request.getParameter("category_2");
        if (category_1 != null & category_2 != null) {
            def data = trendService.getProductTb(category_1, category_2)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 != null & category_2 == null) {
            def data = trendService.getCategory2Tb(category_1)
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        } else if (category_1 == null & category_2 == null) {
            def data = trendService.getCategory1Tb()
            response.put("status", "success")
            response.put("data", data)
            return ResponseEntity.status(HttpStatus.OK).body(response.toString())
        }
    }


}
