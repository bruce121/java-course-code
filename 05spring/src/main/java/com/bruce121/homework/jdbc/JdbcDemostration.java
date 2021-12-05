package com.bruce121.homework.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName: JdbcDemostration
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/5/21 8:26 PM
 * @Version 1.0
 */
public class JdbcDemostration {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        JdbcDemostration demo = new JdbcDemostration();
        demo.run();
    }

    private void run() throws ClassNotFoundException, SQLException {

        // 加载驱动类，会自动执行静态代码块中的内容
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获得连接
        String url = "jdbc:mysql://localhost:3306/db-test";
        String user = "root";
        String password = "123456";
        Connection conn = null;
        // 原生获取数据连接
        // conn = DriverManager.getConnection(url, user, password);

        // 使用线程池获取连接
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setAutoCommit(true);
        dataSource.setConnectionTestQuery("select 1");
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("Demo-Hikari");

        conn = dataSource.getConnection();

        try {
            insert(conn);
            update(conn, "AA");
            delete(conn);
            batchOperate(conn);
            query(conn);
        } finally {
            conn.close();
        }
    }

    private void batchOperate(Connection conn) throws SQLException {
        try {
            // 关闭自动提交:
            conn.setAutoCommit(false);

            // 执行SQL语句:
            Statement stmt = conn.createStatement();
            // 执行sql 获得结果
            // 制造异常
            update(conn, "BB");
            int y = 1 / 0;
            insert(conn);
            // 提交事务:
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务:
            conn.rollback();
            System.out.println("Rollback~");
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private void delete(Connection conn) throws SQLException {
        // 执行SQL语句:
        Statement stmt = conn.createStatement();
        // 执行sql 获得结果
        String delete = "delete from student where age >= 14 ";
        stmt.execute(delete);
    }

    private void update(Connection conn, String name) throws SQLException {
        // 执行SQL语句:
        Statement stmt = conn.createStatement();
        // 执行sql 获得结果
        String update = "update student set name= '" + name + "' where id = 1";
        stmt.executeUpdate(update);
    }

    private void query(Connection conn) throws SQLException {
        // 执行SQL语句:
        Statement stmt = conn.createStatement();
        // 执行sql 获得结果
        String queryAll = "select * from student";
        ResultSet rs = stmt.executeQuery(queryAll);

        // 处理结果
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String age = rs.getString("age");
            System.out.println(id + ":::" + name + ":::" + age);
        }
    }

    private void insert(Connection conn) throws SQLException {
        // 执行SQL语句:
        Statement stmt = conn.createStatement();
        // 执行sql 获得结果
        String insert = "insert into  student(`name`, `age`) values('C', '14'),('D', '15')";
        stmt.execute(insert, Statement.RETURN_GENERATED_KEYS);
    }

}