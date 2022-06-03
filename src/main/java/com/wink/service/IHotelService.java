package com.wink.service;

import com.wink.entity.Hotel;
import com.wink.entity.PageBean;

/**

 * @Description: TODO(酒店业务层接口)
 */
public interface IHotelService {
    //分页查询所有酒店记录
    PageBean<Hotel> pageQuery( int currentPage, int pageSize);

    //查询一个酒店信息
    Hotel findOne(String hid);
}
