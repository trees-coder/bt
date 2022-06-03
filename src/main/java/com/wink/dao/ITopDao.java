package com.wink.dao;

import com.wink.entity.Route;

import java.util.List;

/**

 * @Description: TODO(收藏排行榜持久层接口)
 */
public interface ITopDao {

    //查询总记录数
    int findTotalCount(String rname,Double smoney,Double emoney);

    //分页查询
    List<Route> findByPage(int start,String rname,Double smoney,Double emoney,int pageSize);

}
