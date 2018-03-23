package procorp.rabc;

/**
 * Created by benja on 27/02/2018.
 */

public class RecetteAff {

    //private image;
    private int idRecette;
    private String nomRecette;
    private String tpsPreparation;
    private String tpsCuisson;
    private String difficulte;
    private String tag;
    private String cheminImg;

    public RecetteAff(){}

    public RecetteAff(int idRecette, String nomRecette, String tpsPreparation, String tpsCuisson, String difficulte, String tag, String cheminImg) {
        this.idRecette = idRecette;
        this.nomRecette = nomRecette;
        this.tpsPreparation = tpsPreparation;
        this.tpsCuisson = tpsCuisson;
        this.difficulte = difficulte;
        this.tag = tag;
        this.cheminImg = cheminImg;
    }

    public int getIdRecette(){ return idRecette; }

    public String getNomRecette() {
        return nomRecette;
    }

    public String getTpsPreparation() {
        return tpsPreparation;
    }

    public String getTpsCuisson() {
        return tpsCuisson;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public String getTag() {
        return tag;
    }

    public String getCheminImg() { return cheminImg; }

    public void setIdRecette(int idRecette){ this.idRecette = idRecette; }

    public void setNomRecette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public void setTpsPreparation(String tpsPreparation) {
        this.tpsPreparation = tpsPreparation;
    }

    public void setTpsCuisson(String tpsCuisson) {
        this.tpsCuisson = tpsCuisson;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCheminImg(String cheminImg) { this.cheminImg = cheminImg; }

    @Override
    public String toString() {
        return "RecetteAff{" +
                "idRecettte='" + idRecette + '\'' +
                "nomRecette='" + nomRecette + '\'' +
                ", tpsPreparation=" + tpsPreparation +
                ", tpsCuisson=" + tpsCuisson +
                ", difficulte=" + difficulte +
                ", tag='" + tag + '\'' +
                ", cheminImg=" + cheminImg + '\'' +
                '}';
    }
}
