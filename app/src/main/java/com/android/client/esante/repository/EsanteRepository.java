package com.android.client.esante.repository;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.client.esante.domain.Docteur;
import com.android.client.esante.domain.Patient;
public class EsanteRepository extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Esante";
    private static final String TABLE_USER = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE = "role";
    public EsanteRepository(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_USER + " ("
                + KEY_ID + " INTEGER PRIMARY KEY ,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_ROLE + " TEXT"
                +")";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS  "+TABLE_USER);
        onCreate(db);
    }
    public void addUser(Object object){
        SQLiteDatabase db = this.getWritableDatabase();
        dropTable();
        ContentValues values = new ContentValues();
        if(object instanceof Patient){
            Patient pat=(Patient) object;
            values.put(KEY_ID,pat.getIdPatient());
            values.put(KEY_FIRST_NAME, pat.getFirstName());
            values.put(KEY_LAST_NAME, pat.getLastName());
            values.put(KEY_EMAIL,pat.getEmail());
            values.put(KEY_PASSWORD,pat.getQrCode());
            values.put(KEY_ROLE, "PATIENT");
        }else if(object instanceof Docteur){
            Docteur doc=(Docteur) object;
            values.put(KEY_ID,doc.getIdDocteur());
            values.put(KEY_FIRST_NAME, doc.getFirstName());
            values.put(KEY_LAST_NAME, doc.getLastName());
            values.put(KEY_EMAIL,doc.getEmail());
            values.put(KEY_PASSWORD,doc.getPassword());
            values.put(KEY_ROLE, "MEDECIN");
        }
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public Object getUserLogged(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            if(cursor.getString(5).equals("PATIENT")){
                Patient  pat=new Patient();
                pat.setIdPatient(cursor.getInt(0));
                pat.setFirstName(cursor.getString(1));
                pat.setLastName(cursor.getString(2));
                pat.setEmail(cursor.getString(3));
                pat.setQrCode(cursor.getString(4));
                return pat;
            }else{
                Docteur  doc=new Docteur();
                doc.setIdDocteur(cursor.getInt(0));
                doc.setFirstName(cursor.getString(1));
                doc.setLastName(cursor.getString(2));
                doc.setEmail(cursor.getString(3));
                doc.setPassword(cursor.getString(4));
                return doc;
            }
        }
        return null;
    }
    public  void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE IF EXISTS  " + TABLE_USER;
        db.execSQL(query);
        onCreate(db);
    }
}
