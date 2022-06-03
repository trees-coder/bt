package com.wink.dao;

import com.wink.entity.RouteImg;

import java.util.List;

/**

 * @Description: TODO(旅游线路图片)
 */
public interface IRouteImgDao {

    //根据rid封装图片
    List<RouteImg> findByRid(int rid);
}
