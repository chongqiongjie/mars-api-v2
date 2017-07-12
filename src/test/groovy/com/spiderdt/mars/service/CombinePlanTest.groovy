package com.spiderdt.mars.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import java.sql.Date

/**
 * Created by qiong on 2017/7/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@WebAppConfiguration
class CombinePlanTest {
    @Autowired
    CombinePlanService combinePlanService

    @Test
    void combinePlan(){
        def id = 2
        println combinePlanService.combinePlan(id)
    }

    @Test
    void combinePlanResult(){
        def id = 1
        println combinePlanService.combinePlanResult(id)
    }

    @Test
    void createBigPlan(){
        String name = "q"
        String user_name = "q"
        def start_time = "2017-01-01"
        def end_time = "2017-01-10"
        String status = "success"
        List subplan_id_list = [1,2]
        String subplan_id = subplan_id_list.toListString()
        println combinePlanService.createBigPlan(name,user_name,start_time,end_time,status,subplan_id)
    }


    @Test
    void deleteBigPlan(){
        int id = 1
        println combinePlanService.deleteBigPlan(id)
    }
}
