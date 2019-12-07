package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;
import static android.provider.Telephony.Mms.Part.TEXT;

public class DBHandler extends SQLiteOpenHelper{
        //Database Version
        private static final int DATABASE_VERSION = 1;
        //Nombre de la database
        private static final String DATABASE_NAME = "contactInfo";
        //El nombre de la tabla
        private static final String TABLE_CONTACTS = "contacts";
        //Nombre de las columnas de la tabla
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_PHONE_NUMBER = "phoneNumber";
        private static final String KEY_CALENDAR ="birtdhay";


        public DBHandler(Context context)
        {
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
            + KEY_PHONE_NUMBER + " TEXT," +  KEY_CALENDAR + " TEXT" + ")";

            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            //Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            //Creating tables again
            onCreate(db);
        }

        //Adding new Contact
        public void addContacto(Contacto contacto){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contacto.getNombre());
            values.put(KEY_PHONE_NUMBER, contacto.getNumero());
            values.put(KEY_CALENDAR, contacto.getCumple());

            //Insert row
            db.insert(TABLE_CONTACTS,"id", values);
            db.close();
        }

        //Getting one shop
        public Contacto getContactoById(int id)
        {
            Contacto contacto;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                            KEY_NAME, KEY_PHONE_NUMBER, KEY_CALENDAR}, KEY_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
            }
            contacto = new Contacto(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.close();
            return contacto;
        }

    public ArrayList<Contacto> getContactoByBirtday(String date)
    {
        ArrayList<Contacto> listaContactos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        /*Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PHONE_NUMBER, KEY_CALENDAR}, KEY_CALENDAR + "=?",
                new String[] { date }, null, null, null, null);
        */

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PHONE_NUMBER, KEY_CALENDAR}, KEY_CALENDAR + " LIKE " + "\'"+ date+"%"+"\'",
                null, null, null, null, null);

        try {
            if (cursor.moveToFirst())
            {
                while(!cursor.isAfterLast())
                {
                    Contacto contact = new Contacto(Integer.parseInt(cursor.getString(  0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                    listaContactos.add(contact);

                    cursor.moveToNext();
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        db.close();

        return listaContactos;



    }

        public ArrayList<Contacto> getAllContactos() {
            ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();
            //select all query
            String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            //looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Contacto contacto = new Contacto(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                    //adding contactos to list
                    listaContactos.add(contacto);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return listaContactos;
            }


            public int getContactosCount(){
            //getting contacts Count
            String countQuery = "Select * FROM " + TABLE_CONTACTS;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();

            return count;
        }

        public int updateContacto(Contacto contacto)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contacto.getNombre());
            values.put(KEY_PHONE_NUMBER, contacto.getNumero());

            return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] {String.valueOf(contacto.getId())});
        }

        public void deleteContacto(Contacto contacto)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contacto.getId())});
            db.close();
        }

}
