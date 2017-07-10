package com.spiderdt.mars.controller

import com.spiderdt.mars.service.CommonService
import com.spiderdt.mars.service.SubplanService
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
 * Created by kun on 2017/7/10.
 */
@Controller
class SubplanController {

    @Autowired
    SubplanService subplanService

    @Autowired
    CommonService commonService

    @RequestMapping(value = "/createplan/trendarea", method = RequestMethod.GET)
    @ResponseBody
    def getComparisonScore(HttpServletRequest request) {
        JSONObject json = new JSONObject()
        try {
            def data_source = "mars"
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

    @RequestMapping(value = "/createplan/subplan", method = RequestMethod.POST)
    @ResponseBody
    def CreateSubplan(@RequestBody Map<String, Object> params) {
        JSONObject response = new JSONObject()
        String url = "/predict/sub_promo_plan"
        String name = params.get("name")
        println("name:" + name)
        String category = params.get("category")
        String group = params.get("group")
        String product = params.get("product")
        String start_time = params.get("start_time")
        String end_time = params.get("end_time")

        String priceStr = params.get("drivers").get("price")
        Double price
        if (priceStr == "" || priceStr == null) {
            price = 0
        } else {
            price = Double.valueOf(priceStr)
        }
        String discountStr = params.get("drivers").get("effect_discount")
        println("aaaa:" + params.get("drivers"))
        println("dis:" + discountStr)
        Double discount
        if (discountStr == "" || discountStr == null) {
            discount = 0
        } else {
            discount = Double.valueOf(discountStr)
        }
        String couponStr = params.get("drivers").get("effect_coupon")
        Double coupon
        if (couponStr == "" || couponStr == null) {
            coupon = 0
        } else {
            coupon = Double.valueOf(couponStr)
        }
        String ln_basepriceStr = params.get("drivers").get("effect_ln_baseprice")
        Double ln_baseprice
        if (ln_basepriceStr == "" || ln_basepriceStr == null) {
            ln_baseprice = 0
        } else {
            ln_baseprice = Double.valueOf(ln_basepriceStr)
        }
        String debutStr = params.get("drivers").get("effect_debut")
        Double debut
        if (debutStr == "" || discountStr == null) {
            debut = 0
        } else {
            debut = Double.valueOf(debutStr)
        }
        def drivers = ["coupon": coupon, "price": price, "ln_baseprice": ln_baseprice, "debut": debut, "discount": discount]
        println("drivers:" + drivers)

        marsCreateSubplanService.createSubplan(name, category, group, product, start_time, end_time, price, discount, coupon, ln_baseprice, debut)
        marsExecuteCreateService.create(url, name, category, group, product, start_time, end_time, drivers)

        response.put("status", "success")
        return ResponseEntity.status(HttpStatus.OK).body(response.toString())

    }
//
//
//
//    @RequestMapping(value = "/createplan/subplan",method = RequestMethod.GET)
//    @ResponseBody
//    def listSubplan(){
//        JSONObject response = new JSONObject()
//        def data = marsListSubplanService.ListSubplan()
//
//        response.put("status", "success")
//        response.put("data", data)
//        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
//
//    }
//
//    @RequestMapping(value = "/createplan/showsubplan", method = RequestMethod.GET)
//    @ResponseBody
//    def showSubplan(HttpServletRequest request){
//        JSONObject response = new JSONObject()
//        String name = request.getParameter("name")
//        def result =  marsShowSubplanService.show(name)
//
//        response.put("status","success")
//        response.put("result",result)
//        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
//    }
//
//    @RequestMapping(value = "/plans/deletesubplan", method = RequestMethod.GET)
//    @ResponseBody
//    def delete(HttpServletRequest request) {
//        JSONObject response = new JSONObject()
//        def name = request.getParameter("name")
//
//        marsDeleteSubplanService.delete(name)
//        response.put("status", "success")
//        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
//    }
}
