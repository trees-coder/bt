package com.wink.service;

import com.wink.entity.Category;

import java.util.List;

/**

 * @Description: TODO(分类业务层接口)
 */
public interface ICategoryService {

    //查询所有
    List<Category> findAll();
}
