package com.spiderdt.mars.dao

import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository


@Repository
interface SubplanDao {

    ArrayList<Object> queryLastMonthAvg(map)

    void createSubplan(@Param("data_source") data_source,
                       @Param("name") name,
                       @Param("user_id") user_id,
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
}
