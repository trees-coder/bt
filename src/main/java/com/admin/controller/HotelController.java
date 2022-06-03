package com.admin.controller;

import com.admin.service.IHotelService;
import com.admin.service.IUserService;
import com.admin.util.Upload;
import com.github.pagehelper.PageInfo;
import com.wink.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.admin.util.Upload.HIMAGE;

/**

 * @Description: TODO(酒店信息)
 */
@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IUserService iUserService;

    //根据hid查看已拥有的房型
    @RequestMapping("/findRooms.do")
    @PreAuthorize("hasAnyAuthority('/hotels/findRooms.do')")//houjia
    public ModelAndView findRooms(Integer hid)throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Hoteldetail> list = hotelService.findDetail(hid);
        mv.addObject("room",list);
        mv.setViewName("hotel-room-remove");
        return mv;
    }
    //移除已拥有的房型
    @RequestMapping("/removeRoom.do")
    @PreAuthorize("hasAnyAuthority('/hotels/removeRoom.do')")//houjia
    public String removeRoom(@RequestParam(name = "hid") Integer hid, @RequestParam(name = "ids") int[] ids)throws Exception{
        hotelService.removeRoom(hid,ids);
        return "redirect:findAll.do";
    }

    //根据hid查看未拥有的房型
    @RequestMapping("/findOtherRooms.do")
    @PreAuthorize("hasAnyAuthority('/hotels/findOtherRooms.do')")//houjia
    public ModelAndView findOtherRooms(Integer hid)throws Exception{
        ModelAndView mv = new ModelAndView();
        Hotel hotel = hotelService.findByHid(hid);
        mv.addObject("hotel",hotel);
        List<Hoteltype> list = hotelService.findOtherRooms(hid);
        mv.addObject("room",list);
        mv.setViewName("hotel-room-add");
        return mv;
    }
    //添加未拥有的房型
    @RequestMapping("/addRoom.do")
    @PreAuthorize("hasAnyAuthority('/hotels/addRoom.do')")//houjia
    public String addRoom(@RequestParam(name = "hid") Integer hid, @RequestParam(name = "ids") int[] ids,@RequestParam(name = "prices")String[] prices)throws Exception{
        List list = new ArrayList();
        for (int i=0;i<prices.length;i++) {
            if (prices[i]!=""){
               list.add(Double.valueOf(prices[i]));
            }
        }
        if (ids.length==list.size()){
            hotelService.addRoom(hid,ids,list);
       }
        return "redirect:findAll.do";
    }

    //查看酒店房型详情
    @RequestMapping("/findByUid.do")
    @PreAuthorize("hasAnyAuthority('/hotels/findByUid.do')")//houjia
    public ModelAndView findByUid(Integer hid)throws Exception{
        ModelAndView mv = new ModelAndView();
        Hotel hotel = hotelService.findByHid(hid);
        mv.addObject("hotel",hotel);
        List<Hoteldetail> hoteldetail = hotelService.findDetail(hid);
        mv.addObject("details",hoteldetail);
        mv.setViewName("hotel-detail");
        return mv;
    }

    //新增酒店
    @RequestMapping("/findUsers.do")
    @PreAuthorize("hasAnyAuthority('/hotels/findUsers.do')")//无-----houjia------
    public ModelAndView findUsers() throws Exception{
        List<User> list = hotelService.findUsers();
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",list);
        mv.setViewName("hotel-add");
        return mv;
    }
    @RequestMapping("/save.do")
    @PreAuthorize("hasAnyAuthority('/hotels/save.do')")//houjia
    public String save(Hotel hotel) throws Exception{
        if (hotel.getHimageFile()!=null){
            String filename = Upload.uploadImg(HIMAGE,hotel.getHimageFile());
            hotel.setHimage("img/hotel/"+filename);
            hotelService.save(hotel);
        }
        return "redirect:findAll.do";
    }

    //删除酒店
    @RequestMapping("/delete.do")
    @PreAuthorize("hasAnyAuthority('/hotels/delete.do')")//houjia
    public String delete(Integer hid) throws Exception {
        hotelService.delete(hid);
        return "redirect:findAll.do";
    }

    //修改酒店信息
    @RequestMapping("/findU.do")
    @PreAuthorize("hasAnyAuthority('/hotels/findAll.do')")//houjia
    public ModelAndView findU(Integer hid) throws Exception{
        ModelAndView mv = new ModelAndView();
        Hotel hotel = hotelService.findByHid(hid);
        mv.addObject("hotel",hotel);
        List<User> list = iUserService.findAll(1,100,"%%");
        mv.addObject("UList",list);
        mv.setViewName("hotel-update");
        return mv;
    }
    //-houjia 修改酒店信息
    @RequestMapping("/update.do")
    @PreAuthorize("hasAnyAuthority('/hotels/update.do')")//houjia
    public String update(Hotel hotel) throws Exception{
        if (hotel.getHimageFile().getSize()!=0&&hotel.getHimageFile()!=null){//修改过图片
            String filename = Upload.uploadImg(HIMAGE,hotel.getHimageFile());
            hotel.setHimage("img/hotel/"+filename);
            hotelService.update(hotel);
        }else{//未修改图片
            hotelService.update(hotel);
        }
        return "redirect:findAll.do";
    }

    //查询所有订单
    @RequestMapping("/findAll.do")
    @PreAuthorize("hasAnyAuthority('/hotels/findAll.do')")//houjia
    public ModelAndView findAll(
            @RequestParam(name = "page",defaultValue = "1")Integer page,
            @RequestParam(name = "size",defaultValue = "10")Integer size,
            @RequestParam(name = "search",defaultValue = "")String search
    ) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Hotel> list = hotelService.findAll(page,size,"%"+search+"%");
        PageInfo pageInfo = new PageInfo(list);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("hotel-list");
        return mv;
    }
}
