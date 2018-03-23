package procorp.rabc.view;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import procorp.rabc.DialogPopupAide;
import procorp.rabc.R;
import procorp.rabc.database.Instruction;
import procorp.rabc.database.InstructionManager;
import procorp.rabc.database.Recette;
import procorp.rabc.database.RecetteManager;

import static android.app.Activity.RESULT_OK;

/**
 * Created by benja on 27/02/2018.
 */

public class FragmentAjouter extends Fragment {

    final String[] labelEtape = {"etape1", "etape2","etape3","etape4", "etape5", "etape6", "etape7", "etape8", "etape9", "etape10"};
    final String[] editEtape = {"ajout_etape1", "ajout_etape2", "ajout_etape3", "ajout_etape4", "ajout_etape5", "ajout_etape6", "ajout_etape7", "ajout_etape8", "ajout_etape9", "ajout_etape10"};
    LinearLayout ll;
    final TextView[] tv = new TextView[10];
    final EditText[] et = new EditText[10];

    int nbEtape = 1;


    View myView;
    ImageView gallery;
    String path = "";
    EditText Enom;
    EditText EtpsPrep;
    EditText EtpsCuiss;
    Spinner spinnDifficulte;
    Spinner spinnCategorie;
    EditText Etag;
    EditText Eingredient;

    ImageView tagHelp;
    ImageView ingHelp;

    Button betape;
    Button bsuppr;
    Button benregistrer;

    Instruction instruction;
    InstructionManager instructionManager;
    Recette recette;
    RecetteManager recetteManager;

    private static int RESULT_LOAD_IMG = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_ajouter, container, false);

        ll = myView.findViewById(R.id.etape);

        recetteManager = new RecetteManager(myView.getContext());
        recetteManager.open();
        instructionManager = new InstructionManager(myView.getContext());
        instructionManager.open();


        gallery = myView.findViewById(R.id.ajout_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });

        Enom = myView.findViewById(R.id.ajout_nomRecette);
        EtpsPrep = myView.findViewById(R.id.ajout_tpsPreparation);
        EtpsCuiss = myView.findViewById(R.id.ajout_tpsCuisson);

        spinnCategorie = myView.findViewById(R.id.ajout_categorie);
        ArrayAdapter<CharSequence> categorieAdapter = ArrayAdapter.createFromResource(myView.getContext(), R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        categorieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnCategorie.setAdapter(categorieAdapter);



        spinnDifficulte = myView.findViewById(R.id.ajout_difficulte);
        ArrayAdapter<CharSequence> difficulteAdapter = ArrayAdapter.createFromResource(myView.getContext(), R.array.difficulte_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        difficulteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnDifficulte.setAdapter(difficulteAdapter);

        Etag = myView.findViewById(R.id.ajout_tag);
        Eingredient = myView.findViewById(R.id.ajout_ingredients);

        tagHelp = myView.findViewById(R.id.tag_help);
        ingHelp = myView.findViewById(R.id.ing_help);

        betape = myView.findViewById(R.id.ajout_etape);
        bsuppr = myView.findViewById(R.id.suppr_etape);
        benregistrer = myView.findViewById(R.id.ajout_enregistrer);

        benregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean videEtape = false;
                for(int i =0; i<nbEtape; i++){
                    if(et[i].getText().toString().equals("")){
                        videEtape = true;
                    }
                }
                if(Enom.getText().toString().equals("") || EtpsPrep.getText().toString().equals("") || EtpsCuiss.getText().toString().equals("")
                        || Eingredient.getText().toString().equals("") || videEtape){
                    Toast.makeText(myView.getContext(), "Vous devez renseigner toutes les informations concernant la recette.", Toast.LENGTH_SHORT).show();
                }else{
                    String nom = Enom.getText().toString();
                    int tpsPrep = Integer.parseInt(EtpsPrep.getText().toString());
                    int tpsCuiss = Integer.parseInt(EtpsCuiss.getText().toString());
                    String difficulte = spinnDifficulte.getSelectedItem().toString();
                    String categorie = spinnCategorie.getSelectedItem().toString();
                    String tag = Etag.getText().toString();
                    String ingredients = Eingredient.getText().toString();

                    recette = new Recette();
                    recette.setNomRecette(nom);
                    recette.setTpsPreparation(tpsPrep);
                    recette.setTpsCuisson(tpsCuiss);
                    recette.setDifficulte(difficulte);
                    recette.setCategorie(categorie);
                    recette.setTag(tag);
                    recette.setIngredients(ingredients);
                    recette.setCheminImg(path);
                    recetteManager.insertRecette(recette);

                    for(int i = 0; i<nbEtape; i++){
                        instruction = new Instruction();
                        instruction.setIdRecette(recetteManager.getRecetteByNom(nom).getIdRecette());
                        instruction.setNumEtape(i+1);
                        instruction.setLibelle(et[i].getText().toString());
                        instructionManager.insertInstruction(instruction);
                        //Toast.makeText(myView.getContext(), i + ": " + instruction.toString(), Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(myView.getContext(), "nouvelle recette: " + recette.getNomRecette(), Toast.LENGTH_SHORT).show();
                    Fragment fragment = new FragmentRecetteListe();
                    Bundle bundle=new Bundle();
                    bundle.putString("categorie", recette.getCategorie());
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }




            }
        });

        tv[0] = myView.findViewById(R.id.label_etape1);
        et[0] = myView.findViewById(R.id.ajout_etape1);


        betape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nbEtape < labelEtape.length ){

                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT,
                            (int) LinearLayout.LayoutParams.WRAP_CONTENT);
                    tv[nbEtape] = new TextView(myView.getContext());
                    tv[nbEtape].setText(labelEtape[nbEtape]);
                    tv[nbEtape].setLayoutParams(params1);
                    ll.addView(tv[nbEtape]);

                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.MATCH_PARENT,
                            (int) LinearLayout.LayoutParams.WRAP_CONTENT);
                    et[nbEtape] = new EditText(myView.getContext());
                    et[nbEtape].setGravity(Gravity.TOP|Gravity.CENTER);
                    et[nbEtape].setMaxLines(10);
                    et[nbEtape].setLayoutParams(params2);
                    ll.addView(et[nbEtape]);
                    nbEtape++;
                    //Toast.makeText(myView.getContext(), "nbEtape"+nbEtape, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(myView.getContext(), "deja suffisamment d'étape: " + nbEtape, Toast.LENGTH_SHORT).show();
                }

            }
        });

        bsuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nbEtape>1){
                    ll.removeView(tv[nbEtape-1]);
                    ll.removeView(et[nbEtape-1]);
                    nbEtape--;
                }else{
                    Toast.makeText(myView.getContext(), "Vous devez garder au moins une étape à votre recette.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tagHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment dialog = new DialogPopupAide();
                dialog.show(getFragmentManager(), "tag");
            }
        });

        ingHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment dialog = new DialogPopupAide();
                dialog.show(getFragmentManager(), "ing");
            }
        });


        return myView;
    }

    public void loadImagefromGallery(View view) {

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

            Uri imageUri = data.getData();

            ImageView imageView = myView.findViewById(R.id.ajout_gallery);

            path = getPath(myView.getContext(), imageUri);

            //Toast.makeText(myView.getContext(), path, Toast.LENGTH_SHORT).show();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    String id = "";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        id = DocumentsContract.getDocumentId(uri);
                    }
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    String docId = "";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        docId = DocumentsContract.getDocumentId(uri);
                    }
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }



    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }



}
