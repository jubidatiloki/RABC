package procorp.rabc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by benja on 27/02/2018.
 */

public class MaBaseSQLite extends SQLiteOpenHelper{

    //recette
    private static final String TABLE_RECETTE = "table_recette";
    public static final String COL_IDRECETTE = "idRecette";
    public static final String COL_NOMRECETTE = "nomRecette";
    public static final String COL_TPSPREPARATION = "tpsPreparation";
    public static final String COL_TPSCUISSON = "tpsCuisson";
    public static final String COL_DIFFICULTE = "difficulte";
    public static final String COL_INGREDIENTS = "ingredients";  //stocker sous forme de string séparé par des virgules
    public static final String COL_CATEGORIE = "categorie";
    public static final String COL_TAG = "tag";      //stocker sous forme de string séparé par des virgules
    public static final String COL_CHEMINIMG = "cheminImg";

    //instruction
    private static final String TABLE_INSTRUCTION = "table_instruction";
    public static final String COL_IDINSTRUCTION = "idInstruction";
    //public static final String COL_IDRECETTE = "idRecette";
    public static final String COL_NUMETAPE = "numEtape";
    public static final String COL_LIBELLE = "libelle";


    public static final String CREATE_TABLE_RECETTE = "CREATE TABLE " + TABLE_RECETTE + " ( "
            + COL_IDRECETTE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NOMRECETTE + " TEXT, "
            + COL_TPSPREPARATION + " INTEGER, "
            + COL_TPSCUISSON + " INTEGER, "
            + COL_DIFFICULTE + " INTEGER, "
            + COL_INGREDIENTS + " TEXT, "
            + COL_CATEGORIE + " TEXT, "
            + COL_TAG + " TEXT, "
            + COL_CHEMINIMG + " TEXT );";

    public static final String CREATE_TABLE_IDINSTRUCTION = "CREATE TABLE " + TABLE_INSTRUCTION + " ( "
            + COL_IDINSTRUCTION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_IDRECETTE + " INTEGER, "
            + COL_NUMETAPE + " INTEGER, "
            + COL_LIBELLE + " TEXT );";


    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECETTE);
        db.execSQL(CREATE_TABLE_IDINSTRUCTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETTE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTION + ";");
        onCreate(db);
    }
}
