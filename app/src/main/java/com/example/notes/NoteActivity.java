package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;

public class NoteActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Get information from mainActivity
        Intent intent = getIntent();
        int index = intent.getIntExtra("numItem", 0);
        String noteString = MainActivity.notes.get(index);

        //Build up EditText View
        editText = findViewById(R.id.note);
        editText.setText(noteString);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Update storage if text is edited
                MainActivity.notes.set(index, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                try {
                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}