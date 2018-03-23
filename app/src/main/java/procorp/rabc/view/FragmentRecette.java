package procorp.rabc.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import procorp.rabc.R;
import procorp.rabc.RecetteAff;
import procorp.rabc.database.Instruction;
import procorp.rabc.database.InstructionManager;
import procorp.rabc.database.Recette;
import procorp.rabc.database.RecetteManager;

/**
 * Created by benja on 27/02/2018.
 */

public class FragmentRecette extends Fragment {

    View myView;
    RecetteManager recetteManager;
    Recette recette;
    InstructionManager instructionManager;
    Instruction instruction;

    ImageView imgModif;
    ImageView gallery;
    String path;

    TextView nomRecette;
    TextView tpsPrep;
    TextView tpsCuiss;
    TextView difficulte;
    TextView categorie;
    TextView tag;
    TextView ingredients;

    LinearLayout LNinstructions;
    TextView etape;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_recette, container, false);
        Bundle bundle = getArguments();
        int idRecette = bundle.getInt("idRecette");

         recetteManager = new RecetteManager(myView.getContext());
         recetteManager.open();
         instructionManager = new InstructionManager(myView.getContext());
         instructionManager.open();
         recette = recetteManager.getRecetteById(idRecette);


         imgModif = myView.findViewById(R.id.recette_modif);

         gallery = myView.findViewById(R.id.gallery);
         nomRecette = myView.findViewById(R.id.recette_nomRecette);
         tpsPrep = myView.findViewById(R.id.recette_tpsPrep);
         tpsCuiss = myView.findViewById(R.id.recette_tpsCuiss);
         difficulte = myView.findViewById(R.id.recette_difficulte);
         categorie = myView.findViewById(R.id.recette_categorie);
         tag = myView.findViewById(R.id.recette_tag);
         ingredients = myView.findViewById(R.id.recette_ingredients);


        if(recette.getCheminImg().equals("")) {
            gallery.setImageResource(R.drawable.ic_gallery);
        }else{
            gallery.setImageBitmap(BitmapFactory.decodeFile(recette.getCheminImg()));
            gallery.setVisibility(View.VISIBLE);
        }
         nomRecette.setText(recette.getNomRecette());
         tpsPrep.setText(recette.getTpsPreparation()+"min");
         tpsCuiss.setText(recette.getTpsCuisson()+"min");
         difficulte.setText(recette.getDifficulte());
         categorie.setText(recette.getCategorie());
         tag.setText(recette.getTag());

         String[] ingList = recette.getIngredients().split(",");
         ingredients.setText("");
         for(int i= 0; i < ingList.length; i++){
             ingredients.setText(ingredients.getText() + "-" + ingList[i] + "\n");
         }




        Cursor c = instructionManager.getInstructionsByIdRecette(idRecette);
        c.moveToFirst();
        LNinstructions = myView.findViewById(R.id.recette_instructions);

        for(int i=0; i<instructionManager.getInstructionsByIdRecette(idRecette).getCount(); i++){

            int n = c.getInt(0);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT,
                    (int) LinearLayout.LayoutParams.WRAP_CONTENT);
            instruction = instructionManager.getInstructionByIdInstruction(n);

            //num étape
            etape = new TextView(myView.getContext());
            etape.setText("Etape  " + instruction.getNumEtape() + ":");
            etape.setLayoutParams(params1);
            etape.setTextSize(20);
            etape.setTypeface(null, Typeface.BOLD);
            etape.setGravity(Gravity.TOP|Gravity.LEFT);
            LNinstructions.addView(etape);
            //libellé
            etape = new TextView(myView.getContext());
            etape.setText(instruction.getLibelle());
            etape.setLayoutParams(params1);
            etape.setTextSize(20);
            etape.setGravity(Gravity.TOP|Gravity.CENTER);
            etape.setPadding(0,0,0,10);
            LNinstructions.addView(etape);
            c.moveToNext();
        }

        imgModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentRecetteModif();
                Bundle bundle=new Bundle();
                bundle.putInt("idRecette", recette.getIdRecette());
                fragment.setArguments(bundle);
                FragmentManager frM = getFragmentManager();
                frM.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });


        return myView;
    }
}
