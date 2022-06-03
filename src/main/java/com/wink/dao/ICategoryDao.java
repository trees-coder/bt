package com.wink.dao;

import com.wink.entity.Category;

import java.util.List;

/**

 * @Description: TODO(分类持久层接口)
 */
public interface ICategoryDao {

    //查询所有
    List<Category> findAll();
}
