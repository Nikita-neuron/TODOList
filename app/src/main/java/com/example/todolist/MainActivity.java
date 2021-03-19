package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button save;
    RecyclerView recyclerView;

    EditText nameInput;
    EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = findViewById(R.id.save);
        recyclerView = findViewById(R.id.recycleView);

        nameInput = findViewById(R.id.textInputName);
        textInput = findViewById(R.id.textInputNText);

        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.save) {
            String name = nameInput.getText().toString();
            String text = textInput.getText().toString();

        }

    }
}