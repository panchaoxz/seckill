package org.seckill.dao;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.seckill.BaseTest;
import org.seckill.entity.Seckill;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class SeckillDaoTest extends BaseTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
    }


    @Test
    public void testReduceNumber() throws Exception {
//      Java没有保存形参的记录:QueryAll(int offset,int limit)->QueryAll(arg0,arg1);
//      因为java形参的问题,多个基本类型参数的时候需要用@Param("seckillId")注解区分开来
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }


    @Test
    public void testQueryAll() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount:  " + updateCount);
    }


    @Test
    public void testGetConnection() {

        String url = "jdbc:mysql://10.112.1.110:3306/test_mysql";
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String passwd = "111111";

        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.out.println("Get Connection failed!!!");
        }

        try {
            Connection con = DriverManager.getConnection(url, user, passwd);

            System.out.println("Get Connection Success!!!");

            Properties properties = con.getClientInfo();
            Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = it.next();
                System.out.println(entry.getKey() + "---------------" + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testComboPooledDataSource() throws Exception {
        String url = "jdbc:mysql://localhost:3306/seckill";
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String passwd = "nishengri";

        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setUrl(url);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(passwd);

        druidDataSource.setDriverClassName(driver);


        Connection con = druidDataSource.getConnection();

        System.out.println("Get Connection Success!!!   " + con);

    }
}

