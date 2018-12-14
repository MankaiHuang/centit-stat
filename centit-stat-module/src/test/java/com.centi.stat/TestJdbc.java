package com.centi.stat;

import java.sql.*;

public class TestJdbc {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.128.32/frameworkdb?useUnicode=true&characterEncoding=utf-8", "framework", "framework");
            statement = connection.prepareStatement("select user_code, user_name from f_userinfo where user_code = ?");
            statement.setString(1, "20");
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                String userCode = resultSet.getString("user_code");
                System.out.printf("userCode:%s\n", userCode);
                String userName = resultSet.getString("user_name");
                System.out.printf("userName:%s\n", userName);
            }
        }catch (ClassNotFoundException e){
            System.out.println("类找不到");
        }catch (SQLException e){
            System.out.println("连接数据库出错");
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                System.out.println("关闭resultSet失败");
            }finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                }catch (SQLException e){
                    System.out.println("关闭statement失败");
                }finally {
                    try{
                        if(connection != null){
                            connection.close();
                        }
                    }catch (SQLException e){
                        System.out.println("关闭connection失败");
                    }
                }
            }
        }
    }
}
