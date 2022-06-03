package com.wink.service;

import com.wink.entity.PageBean;
import com.wink.entity.Route;

/**

 * @Description: TODO(收藏排行榜业务层接口)
 */
public interface ITopService {

    //根据线路名和价格查询
    PageBean<Route> pageQuery(int currentPage,String rname,Double smoney,Double emoney,int pageSize);

}
