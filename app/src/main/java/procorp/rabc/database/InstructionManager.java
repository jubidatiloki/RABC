package procorp.rabc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by benja on 27/02/2018.
 */

public class InstructionManager {

    private static final int VERSION_BDD = 6;
    private static final String NOM_BDD = "etape.db";

    private static final String TABLE_INSTRUCTION = "table_instruction";

    public static final String COL_IDINSTRUCTION = "idInstruction";
    public static final int NUM_COL_IDINSTRUCTION = 0;

    public static final String COL_IDRECETTE = "idRecette";
    public static final int NUM_COL_IDRECETTE = 1;

    public static final String COL_NUMETAPE = "numEtape";
    public static final int NUM_COL_NUMETAPE = 2;

    public static final String COL_LIBELLE = "libelle";
    public static final int NUM_COL_LIBELLE = 3;



    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;


    public InstructionManager(Context context){
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public long insertInstruction(Instruction instruction){
        ContentValues values = new ContentValues();
        values.put(COL_IDRECETTE, instruction.getIdRecette());
        values.put(COL_NUMETAPE, instruction.getNumEtape());
        values.put(COL_LIBELLE, instruction.getLibelle());

        return bdd.insert(TABLE_INSTRUCTION, null, values);
    }

    public int updateInstruction(int id, Instruction instruction){
        ContentValues values = new ContentValues();
        values.put(COL_IDINSTRUCTION, instruction.getIdInstruction());
        values.put(COL_IDRECETTE, instruction.getIdRecette());
        values.put(COL_NUMETAPE, instruction.getNumEtape());
        values.put(COL_LIBELLE, instruction.getLibelle());

        return bdd.update(TABLE_INSTRUCTION, values, COL_IDINSTRUCTION + " = " +id, null);
    }

    public void removeInstructionByIdRecette(int id){
        //Suppression d'une commande de la BDD grâce à l'ID
        bdd.delete(TABLE_INSTRUCTION, COL_IDRECETTE + " = " +id, null);
        String req = "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='"+TABLE_INSTRUCTION+"';";
        bdd.execSQL(req);
    }

    public Instruction getInstructionByIdInstruction(int id){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_INSTRUCTION, new String[] {COL_IDINSTRUCTION, COL_IDRECETTE, COL_NUMETAPE, COL_LIBELLE}, COL_IDINSTRUCTION + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToInstruction(c);
    }

    public Cursor getInstructionsByIdRecette(int idRecette){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_INSTRUCTION, new String[] {COL_IDINSTRUCTION, COL_IDRECETTE, COL_NUMETAPE, COL_LIBELLE}, COL_IDRECETTE + " LIKE \"" + idRecette +"\"", null, null, null, COL_NUMETAPE);
        return c;
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Instruction cursorToInstruction(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Instruction instruction = new Instruction();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        instruction.setIdInstruction(c.getInt(NUM_COL_IDINSTRUCTION));
        instruction.setIdRecette(c.getInt(NUM_COL_IDRECETTE));
        instruction.setNumEtape(c.getInt(NUM_COL_NUMETAPE));
        instruction.setLibelle(c.getString(NUM_COL_LIBELLE));

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return instruction;
    }

    public int nbreInstruction(){
        Cursor curseur = bdd.query(TABLE_INSTRUCTION, null, null, null, null, null, null);
        //Renvoyer le nombre de lignes
        return curseur.getCount();
    }

    public Cursor getAllInstruction(){
        Cursor c = bdd.query(TABLE_INSTRUCTION, null, null, null, null, null, null);
        return c;
    }

}
