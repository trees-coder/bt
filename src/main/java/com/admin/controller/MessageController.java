package com.admin.controller;

import com.admin.service.IMessageService;
import com.admin.service.IUserService;
import com.github.pagehelper.PageInfo;
import com.wink.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**

 * @Description: TODO(留言信息)
 */
@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUserService userService;

    //新增留言
    @RequestMapping("/save.do")
    @PreAuthorize("hasAnyAuthority('/messages/save.do')")//houjia
    public String save(Message message)throws Exception{
        //从springsecurity获取登录名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        message.setUid(userService.findUidByUsername(username));
        messageService.save(message);
        return "redirect:findAll.do";
    }

    //删除留言
    @RequestMapping("/delete.do")
    @PreAuthorize("hasAnyAuthority('/messages/delete.do')")//houjia
    public String delete(Integer mid) throws Exception{
        messageService.delete(mid);
        return "redirect:findAll.do";
    }

    //查询所有留言信息
    @RequestMapping("/findAll.do")
    @PreAuthorize("hasAnyAuthority('/messages/findAll.do')")//houjia
    public ModelAndView findAll(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size,
            @RequestParam(name = "search",defaultValue = "") String search
    ) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Message> list = messageService.findAll(page,size,"%"+search+"%");
        PageInfo pageInfo = new PageInfo(list);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("message-list");
        return mv;
    }
}
