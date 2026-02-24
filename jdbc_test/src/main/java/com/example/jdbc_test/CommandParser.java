package com.example.jdbc_test;

import java.util.Map;

public class CommandParser {
    
    private CommandParser(){};

    public static String GenerateInsertSQL(String tablename, Map<String,String> columns_values ){

        String sqlQuerry = String.format("INSERT INTO %s", tablename);
        String values = "", columns = ""; 
        for(Map.Entry<String,String> item : columns_values.entrySet()){
            String column = item.getKey();
            String value = item.getValue();
            values += values.isEmpty() ? value : String.format(", %s", value);
            columns += columns.isEmpty() ? column : String.format(", %s", column);
        }

        return String.format("%s %s VALUES %s", sqlQuerry, columns, values); 
    }

    //TODO
    public static String GenerateSelectSQL(){ throw new UnsupportedOperationException();}
    //TODO
    public static String GenerateUpdateSQL(){ throw new UnsupportedOperationException();}
    //TODO
    public static String GenerateDeleteSQL(){ throw new UnsupportedOperationException();}

}
