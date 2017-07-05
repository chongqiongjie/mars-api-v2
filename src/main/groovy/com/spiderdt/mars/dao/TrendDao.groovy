package com.spiderdt.mars.dao

import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository


@Repository
interface TrendDao {

    ArrayList<Object> queryCategory(@Param("data_source") data_source)

    ArrayList<Object> queryAllProduct(@Param("data_source") data_source)

    ArrayList<Object> queryProductQuantity(map)

}
