package com.spiderdt.mars.dao

import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository


@Repository
interface SubplanDao {

    ArrayList<Object> queryLastMonthAvg(map)

    void createSubplan(@Param("name") name,
                       @Param("user_name") user_name,
                       @Param("category_1") category_1,
                       @Param("category_2") category_2,
                       @Param("product_name") product_name,
                       @Param("start_time") start_time,
                       @Param("end_time") end_time,
                       @Param("exec_status") exec_status,
                       @Param("discount") discount,
                       @Param("coupon") coupon,
                       @Param("effect_ln_baseprice") effect_ln_baseprice,
                       @Param("debut") debut,
                       @Param("status") status)

    void updateStatusTo0(@Param("name") name,
                         @Param("user_name") user_name)

    void updateStatusToDel(@Param("name") name,
                           @Param("user_name") user_name)

    void updateSubplanResult(@Param("name") name,
                             @Param("user_name") user_name,
                             @Param("result") result)

    ArrayList<Object> listSubplan(@Param("user_name") user_name)

    Object showSubplan(@Param("name") name,
                       @Param("user_name") user_name)
}
