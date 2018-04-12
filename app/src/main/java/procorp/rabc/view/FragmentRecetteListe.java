package procorp.rabc.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import procorp.rabc.DialogPopupRemove;
import procorp.rabc.R;
import procorp.rabc.RecetteAff;
import procorp.rabc.database.Recette;
import procorp.rabc.database.RecetteManager;
import procorp.rabc.list.RecetteAdapter;
import procorp.rabc.list.RecyclerTouchListener;

/**
 * Created by benja on 27/02/2018.
 */

public class FragmentRecetteListe extends Fragment {

    View myView;
    RecetteManager recetteManager;
    List<RecetteAff> recetteAffList = new ArrayList<>();
    RecyclerView recyclerView;
    RecetteAdapter recetteAdapter;
    RecetteAff recetteAff;
    Recette recette;
    String cat;
    String titre;
    TextView labelVide;

    //recherche
    LinearLayout recherche;
    EditText motRecherche;
    Button validerRecherche;
    String nom = "";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_recette_liste, container, false);
        Bundle bundle = getArguments();
        String categorie = bundle.getString("categorie");

        recetteManager = new RecetteManager(myView.getContext());
        recetteManager.open();


        labelVide = myView.findViewById(R.id.labelVide);

        recherche = myView.findViewById(R.id.recherche);
        motRecherche = myView.findViewById(R.id.motRecherche);
        validerRecherche = myView.findViewById(R.id.validerRecherche);

        validerRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nom = motRecherche.getText().toString();
                recetteAffList.clear();
                prepareRecetteAffData();
            }
        });

        switch (categorie){
            case "entrée":
                cat = "entrée";
                titre = "Vos entrées";
                break;
            case "plat":
                cat = "plat";
                titre = "Vos plats";
                break;
            case "dessert":
                cat = "dessert";
                titre = "Vos desserts";
                break;
            case "cocktail":
                cat = "cocktail";
                titre = "Vos cocktails";
                break;
            case "favoris":
                cat = "favoris";
                titre = "Vos favoris";
                break;
            default:
                cat = "";
                break;
        }
        recyclerView = myView.findViewById(R.id.recycler_view);

        recetteAdapter = new RecetteAdapter(recetteAffList);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());


        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(myView.getContext(), LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareRecetteAffData();

        recyclerView.setAdapter(recetteAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity().getBaseContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                recetteAff = recetteAffList.get(position);
                Fragment fragment = new FragmentRecette();
                Bundle bundle=new Bundle();
                bundle.putInt("idRecette", recetteAff.getIdRecette());
                fragment.setArguments(bundle);
                FragmentManager frM = getFragmentManager();
                frM.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                recetteAff = recetteAffList.get(position);
                DialogFragment dialog = new DialogPopupRemove();
                dialog.show(getFragmentManager(), recetteAff.getIdRecette()+"");

            }
        }));



        return myView;
    }

    private void prepareRecetteAffData() {

        if(cat.equals("")) {
            if (recetteManager.getAllRecettes().getCount() != 0) {
                labelVide.setVisibility(View.GONE);
                recherche.setVisibility(View.VISIBLE);
                Cursor c = recetteManager.getRecettesByNom(nom);
                c.moveToFirst();
                if (recetteManager.getRecettesByNom(nom).getCount() == 0) {
                    labelVide.setVisibility(View.VISIBLE);
                    labelVide.setText(R.string.rechercheNulle);
                }

                for (int i = 0; i < recetteManager.getRecettesByNom(nom).getCount(); i++) {
                    int n = c.getInt(0);
                    recette = recetteManager.getRecetteById(n);
                    recetteAff = new RecetteAff(recette.getIdRecette(), recette.getNomRecette(), "tpsPrep: " + recette.getTpsPreparation() + "min", "tpsCuiss: " + recette.getTpsCuisson() + "min", "difficulté: " + recette.getDifficulte(), "Tag: " + recette.getTag(), recette.getCheminImg(), recette.getNbPersonnes(), recette.getFavoris());
                    recetteAffList.add(recetteAff);
                    c.moveToNext();
                }
            } else {
                labelVide.setText(R.string.aucuneRecette);
                labelVide.setVisibility(View.VISIBLE);
            }
        }else if(cat.equals("favoris")){
            if (recetteManager.getFavoris().getCount() != 0) {
                labelVide.setText(titre);
                labelVide.setVisibility(View.VISIBLE);
                recherche.setVisibility(View.GONE);
                Cursor c = recetteManager.getFavoris();
                c.moveToFirst();
                for(int i=0; i<recetteManager.getFavoris().getCount(); i++){
                    int n = c.getInt(0);
                    recette = recetteManager.getRecetteById(n);
                    recetteAff = new RecetteAff(recette.getIdRecette(), recette.getNomRecette(), "tpsPrep: " + recette.getTpsPreparation() + "min", "tpsCuiss: " + recette.getTpsCuisson() + "min", "difficulté: " + recette.getDifficulte() , "Tag: " + recette.getTag(), recette.getCheminImg(), recette.getNbPersonnes(), recette.getFavoris());
                    recetteAffList.add(recetteAff);
                    c.moveToNext();
                }
            }else{
                labelVide.setText(R.string.aucuneRecetteFavoris);
                labelVide.setVisibility(View.VISIBLE);
                recherche.setVisibility(View.GONE);
            }


        }else{
            if (recetteManager.getRecettesByCategorie(cat).getCount() != 0) {
                labelVide.setText(titre);
                labelVide.setVisibility(View.VISIBLE);
                recherche.setVisibility(View.GONE);
                Cursor c = recetteManager.getRecettesByCategorie(cat);
                c.moveToFirst();
                for(int i=0; i<recetteManager.getRecettesByCategorie(cat).getCount(); i++){
                    int n = c.getInt(0);
                    recette = recetteManager.getRecetteById(n);
                    recetteAff = new RecetteAff(recette.getIdRecette(), recette.getNomRecette(), "tpsPrep: " + recette.getTpsPreparation() + "min", "tpsCuiss: " + recette.getTpsCuisson() + "min", "difficulté: " + recette.getDifficulte() , "Tag: " + recette.getTag(), recette.getCheminImg(), recette.getNbPersonnes(), recette.getFavoris());
                    recetteAffList.add(recetteAff);
                    c.moveToNext();
                }
            }else{
                labelVide.setText(R.string.aucuneRecette);
                labelVide.setVisibility(View.VISIBLE);
                recherche.setVisibility(View.GONE);
            }
        }
        recetteAdapter.notifyDataSetChanged();
    }

}
