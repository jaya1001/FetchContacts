package com.example.android.tasksecond;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        ArrayList<String> numbers;

        Observable myObservable = Observable.from(contacts);

        writeCsv(contacts);


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
    public void writeCsv(ArrayList<String> contacts)
    {
        String [] contact = contacts.toArray(new String[contacts.size()]);
        CSVWriter writer = null;
        try
        {
            writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath()+"/contactsFile.csv"), ',');
            writer.writeNext(contact);
            writer.close();
        }
        catch (IOException e)
        {
            //error

        }
    }
}