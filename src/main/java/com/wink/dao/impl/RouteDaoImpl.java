package com.wink.dao.impl;

import com.wink.dao.IRouteDao;
import com.wink.entity.Route;
import com.wink.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**

 * @Description: TODO(旅游线路商品持久层实现类)
 */
public class RouteDaoImpl implements IRouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        String sql = "select count(1) from tab_route where rflag = 1 and 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() >0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();

        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql = "select * from tab_route where rflag = 1 and 1=1 ";
        StringBuffer sb = new StringBuffer(sql);
        List<Object> params = new ArrayList<>();
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() >0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");

        sql = sb.toString();
        params.add(start);
        params.add(pageSize);

        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class),params.toArray());
    }


    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);
    }

    @Override
    public List<Route> findTopRoute(int cid,int num) {
        String sql = "select * from tab_route where 1=1 ";
        //StringBuffer类是动态字符串数组
        StringBuffer sb = new StringBuffer(sql);
        List<Object> params = new ArrayList<>();
        if (cid != 0){
            //append（）⽅法相当于“+”，将指定的字符串追加到此字符序列
            sb.append(" and cid = ? ");
            //add方法可以给同一个key值传多个value，它会存在一个list里面
            params.add(cid);
        }
        sb.append(" order by count desc limit ? ");
        sql = sb.toString();
        params.add(num);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class),params.toArray());
    }

    @Override
    public List<Route> findNewRoute() {
        String sql = "select * from tab_route order by rdate limit 4";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> findThemeRoute() {
        String sql = "select * from tab_route where isThemeTour=1 limit 4";
        List<Route> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class));
        if (list.size() == 4){
            StringBuffer sb = new StringBuffer();
            int count = 1;
            for (Route r : list) {
                if (count == 4){
                    sb.append(r.getRid());
                }else{
                    sb.append(r.getRid()+",");
                }
                count++;
            }
            sql = " select * from tab_route where rid in ("+sb.toString()+") order by count desc";
        }
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class));
    }
}
