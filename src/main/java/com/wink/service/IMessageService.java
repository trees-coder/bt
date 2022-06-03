package com.wink.service;

import com.wink.entity.Message;
import com.wink.entity.PageBean;


/**

 * @Description: TODO(留言业务层接口)
 */
public interface IMessageService {

    //保存留言
    void saveMessage(Message message);

    //分页查询留言
    PageBean<Message> findByPage(int currentPage, int pageSize);
}
