package cl.enexo.dearsoccer.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 17-01-2017.
 */

public class Bd extends SQLiteOpenHelper {
    String sqlCreate1 = " CREATE TABLE IF NOT EXISTS Configuration ( " +
            "COUNTRY	TEXT, " +
            "CITY TEXT, " +
            "LOCALITY TEXT, " +
            "YEAR TEXT, " +
            "MONTH TEXT, " +
            "DAY TEXT, " +
            "YEARFIN TEXT, " +
            "MONTHFIN TEXT, " +
            "DAYFIN TEXT " +
            "); " ;

    String sqlCreate2 = " CREATE TABLE IF NOT EXISTS COUNTRY ( " +
            "VALUE	TEXT " +
            "); " ;

    String sqlCreate3 = " CREATE TABLE IF NOT EXISTS CITY ( " +
            "KEY	TEXT,  " +
            "VALUE	TEXT " +
            "); " ;

    String sqlCreate4 = " CREATE TABLE IF NOT EXISTS LOCALITY ( " +
            "KEY	TEXT,  " +
            "VALUE	TEXT " +
            "); " ;


    public Bd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS Configuration");
        db.execSQL("DROP TABLE IF EXISTS COUNTRY");
        db.execSQL("DROP TABLE IF EXISTS CITY");
        db.execSQL("DROP TABLE IF EXISTS LOCALITY");
    }

    public void createTables(SQLiteDatabase db) {
        db.execSQL(sqlCreate1);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        db.execSQL(sqlCreate4);
    }

    public void addCountry(SQLiteDatabase db,String country)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM COUNTRY where VALUE = '" + country + "'" , null);
        cursor.moveToFirst();
        int cantidadcursor  = cursor.getCount();
        cursor.close();

        if (cantidadcursor == 0)
        {
            db.execSQL("Insert into COUNTRY (value) " +
                    "values('" + country + "')");
        }
    }

    public void addCity(SQLiteDatabase db,String country,String city)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM CITY where KEY = '" + country + "' AND VALUE = '" + city + "'" , null);
        cursor.moveToFirst();
        int cantidadcursor  = cursor.getCount();
        cursor.close();

        if (cantidadcursor == 0) {
            db.execSQL("Insert into CITY (key,value) " +
                    "values('" + country + "','" + city + "')");
        }
    }

    public void addLocality(SQLiteDatabase db,String city,String locality)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM LOCALITY where KEY = '" + city + "' AND VALUE = '" + locality + "'" , null);
        cursor.moveToFirst();
        int cantidadcursor  = cursor.getCount();
        cursor.close();

        if (cantidadcursor == 0) {
            db.execSQL("Insert into LOCALITY (key,value) " +
                    "values('" + city + "','" + locality + "')");
        }
    }

    public void addConfiguration(SQLiteDatabase db,String country,String city,String locality,String date,String datef)
    {
        String[] arrayaDate = date.split("-");
        String year = arrayaDate[0];
        String month = arrayaDate[1];
        String day = arrayaDate[2];
        String[] arrayaDatef = datef.split("-");
        String yearfin = arrayaDatef[0];
        String monthfin = arrayaDatef[1];
        String dayfin = arrayaDatef[2];
        deleteTables(db);
        createTables(db);
        db.execSQL("Insert into CONFIGURATION (COUNTRY,CITY,LOCALITY,YEAR,MONTH,DAY,YEARFIN,MONTHFIN,DAYFIN) " +
                "values('" + country + "','" + city +"','" + locality + "','" + year + "','" + month + "','" + day + "','" + yearfin + "','" + monthfin + "','" + dayfin + "')");
    }

    public List<String> getCountrys(SQLiteDatabase bd)  {
        List<String> list =new ArrayList<String>();
        try {
        SQLiteDatabase db = bd;
        Cursor cursor = db.rawQuery("SELECT DISTINCT value FROM COUNTRY ORDER BY value ASC", null);
        cursor.moveToFirst();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            String value = cursor.getString(0);
            list.add(new String(value));
        }
        cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
            return list;
        }
        return list;
    }


    public List<String> getCities(SQLiteDatabase bd,String country)  {
        List<String> list =new ArrayList<String>();
        try {
            SQLiteDatabase db = bd;
            Cursor cursor = db.rawQuery("SELECT DISTINCT value FROM CITY WHERE key = '" + country + "' ORDER BY value ASC", null);
            cursor.moveToFirst();

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                String value = cursor.getString(0);
                list.add(new String(value));
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
            return list;
        }
        return list;
    }

    public List<String> getLocalities(SQLiteDatabase bd,String city)  {
        List<String> list =new ArrayList<String>();
        try {
            SQLiteDatabase db = bd;
            Cursor cursor = db.rawQuery("SELECT DISTINCT value FROM LOCALITY WHERE key = '" + city + "' ORDER BY value ASC", null);
            cursor.moveToFirst();

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                String value = cursor.getString(0);
                list.add(new String(value));
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
            return list;
        }
        return list;
    }

    public String getDateSelected(SQLiteDatabase bd)  {
        String date = "";
        String y = "";
        String m = "";
        String d = "";
        try {
            SQLiteDatabase db = bd;
            Cursor cursor = db.rawQuery("SELECT YEAR,MONTH,DAY  FROM CONFIGURATION", null);
            cursor.moveToFirst();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                y = cursor.getString(0);
                m = cursor.getString(1);
                d = cursor.getString(2);
                date = y + "-" + m + "-" + d;
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
        return date;
    }

    public String getDateFinSelected(SQLiteDatabase bd)  {
        String date = "";
        String y = "";
        String m = "";
        String d = "";
        try {
            SQLiteDatabase db = bd;
            Cursor cursor = db.rawQuery("SELECT YEARFIN,MONTHFIN,DAYFIN  FROM CONFIGURATION", null);
            cursor.moveToFirst();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                y = cursor.getString(0);
                m = cursor.getString(1);
                d = cursor.getString(2);
                date = y + "-" + m + "-" + d;
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
        return date;
    }

    public String getCountrySelected(SQLiteDatabase bd)  {
        String country = "";

        try {
            SQLiteDatabase db = bd;
            Cursor cursor = db.rawQuery("SELECT country  FROM CONFIGURATION", null);
            cursor.moveToFirst();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                country = cursor.getString(0);
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
        return country;
    }


    public String getCitySelected(SQLiteDatabase bd)  {
        String city = "";
        try {
            SQLiteDatabase db = bd;
            Cursor cursor = db.rawQuery("SELECT city  FROM CONFIGURATION", null);
            cursor.moveToFirst();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                city = cursor.getString(0);
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
        return city;
    }

    public String getLocalitySelected(SQLiteDatabase bd)  {
        String locality = "";
        try {
            SQLiteDatabase db = bd;
            Cursor cursor = db.rawQuery("SELECT locality  FROM CONFIGURATION", null);
            cursor.moveToFirst();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                locality = cursor.getString(0);
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
        return locality;
    }
}
