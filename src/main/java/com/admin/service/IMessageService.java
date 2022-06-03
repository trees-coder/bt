package com.admin.service;

import com.wink.entity.Message;

import java.util.List;

public interface IMessageService {
    List<Message> findAll(Integer page, Integer size, String search) throws Exception;

    void delete(int mid) throws Exception;

    void save(Message message)throws Exception;
}
