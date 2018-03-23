package procorp.rabc.list;

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nomRecette, tpsPreparation, tpsCuisson, difficulte, tag;
        private ImageView imgView;

        private MyViewHolder(View view) {
            super(view);
            nomRecette =  view.findViewById(R.id.nomRecette);
            tpsPreparation = view.findViewById(R.id.tpsPreparation);
            tpsCuisson = view.findViewById(R.id.tpsCuisson);
            difficulte = view.findViewById(R.id.difficulte);
            tag = view.findViewById(R.id.tag);
            imgView = view.findViewById(R.id.img);
        }
    }


    public RecetteAdapter(List<RecetteAff> recetteAffList) {
        this.recetteAffList = recetteAffList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if(recetteAff.getCheminImg().equals("")) {
            holder.imgView.setImageResource(R.drawable.ic_gallery);
            holder.imgView.setVisibility(View.VISIBLE);
        }else{
            System.out.println(recetteAff.getCheminImg());
            holder.imgView.setImageBitmap(BitmapFactory.decodeFile(recetteAff.getCheminImg()));
            holder.imgView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return recetteAffList.size();
    }
}


