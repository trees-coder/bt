package com.admin.service;

import com.wink.entity.Order;
import com.wink.entity.RouteOrder;

import java.util.List;

public interface IOrderService {
    List<Order> findAll(Integer page, Integer size, String search) throws Exception;

    void delete(int id) throws Exception;

    List<RouteOrder> findAllRoute(Integer page, Integer size, String search) throws Exception;

    void deleteRoute(Integer id)throws Exception;
}
