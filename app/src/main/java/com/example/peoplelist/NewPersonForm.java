package com.example.peoplelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPersonForm extends AppCompatActivity {

    Button btnOk, btnCancel;
    EditText etName, etAge, etPictureNumber;

    int positionToEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person_form);

        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPictureNumber = findViewById(R.id.etPictureNumber);

        Bundle incomingIntent = getIntent().getExtras();
        // capture incoming data
        if(incomingIntent != null) {
            String name = incomingIntent.getString("name");
            int age = Integer.parseInt(incomingIntent.getString("age"));
            int photoNumber = Integer.parseInt(incomingIntent.getString("pictureNumber"));
            positionToEdit = incomingIntent.getInt("edit");

            etName.setText(name);
            etAge.setText(Integer.toString(age));
            etPictureNumber.setText(Integer.toString(photoNumber));
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get strings from et view objects
                String newName = etName.getText().toString();
                String newAge = etAge.getText().toString();
                String newPictureNumber = etPictureNumber.getText().toString();

                // put strings into a  message for main activity
                Intent i = new Intent(v.getContext(), MainActivity.class);

                i.putExtra("positionToEdit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("age", newAge);
                i.putExtra("pictureNumber", newPictureNumber);

                // start main activity



                startActivity(i);


            }
        });



    }
}