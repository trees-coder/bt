package com.wink.dao;

import com.wink.entity.Hoteltype;

/**

 * @Description: TODO(房型持久层接口)
 */
public interface IHoteltypeDao {

    //查询房型分类
    Hoteltype findByTid(int tid);
}
