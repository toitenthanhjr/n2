package com.example.computermanagement_330;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MenuList extends AppCompatActivity {
    private ComputerAdapter adapter;
    private List<Computer> list;
    private ListView listView;
    private String computerId;

    Button button_edit, button_insert,button_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_computer);

        button_delete = (Button) findViewById(R.id.delete_computer);
        button_edit = (Button)findViewById(R.id.edit_computer);
        button_insert = (Button)findViewById(R.id.new_computer);

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectUpdate();
            }
        });

        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectInsert();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectDelete();
            }
        });

        listView = (ListView) findViewById(R.id.lvComputer);
        ComputerDAO dao = new ComputerDAO(this);
        list = dao.getAllComputer();
        adapter = new ComputerAdapter(this,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Computer cmp = list.get(i);
                computerId = cmp.getId();
                int n;
                    for (n = 0; n < listView.getChildCount(); n++) {
                        if(i == n ){
                            listView.getChildAt(n).setBackgroundColor(Color.BLUE);
                        }else{
                            listView.getChildAt(n).setBackgroundColor(Color.TRANSPARENT);
                        }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ComputerDAO dao = new ComputerDAO(this);
        List<Computer> updateList = dao.getAllComputer();
        list.clear();
        for (Computer item : updateList) {
            list.add(item);
        }
        adapter.notifyDataSetChanged();
    }
    public void redirectInsert(){
        Intent intent = new Intent(MenuList.this, DetailActivity.class);
        startActivity(intent);
    }

    public void redirectUpdate(){
        Intent intent = new Intent(MenuList.this, DetailActivity.class);
        if(computerId == null) {
            Toast.makeText(this, "Computer id must be selected", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("id", computerId);
        bundle.putString("key","update");
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    public void redirectDelete(){
        Intent intent = new Intent(MenuList.this, DetailActivity.class);
        if(computerId == null) {
            Toast.makeText(this, "Computer id must be selected", Toast.LENGTH_SHORT).show();
            return;
        }
        ComputerDAO dao  = new ComputerDAO(this);
        dao.delete(computerId);
        computerId = null;
        onResume();
        Toast.makeText(this, "Computer was delete", Toast.LENGTH_SHORT).show();

    }
}