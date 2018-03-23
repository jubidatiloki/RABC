package procorp.rabc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import procorp.rabc.database.InstructionManager;
import procorp.rabc.database.Recette;
import procorp.rabc.database.RecetteManager;
import procorp.rabc.view.FragmentRecetteListe;

/**
 * Created by benja on 03/03/2018.
 */

public class DialogPopupRemove extends DialogFragment {

    private RecetteManager recetteManager;
    private InstructionManager instructionManager;
    private Recette recette;
    private int idRecette;
    private String categorie;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialog);

        recetteManager = new RecetteManager(getActivity().getBaseContext());
        recetteManager.open();
        instructionManager = new InstructionManager(getActivity().getBaseContext());
        instructionManager.open();

        idRecette = Integer.parseInt(getTag());
        recette = recetteManager.getRecetteById(idRecette);
        categorie = recette.getCategorie();
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        builder.setMessage("voulez vous supprimer la recette:  " + recette.getNomRecette());
        builder.setPositiveButton("oui, je supprime", new okRemove());
        builder.setNegativeButton("annuler", new cancelRemove());

        return builder.create();
    }

    private final class okRemove implements  DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            recetteManager.removeRecetteById(idRecette);
            instructionManager.removeInstructionByIdRecette(idRecette);
            Toast.makeText(getActivity().getBaseContext(), "recette supprimée", Toast.LENGTH_SHORT).show();
            Fragment fragment = new FragmentRecetteListe();
            Bundle bundle=new Bundle();
            bundle.putString("categorie", categorie);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    private final class cancelRemove implements  DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }
}


