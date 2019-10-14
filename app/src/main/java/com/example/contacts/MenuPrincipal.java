package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity {

    public final DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


    }

    public void createNewContact(View view)
    {
        Intent intent = new Intent(this, CreateContact.class);
        startActivity(intent);
    }

    public void listContacts(View view)
    {
        Intent intent = new Intent(this, ListaContactos.class);

        ArrayList<Contacto> lista = db.getAllContactos();
        intent.putExtra("listaContactos", lista);

        startActivity(intent);
    }


    public void buscarCumpleanos(View view)
    {
        Intent intent = new Intent(this, BuscarCumpleanos.class);
        startActivity(intent);
    }


}
