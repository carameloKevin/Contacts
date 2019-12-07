package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BuscarCumpleanos extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private String date = "";
    private DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cumpleanos);

        Button buttonDate= (Button) findViewById(R.id.setDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button buttonSearch = (Button) findViewById(R.id.btnSearchDate);
        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!date.equals(""))
                {
                    searchBirthday();
                }else{
                    Toast.makeText(BuscarCumpleanos.this, getString(R.string.dataInputError), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        date = df.format(c.getTime());

        //date = DateFormat.getDateInstance().format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.textViewSelectedDate);
        textView.setText(date);
    }

    public void searchBirthday(){
        String aux = date.substring(0,5);
        Toast.makeText(BuscarCumpleanos.this, aux, Toast.LENGTH_SHORT).show();


        ArrayList<Contacto> lista = db.getContactoByBirtday(aux);

        Intent intent = new Intent(BuscarCumpleanos.this, ListaContactos.class);
        intent.putExtra("listaContactos", lista);

        startActivity(intent);
    }

}
