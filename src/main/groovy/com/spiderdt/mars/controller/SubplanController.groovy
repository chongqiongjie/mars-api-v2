package com.spiderdt.mars.controller

import com.spiderdt.mars.service.CommonService
import com.spiderdt.mars.service.SubplanService
import com.spiderdt.mars.util.Slog
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

/**
 * Created by kun on 2017/7/10.
 */
@Controller
class SubplanController {

    @Autowired
    SubplanService subplanService

    @Autowired
    CommonService commonService

    @Autowired
    Slog slog

    @Value('${url.subplan_url}')
    String subplan_url


    @RequestMapping(value = "/createplan/trendarea", method = RequestMethod.GET)
    @ResponseBody
    def getComparisonScore(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        try {
            def data_source = "tutuanna"
            def category_1 = request.getParameter("category_1")
            def category_2 = request.getParameter("category_2")
            def product_name = request.getParameter("product_name")
            def last_month_avg = subplanService.queryLastMonthAvg(data_source, category_1, category_2, product_name)
            def last_date = commonService.dateRange(data_source).max
            def data = [avg_cat  : last_month_avg,
                        last_date: last_date]
            json = [status: "success",
                    data  : data]
        } catch (Exception e) {
            json = [status : "failure",
                    message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }
    @RequestMapping(value = "/createplan/subplan", method = RequestMethod.GET)
    @ResponseBody
    def listSubplan(@RequestBody params) {
        JSONObject json = new JSONObject()

    }

    @RequestMapping(value = "/createplan/subplan", method = RequestMethod.POST)
    @ResponseBody
    def CreateSubplan(@RequestBody params) {
        JSONObject json = new JSONObject()
        def data_source = "tutuanna"
        try {
            def user_id = "tutuanna"
            def name = params.get("name")
            slog.info("name:" + name)
            def category = params.get("category")
            def group = params.get("group")
            def product = params.get("product")
            def start_time = params.get("start_time")
            def end_time = params.get("end_time")


            def discountStr = params.get("drivers").get("effect_discount")
            slog.info("aaaa:" + params.get("drivers"))
            slog.info("dis:" + discountStr)
            Double discount
            if (discountStr == "" || discountStr == null) {
                discount = 0
            } else {
                discount = Double.valueOf(discountStr)
            }
            def couponStr = params.get("drivers").get("effect_coupon")
            Double coupon
            if (couponStr == "" || couponStr == null) {
                coupon = 0
            } else {
                coupon = Double.valueOf(couponStr)
            }
            def ln_basepriceStr = params.get("drivers").get("effect_ln_baseprice")
            Double ln_baseprice
            if (ln_basepriceStr == "" || ln_basepriceStr == null) {
                ln_baseprice = 0
            } else {
                ln_baseprice = Double.valueOf(ln_basepriceStr)
            }
            def debutStr = params.get("drivers").get("effect_debut")
            Double debut
            if (debutStr == "" || discountStr == null) {
                debut = 0
            } else {
                debut = Double.valueOf(debutStr)
            }
            def drivers = ["coupon": coupon, "ln_baseprice": ln_baseprice, "debut": debut, "discount": discount]
            slog.info("drivers:" + drivers)

            subplanService.createSubplan(name, user_id, category, group, product, start_time, end_time, discount, coupon, ln_baseprice, debut)
            subplanService.executeSubplan(subplan_url, name, user_id, category, group, product, start_time, end_time, drivers)

            json = [status: "success"]
        } catch (Exception e) {
            json = [status : "failure",
                    message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.toString())

    }

    @RequestMapping(value = "/createplan/showsubplan", method = RequestMethod.GET)
    @ResponseBody
    def showSubplan(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        try {
            def name = request.getParameter("name")
            def user_name = "tutuanna"

            json = [status: "success",
                    result: subplanService.showSubplan(name, user_name)]
        } catch (Exception e) {
            json = [status : "failure",
                    message: e.message]
        }

        return ResponseEntity.status(HttpStatus.OK).body(json.toString())
    }

    @RequestMapping(value = "/plans/deletesubplan", method = RequestMethod.GET)
    @ResponseBody
    def delete(HttpServletRequest request) {
        JSONObject response = new JSONObject()
        def name = request.getParameter("name")
        def user_name = "tutuanna"
        subplanService.deleteSubplan(name,user_name)
        response.put("status", "success")
        return ResponseEntity.status(HttpStatus.OK).body(response.todef())
    }
}
