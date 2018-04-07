package com.example.android.tasksecond;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by User on 4/6/2018.
 */

public class FetchContact extends AppCompatActivity {

    private String name, phonenumber ;
    private ArrayList<String> contacts;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private Intent i;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        i = getIntent();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        GetContactsIntoArrayList();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);

        Observable myObservable = Observable.from(contacts);


    }

    public void GetContactsIntoArrayList() {

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        contacts = new ArrayList<String>();

        if (cursor == null) {
        } else {
            while (cursor.moveToNext()) {

                name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(name+"-->"+phonenumber);

            }
            cursor.close();
        }
    }
}