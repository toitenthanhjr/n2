package com.example.computermanagement_330;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    EditText ed_id, ed_name;
    Spinner ed_category;
    Button btnSave, btnBack;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_items);

        btnBack = (Button) findViewById(R.id.button_back_list);
        ed_id = findViewById(R.id.new_ID);
        ed_name = findViewById(R.id.new_name);
        ed_category = findViewById(R.id.new_category);

        btnSave = (Button) findViewById(R.id.button_save_new);
        findViewById(R.id.button_save_new).setOnClickListener(this);
        findViewById(R.id.button_back_list).setOnClickListener(this);
        ;
        readComputer();

        ComputerDAO daoCater = new ComputerDAO(this);
        List<Category> ctr = daoCater.getAllCategoryName();
        List<String> loc = new ArrayList<>();
        for (Category loc1 : ctr) {
            loc.add(loc1.getName());
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, loc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ed_category.setAdapter(arrayAdapter);
    }
    private void readComputer(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        if(bundle == null)
            return;

        String id  = bundle.getString("id");
        String update = bundle.getString("key");
        ComputerDAO dao = new ComputerDAO(this);
        Computer ct = dao.getById(id);

        ed_id.setText(ct.getId());
        ed_name.setText(ct.getName());
        ed_id.setEnabled(false);
        btnSave.setText("UPDATE");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_save_new:
                    ComputerDAO dao = new ComputerDAO(this);
                    Computer computer = new Computer();
                    computer.setId(ed_id.getText().toString());
                    computer.setName(ed_name.getText().toString());
                    String Text = ed_category.getSelectedItem().toString();
                    computer.setCategory(Text);
                    if (btnSave.getText().equals("UPDATE"))
                        dao.update(computer);
                    else
                        dao.insert(computer);
                    Toast.makeText(this, "New employee has been save", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailActivity.this, MenuList.class);
                    startActivity(intent);
                    break;
            case R.id.button_back_list:
                Intent intent2 = new Intent(DetailActivity.this, MenuList.class);
                startActivity(intent2);
        }
    }
}