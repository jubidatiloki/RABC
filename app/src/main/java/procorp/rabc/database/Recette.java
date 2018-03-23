package procorp.rabc.database;

import java.util.Arrays;

/**
 * Created by benja on 27/02/2018.
 */

public class Recette {

    private int idRecette;
    private String nomRecette;
    private int tpsPreparation;
    private int tpsCuisson;
    private String difficulte;
    private String ingredients;
    private String categorie;
    private String tag;
    private String cheminImg;


    public Recette(){}

    public Recette(String nomRecette, int tpsPreparation, int tpsCuisson, String difficulte, String ingredients, String categorie, String tag, String cheminImg) {
        this.nomRecette = nomRecette;
        this.tpsPreparation = tpsPreparation;
        this.tpsCuisson = tpsCuisson;
        this.difficulte = difficulte;
        this.ingredients = ingredients;
        this.categorie = categorie;
        this.tag = tag;
        this.cheminImg = cheminImg;
    }

    public int getIdRecette() {
        return idRecette;
    }

    public String getNomRecette() {
        return nomRecette;
    }

    public int getTpsPreparation() {
        return tpsPreparation;
    }

    public int getTpsCuisson() {
        return tpsCuisson;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getTag() {
        return tag;
    }

    public String getCheminImg(){ return cheminImg; }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public void setNomRecette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public void setTpsPreparation(int tpsPreparation) {
        this.tpsPreparation = tpsPreparation;
    }

    public void setTpsCuisson(int tpsCuisson) {
        this.tpsCuisson = tpsCuisson;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCheminImg(String cheminImg){ this.cheminImg = cheminImg; }


    @Override
    public String toString() {
        return "Recette{" +
                "idRecette=" + idRecette +
                ", nomRecette='" + nomRecette + '\'' +
                ", tpsPreparation=" + tpsPreparation +
                ", tpsCuisson=" + tpsCuisson +
                ", difficulte=" + difficulte +
                ", ingredients=" + ingredients +
                ", categorie='" + categorie + '\'' +
                ", tag=" + tag +
                ", cheminImg=" + cheminImg +
                '}';
    }
}
