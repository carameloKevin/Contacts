package com.example.contacts;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ListaContactos extends ListActivity {

    private static final int REQUEST_CALL = 1;
    private ArrayList<Contacto> lista;
    private ArrayAdapter<Contacto> adaptor;
    private final DBHandler db = new DBHandler(this);
    private ListView lv;
    private Contacto aux;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        lv = getListView();

        lista = (ArrayList<Contacto>)getIntent().getSerializableExtra("listaContactos");
        adaptor = new ArrayAdapter<>(this, R.layout.list_item, lista);

        setListAdapter(adaptor);
        adaptor.notifyDataSetChanged();

        registerForContextMenu(lv);
        if(lista.size() == 0)
        {
            Toast.makeText(ListaContactos.this,getString(R.string.noContacts),Toast.LENGTH_LONG).show();
        }

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        aux = (Contacto) lv.getItemAtPosition(acmi.position);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.submenu, menu);

        menu.setHeaderTitle(getString(R.string.selectAction));
    }

    public boolean onContextItemSelected(MenuItem item){
        Boolean valorRet = true;
        switch (item.getItemId()){


        case(R.id.deleteContact):
            Toast.makeText(getApplicationContext(),"Deleting", Toast.LENGTH_LONG).show();
            db.deleteContacto(aux);
            adaptor.remove(aux);
            adaptor.notifyDataSetChanged();
        break;

        case(R.id.call):
            Toast.makeText(getApplicationContext(),"Calling",Toast.LENGTH_LONG).show();
            makePhoneCall();
        break;

        default:
                valorRet = false;
        }

        //Retorna true para que no se siga propaganda para activities de mas arriba
        return valorRet;
    }


    private void makePhoneCall()
    {
        String number = aux.getNumero();
        if(number.trim().length() > 0)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(this, "Phone number missing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
