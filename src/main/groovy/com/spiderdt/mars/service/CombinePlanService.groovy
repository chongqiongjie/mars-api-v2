package com.spiderdt.mars.service

import com.spiderdt.mars.dao.CombinePlanDao
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

    def combinePlan(int id) {
        return combinePlanDao.combinePlan(id)
    }

    def combinePlanResult(int id) {
        return combinePlanDao.combinePlanResult(id).get(0)
    }

    def createBigPlan(String name, String user_name, String start_time, String end_time, String status, String subplan_id) {
        return combinePlanDao.createBigPlan(name, user_name, start_time, end_time, status, subplan_id)
    }

    def deleteBigPlan(int id){
        return combinePlanDao.deleteBigPlan(id)
    }


}
