package com.example.peoplelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnSortABC, btnSortAge;

    ListView lvFriendsList;

    PersonAdapter adapter;
    MyFriends myFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnSortABC = findViewById(R.id.btnSortABC);
        btnSortAge = findViewById(R.id.btnSortAge);
        lvFriendsList = findViewById(R.id.lvFriendsList);

        myFriends = new MyFriends();

        adapter = new PersonAdapter(MainActivity.this, myFriends);

        lvFriendsList.setAdapter(adapter);

        // Listen for incoming messages
        Bundle incomingMessages = getIntent().getExtras();
        // capture incoming data
        if(incomingMessages != null) {
            String name = incomingMessages.getString("name");
            int age = Integer.parseInt(incomingMessages.getString("age"));
            int photoNumber = Integer.parseInt(incomingMessages.getString("pictureNumber"));
            int positionEdited = incomingMessages.getInt("edit");

                    // create a new person object
            Person p = new Person(name, age, photoNumber);
            // add person to the list
            if (positionEdited > -1) {
                myFriends.getMyFriendsList().remove(positionEdited);
            }
            myFriends.getMyFriendsList().add(p);
            adapter.notifyDataSetChanged();
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewPersonForm.class);
                startActivity(i);
            }
        });

        btnSortABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(myFriends.getMyFriendsList());
                adapter.notifyDataSetChanged();
            }
        });

        btnSortAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.sort(myFriends.getMyFriendsList(), new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.getAge() - o2.getAge();
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        lvFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPerson(position);
            }
        });




    }

    private void editPerson(int position) {
        Intent i = new Intent(getApplicationContext(), NewPersonForm.class);

        // Get contents of person that was clicked
        Person p = myFriends.getMyFriendsList().get(position);

        i.putExtra("edit", position);
        i.putExtra("name", p.getName());
        i.putExtra("age", Integer.toString(p.getAge()));
        i.putExtra("pictureNumber", Integer.toString(p.getPictureNumber()));

        startActivity(i);
    }
}