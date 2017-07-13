package com.spiderdt.mars.service

import com.spiderdt.mars.dao.CombinePlanDao
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.sql.Date

/**
 * Created by qiong on 2017/7/10.
 */
@Service
class CombinePlanService {
    @Autowired
    CombinePlanDao combinePlanDao

    def combinePlan(id) {
        return combinePlanDao.combinePlan(id)
    }

    def combinePlanResult(id) {
        return combinePlanDao.combinePlanResult(id).get(0)
    }

    def createBigPlan(name, user_name, start_time, end_time, status, subplan_id) {
        return combinePlanDao.createBigPlan(name, user_name, start_time, end_time, status, JsonOutput.toJson(subplan_id))
    }

    def deleteBigPlan(id) {
        return combinePlanDao.deleteBigPlan(id)
    }

    def updateBigPlan(id,name, user_name, start_time, end_time, subplan_id){
        return combinePlanDao.updateBigPlan(id,name, user_name, start_time, end_time, JsonOutput.toJson(subplan_id))
    }

}
