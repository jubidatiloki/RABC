package procorp.rabc.list;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import procorp.rabc.R;
import procorp.rabc.RecetteAff;

/**
 * Created by benja on 27/02/2018.
 */

public class RecetteAdapter extends RecyclerView.Adapter<RecetteAdapter.MyViewHolder> {
    private List<RecetteAff> recetteAffList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nomRecette, tpsPreparation, tpsCuisson, difficulte, tag, nbPersonnes;
        private ImageView imgView, favoris;

        private MyViewHolder(View view) {
            super(view);
            nomRecette =  view.findViewById(R.id.nomRecette);
            tpsPreparation = view.findViewById(R.id.tpsPreparation);
            tpsCuisson = view.findViewById(R.id.tpsCuisson);
            difficulte = view.findViewById(R.id.difficulte);
            tag = view.findViewById(R.id.tag);
            nbPersonnes = view.findViewById(R.id.nbPersonnes);
            imgView = view.findViewById(R.id.img);
            favoris = view.findViewById(R.id.favoris);
        }
    }


    public RecetteAdapter(List<RecetteAff> recetteAffList) {
        this.recetteAffList = recetteAffList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recette_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecetteAff recetteAff = recetteAffList.get(position);
        holder.nomRecette.setText(recetteAff.getNomRecette());
        holder.tpsPreparation.setText(recetteAff.getTpsPreparation());
        holder.tpsCuisson.setText(recetteAff.getTpsCuisson());
        holder.difficulte.setText(recetteAff.getDifficulte());
        holder.tag.setText(recetteAff.getTag());
        holder.nbPersonnes.setText(recetteAff.getNbPersonnes() + "Pers");
        if(recetteAff.getCheminImg().equals("")) {
            if(recetteAff.getIdRecette() == 1 && recetteAff.getNomRecette().equals(context.getResources().getString(R.string.nom_recette_defaut_entree1))){
                holder.imgView.setImageResource(R.mipmap.defaut_salade_quercynoise);
            }else if(recetteAff.getIdRecette() == 2 && recetteAff.getNomRecette().equals(context.getResources().getString(R.string.nom_recette_defaut_plat1))){
                holder.imgView.setImageResource(R.mipmap.defaut_tartiflette);
            }else if(recetteAff.getIdRecette() == 3 && recetteAff.getNomRecette().equals(context.getResources().getString(R.string.nom_recette_defaut_dessert1))){
                holder.imgView.setImageResource(R.mipmap.defaut_pain_perdu);
            }else if(recetteAff.getIdRecette() == 4 && recetteAff.getNomRecette().equals(context.getResources().getString(R.string.nom_recette_defaut_dessert2))){
                holder.imgView.setImageResource(R.mipmap.defaut_mousse_chocolat);
            }else if(recetteAff.getIdRecette() == 5 && recetteAff.getNomRecette().equals(context.getResources().getString(R.string.nom_recette_defaut_cocktail1))){
                holder.imgView.setImageResource(R.mipmap.defaut_punch);
            }else{
                holder.imgView.setImageResource(R.drawable.ic_gallery);
            }
        }else{
            holder.imgView.setImageBitmap(BitmapFactory.decodeFile(recetteAff.getCheminImg()));
        }
        holder.imgView.setVisibility(View.VISIBLE);
        if(recetteAff.getFavoris()== 1){
            holder.favoris.setImageResource(R.drawable.ic_full_star);
        }else{
            holder.favoris.setImageResource(R.drawable.ic_blank_star);
        }
        holder.favoris.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return recetteAffList.size();
    }
}


