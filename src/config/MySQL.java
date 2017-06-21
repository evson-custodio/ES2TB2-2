/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author evson
 */
public interface MySQL {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/escolar?zeroDateTimeBehavior=convertToNull";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";
}
