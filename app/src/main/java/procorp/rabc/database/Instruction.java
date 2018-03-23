package procorp.rabc.database;

/**
 * Created by benja on 27/02/2018.
 */

public class Instruction {

    private int idInstruction;
    private int idRecette;
    private int numEtape;
    private String libelle;


    public Instruction(){}

    public Instruction(int idRecette, int numEtape, String libelle) {
        this.idRecette = idRecette;
        this.numEtape = numEtape;
        this.libelle = libelle;
    }

    public int getIdInstruction(){ return idInstruction; }

    public int getIdRecette() {
        return idRecette;
    }

    public int getNumEtape() {
        return numEtape;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdInstruction(int idInstruction){ this.idInstruction = idInstruction; }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public void setNumEtape(int numEtape) {
        this.numEtape = numEtape;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "idInstruction=" + idInstruction +
                "idRecette=" + idRecette +
                ", numEtape=" + numEtape +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
