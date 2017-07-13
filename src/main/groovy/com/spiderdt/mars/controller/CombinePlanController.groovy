package com.spiderdt.mars.controller

import com.spiderdt.mars.service.CombinePlanService
import com.spiderdt.mars.util.Slog
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
 *  Created by kun on 2017/7/3.
 */
@Controller
class CombinePlanController {
    @Autowired
    CombinePlanService combinePlanService
    @Autowired
    Slog slog

    @RequestMapping(value = "/createplan/combinesubplan", method = RequestMethod.POST)
    @ResponseBody
    def Combine(@RequestBody Map<String, Object> params) {
        JSONObject response = new JSONObject()
        try {
            def name = params.get("name")
            def user_name = "tutuanna"
            def start_time = params.get("start_time")
            def end_time = params.get("end_time")
            def status = params.get("status")
            slog.info("status:" + status)
            def subplan_id = params.get("subplan_id")
            slog.info("subplan_id++:" + subplan_id)
            combinePlanService.createBigPlan(name, user_name, start_time, end_time, status, subplan_id)

            response = [status: "success"]
        } catch (Exception e) {
            response = [status : "failure",
                        message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
    }


    @RequestMapping(value = "/plans/showallplans", method = RequestMethod.GET)
    @ResponseBody
    def combinePlan(HttpServletRequest request) {
        JSONObject response = new JSONObject()
        try {
            def id = request.getParameter("id")
            int id1 = Integer.parseInt(id)
            slog.info("id+++:" + id)
            slog.info("id.class+++:" + id.class)
            slog.info("id1++:" + id1)
            slog.info("id1.class++:" + id1.class)
            def res = combinePlanService.combinePlan(id1)

            response = [status: "success",
                        data  : res]
        } catch (Exception e) {
            response = [status : "failure",
                        message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
    }

    @RequestMapping(value = "/plans/showbigplans", method = RequestMethod.GET)
    @ResponseBody
    def combinePlanResult(HttpServletRequest request) {
        JSONObject response = new JSONObject()
        try {
            def id = request.getParameter("id")
            int id1 = Integer.parseInt(id)

            def data = combinePlanService.combinePlanResult(id1)

            response = [status     : "success",
                        bigplan_res: data]
        } catch (Exception e) {
            response = [status : "failure",
                        message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
    }


    @RequestMapping(value = "/plans/deletebigplans", method = RequestMethod.GET)
    @ResponseBody
    def deleteBigPlan(HttpServletRequest request) {
        JSONObject response = new JSONObject()
        try {
            def id = request.getParameter("id")
            int id1 = Integer.parseInt(id)

            combinePlanService.deleteBigPlan(id1)

            response = [status: "success"]
        } catch (Exception e) {
            response = [status : "failure",
                        message: e.message]
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.toString())
    }


}
