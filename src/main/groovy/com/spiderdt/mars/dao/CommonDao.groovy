package com.spiderdt.mars.dao

import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository


@Repository
interface CommonDao {

    Object dateRange(@Param("data_source") data_source)
}
