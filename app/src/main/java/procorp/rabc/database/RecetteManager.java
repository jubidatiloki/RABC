package procorp.rabc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by benja on 27/02/2018.
 */

public class RecetteManager {

    private static final int VERSION_BDD = 4;
    private static final String NOM_BDD = "recette.db";

    private static final String TABLE_RECETTE = "table_recette";

    public static final String COL_IDRECETTE = "idRecette";
    public static final int NUM_COL_IDRECETTE = 0;

    public static final String COL_NOMRECETTE = "nomRecette";
    public static final int NUM_COL_NOMRECETTE = 1;

    public static final String COL_TPSPREPARATION = "tpsPreparation";
    public static final int NUM_COL_TPSPREPARATION = 2;

    public static final String COL_TPSCUISSON = "tpsCuisson";
    public static final int NUM_COL_TPSCUISSON = 3;

    public static final String COL_DIFFICULTE = "difficulte";
    public static final int NUM_COL_DIFFICULTE = 4;

    public static final String COL_INGREDIENTS = "ingredients";  //stocker sous forme de string séparé par des virgules
    public static final int NUM_COL_INGREDIENTS = 5;

    public static final String COL_CATEGORIE = "categorie";
    public static final int NUM_COL_CATEGORIE = 6;

    public static final String COL_TAG = "tag";      //stocker sous forme de string séparé par des virgules
    public static final int NUM_COL_TAG = 7;

    public static final String COL_CHEMINIMG = "cheminImg";
    public static final int NUM_COL_CHEMINIMG = 8;

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;


    public RecetteManager(Context context){
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public long insertRecette(Recette recette){
        ContentValues values = new ContentValues();
        values.put(COL_NOMRECETTE, recette.getNomRecette());
        values.put(COL_TPSPREPARATION, recette.getTpsPreparation());
        values.put(COL_TPSCUISSON, recette.getTpsCuisson());
        values.put(COL_DIFFICULTE, recette.getDifficulte());
        values.put(COL_INGREDIENTS, recette.getIngredients());
        values.put(COL_CATEGORIE, recette.getCategorie());
        values.put(COL_TAG, recette.getTag());
        values.put(COL_CHEMINIMG, recette.getCheminImg());

        return bdd.insert(TABLE_RECETTE, null, values);
    }

    public int updateRecette(int id, Recette recette){
        ContentValues values = new ContentValues();
        values.put(COL_NOMRECETTE, recette.getNomRecette());
        values.put(COL_TPSPREPARATION, recette.getTpsPreparation());
        values.put(COL_TPSCUISSON, recette.getTpsCuisson());
        values.put(COL_DIFFICULTE, recette.getDifficulte());
        values.put(COL_INGREDIENTS, recette.getIngredients());
        values.put(COL_CATEGORIE, recette.getCategorie());
        values.put(COL_TAG, recette.getTag());
        values.put(COL_CHEMINIMG, recette.getCheminImg());

        return bdd.update(TABLE_RECETTE, values, COL_IDRECETTE + " = " +id, null);
    }

    public void removeRecetteById(int id){
        bdd.delete(TABLE_RECETTE, COL_IDRECETTE + " = " +id, null);
        String req = "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='"+TABLE_RECETTE+"';";
        bdd.execSQL(req);
    }

    public Recette getRecetteById(int id){
        Cursor c = bdd.query(TABLE_RECETTE, new String[] {COL_IDRECETTE, COL_NOMRECETTE, COL_TPSPREPARATION, COL_TPSCUISSON, COL_DIFFICULTE, COL_INGREDIENTS, COL_CATEGORIE, COL_TAG, COL_CHEMINIMG}, COL_IDRECETTE + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToRecette(c);
    }

    public Recette getRecetteByNom(String nom){
        Cursor c = bdd.query(TABLE_RECETTE, new String[] {COL_IDRECETTE, COL_NOMRECETTE, COL_TPSPREPARATION, COL_TPSCUISSON, COL_DIFFICULTE, COL_INGREDIENTS, COL_CATEGORIE, COL_TAG, COL_CHEMINIMG}, COL_NOMRECETTE + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToRecette(c);
    }

    public Cursor getRecettesByCategorie(String categorie){
        Cursor c = bdd.query(TABLE_RECETTE, new String[] {COL_IDRECETTE, COL_NOMRECETTE, COL_TPSPREPARATION, COL_TPSCUISSON, COL_DIFFICULTE, COL_INGREDIENTS, COL_CATEGORIE, COL_TAG, COL_CHEMINIMG}, COL_CATEGORIE + " LIKE \"" + categorie +"\"", null, null, null, null);
        return c;
    }

    private Recette cursorToRecette(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Recette recette = new Recette();
        recette.setIdRecette(c.getInt(NUM_COL_IDRECETTE));
        recette.setNomRecette(c.getString(NUM_COL_NOMRECETTE));
        recette.setTpsPreparation(c.getInt(NUM_COL_TPSPREPARATION));
        recette.setTpsCuisson(c.getInt(NUM_COL_TPSCUISSON));
        recette.setDifficulte(c.getString(NUM_COL_DIFFICULTE));
        recette.setIngredients(c.getString(NUM_COL_INGREDIENTS));
        recette.setCategorie(c.getString(NUM_COL_CATEGORIE));
        recette.setTag(c.getString(NUM_COL_TAG));
        recette.setCheminImg(c.getString(NUM_COL_CHEMINIMG));
        c.close();

        return recette;
    }

    public int nbreRecette(){
        Cursor curseur = bdd.query(TABLE_RECETTE, null, null, null, null, null, null);
        return curseur.getCount();
    }

    public Cursor getAllRecettes(){
        Cursor c = bdd.query(TABLE_RECETTE, new String[] {COL_IDRECETTE, COL_NOMRECETTE, COL_TPSPREPARATION, COL_TPSCUISSON, COL_DIFFICULTE, COL_INGREDIENTS, COL_CATEGORIE, COL_TAG, COL_CHEMINIMG}, null, null, null, null, null);
        return c;
    }

    public Cursor getRecettesByNom(String nom){
        Cursor c = bdd.query(TABLE_RECETTE, new String[] {COL_IDRECETTE, COL_NOMRECETTE, COL_TPSPREPARATION, COL_TPSCUISSON, COL_DIFFICULTE, COL_INGREDIENTS, COL_CATEGORIE, COL_TAG, COL_CHEMINIMG}, COL_NOMRECETTE + " LIKE \"" + nom +"%\"",null, null, null, null);
        return c;
    }
}
