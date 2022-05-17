package Test;
import javax.swing.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class DbUtil extends JFrame {
    //public static final String URL = "jdbc:mysql://localhost:3306/test";

    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";//数据库驱动类所对应的字符串

        String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
        //URL语法格式如下
        //jdbc:mysql:是固定的写法，后面跟主机名localhost，3306是默认的MySQL端口号
        //serverTimezone=UTC是指定时区时间为世界统一时间
        //useUnicode=true是指是否使用Unicode字符集，赋值为true
        //characterEncoding=utf-8是指定字符编码格式为UTF8
        Connection conn = null;
        //Connection接口代表Java程序和数据库的连接对象，只有获得该连接对象后，才能访问数据库，并操作数据表
        try {
            Class.forName(driver);//加载MySQL数据库驱动
        } catch (java.lang.ClassNotFoundException e) {//如果找不到这个类，执行下面的异常处理
            System.out.println("驱动程序配置未配置成功!!!");
        }
        try {
            conn = DriverManager.getConnection(URL, "zsr", "123456");//建立和数据库的连接，并返回表示连接的Connection对象
            System.out.println("数据库连接成功");
        } catch (Exception e) {//未连接成功，执行下面的异常处理
            System.out.println("数据库连接失败!!!");
        }
    }
}

