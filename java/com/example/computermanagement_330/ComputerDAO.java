package com.example.computermanagement_330;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ComputerDAO  {
    private SQLiteDatabase db;

    public ComputerDAO() {
    }

    public ComputerDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Computer> get(String sql, String ...selectArgs){
        List<Computer> list  = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);

        while(cursor.moveToNext()){
           Computer cp = new Computer();
            cp.setId(cursor.getString((cursor.getColumnIndex("id"))));
            cp.setName(cursor.getString((cursor.getColumnIndex("name"))));
            cp.setCategory(cursor.getString((cursor.getColumnIndex("categoryId"))));

            list.add(cp);

        }
        return list;
    }

    @SuppressLint("Range")
    public List<Category> getName(String sql, String ...selectArgs){
        List<Category> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while(cursor.moveToNext()){
            Category cp = new Category();
            cp.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cp.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(cp);
        }
        return list;
    }

    public List<Computer> getAllComputer(){
        String sql = "select * from InfComputer";
        return get(sql);
    }

    public List<Category> getAllCategoryName(){
        String sql = "select * from Category";
        return getName(sql);
    }


    public Computer getById(String id){
        String sql = "select * from InfComputer where id = ?";
        List<Computer> list = get(sql,id);
        return list.get(0);
    }

    public long insert(Computer cm){
        ContentValues values = new ContentValues();
        values.put("id",cm.getId());
        values.put("name",cm.getName());
        values.put("categoryId",cm.getCategory());

        return db.insert("InfComputer", null, values);
    }

    public long update(Computer cm){
        ContentValues values = new ContentValues();
        values.put("name",cm.getName());
        values.put("categoryId",cm.getCategory());
        return db.update("InfComputer", values, "id = ?",new String[]{cm.getId()});
    }

    public int delete(String id){
        return db.delete("InfComputer","id = ?",new String[]{id});
    }
}
