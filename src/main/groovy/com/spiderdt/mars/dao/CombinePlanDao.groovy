package com.spiderdt.mars.dao

import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

import java.sql.Date

/**
 * Created by qiong on 2017/7/10.
 */
@Repository
interface CombinePlanDao {
    void createBigPlan(@Param("name") String name, @Param("user_name") String user_name, @Param("start_time") String start_time, @Param("end_time") String end_time,  @Param("status") String status, @Param("subplan_id") String subplan_id)
    ArrayList<Object> combinePlan(@Param("id") int id)
    ArrayList<Object> combinePlanResult(@Param("id") int id)
    void deleteBigPlan(@Param("id") int id)
}
