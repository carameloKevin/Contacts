package com.example.contacts;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateContact extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DBHandler dbHandler = new DBHandler(this);

    String name, phone, date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        Button buttonDate= (Button) findViewById(R.id.birthdayBtn);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button buttonSubmit = (Button) findViewById(R.id.sendBtn);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if ( !(((TextView) findViewById(R.id.nameContact)).getText().toString().equals("")) &&  !(((TextView) findViewById(R.id.phoneContact)).getText().toString().equals("")) && !date.equals(""))
                {
                    createContact(v);
                }else{
                    Toast.makeText(CreateContact.this, getString(R.string.dataInputError) ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.textViewDate);
        textView.setText(date);
    }

    public void createContact(View view)
    {

        //Tomo el nombre
        TextView editText = (TextView) findViewById(R.id.nameContact);
        name = editText.getText().toString();

        //tomo el numero que escribio
        editText = (TextView) findViewById(R.id.phoneContact);
        phone = editText.getText().toString();

        Contacto contacto = new Contacto(dbHandler.getContactosCount(), name, phone, date);

        dbHandler.addContacto(contacto);
        super.onBackPressed();
    }

}
