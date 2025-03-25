package me.dunescifye.practicehubcore.utils;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

public class Database {
    

    public static DataSource createDataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:database.db");
        return dataSource;
    }

}
