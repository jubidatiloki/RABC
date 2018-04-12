package procorp.rabc;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import procorp.rabc.database.Instruction;
import procorp.rabc.database.InstructionManager;
import procorp.rabc.database.Recette;
import procorp.rabc.database.RecetteManager;
import procorp.rabc.view.FragmentAccueil;
import procorp.rabc.view.FragmentAjouter;
import procorp.rabc.view.FragmentRecetteListe;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecetteManager recetteManager;
    Recette recette;
    InstructionManager instructionManager;
    Instruction instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        recetteManager = new RecetteManager(this);
        recetteManager.open();
        instructionManager = new InstructionManager(this);
        instructionManager.open();

        if(recetteManager.nbreRecette() <= 1){
            init();
        }
        FragmentManager frM = getFragmentManager();
        frM.beginTransaction().replace(R.id.content_frame, new FragmentAccueil()).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void init(){
        checkPermissions();

        recette = new Recette();
        recette.setNomRecette(getResources().getString(R.string.nom_recette_defaut_entree1));
        recette.setTpsPreparation(15);
        recette.setTpsCuisson(0);
        recette.setIngredients("salade, foie gras, gésiers de canard, huile de noix, vinaigrette");
        recette.setDifficulte("facile");
        recette.setCategorie("entrée");
        recette.setTag("salé, froid");
        recette.setCheminImg("");
        recette.setNbPersonnes(4);
        recette.setFavoris(0);
        recetteManager.insertRecette(recette);

        instruction = new Instruction();
        instruction.setNumEtape(1);
        instruction.setIdRecette(recetteManager.getRecetteByNom(recette.getNomRecette()).getIdRecette());
        instruction.setLibelle("Commencez à faire cuire les gésiers de canard à la peole à feu doux.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(2);
        instruction.setLibelle("Découpez ensuite le foie gras en petits morceaux (de petite taille pour manger avec la salade, " +
                "ou en plus gros morceaux pour le manger avec du pain en accompagnement).");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(3);
        instruction.setLibelle("Mélangez le tout et assaisonnez avec la vinaigrette. Laissez une demi-heure au frigo, pour " +
                "plus de fraicheur.");
        instructionManager.insertInstruction(instruction);
        Toast.makeText(this, "ajout de la recette: "+recette.getNomRecette(), Toast.LENGTH_SHORT).show();


        recette = new Recette();
        recette.setNomRecette(getResources().getString(R.string.nom_recette_defaut_plat1));
        recette.setTpsPreparation(30);
        recette.setTpsCuisson(60);
        recette.setIngredients("pomme de terre, lardons, oignon, reblochon, crème fraiche");
        recette.setDifficulte("moyenne");
        recette.setCategorie("plat");
        recette.setTag("salé, chaud");
        recette.setCheminImg("");
        recette.setNbPersonnes(4);
        recette.setFavoris(0);
        recetteManager.insertRecette(recette);

        instruction = new Instruction();
        instruction.setNumEtape(1);
        instruction.setIdRecette(recetteManager.getRecetteByNom(recette.getNomRecette()).getIdRecette());
        instruction.setLibelle("Commencez à faire cuire les pommes de terre (si elles ne sont pas déjà cuites.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(2);
        instruction.setLibelle("Coupez les pommes de terre en rondelles et disposez en au fond d'un grand plat.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(3);
        instruction.setLibelle("Ajoutez les lardons, le reblochon, la crème fraiche et le reste de pommes de terre, afin " +
                "d'avoir un plat assez bien réparti.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(4);
        instruction.setLibelle("Faites cuire pendant une bonne heure au four. Régalez vous.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(5);
        instruction.setLibelle("Conseil: peut être accompagnée d'une salade et d'un vin blanc.");
        instructionManager.insertInstruction(instruction);
        Toast.makeText(this, "ajout de la recette: "+recette.getNomRecette(), Toast.LENGTH_SHORT).show();


        recette = new Recette();
        recette.setNomRecette(getResources().getString(R.string.nom_recette_defaut_dessert1));
        recette.setTpsPreparation(5);
        recette.setTpsCuisson(5);
        recette.setIngredients("pain, oeuf, lait, sucre, beurre");
        recette.setDifficulte("facile");
        recette.setCategorie("dessert");
        recette.setTag("sucré, chaud");
        recette.setCheminImg("");
        recette.setNbPersonnes(4);
        recette.setFavoris(0);
        recetteManager.insertRecette(recette);

        instruction = new Instruction();
        instruction.setNumEtape(1);
        instruction.setIdRecette(recetteManager.getRecetteByNom(recette.getNomRecette()).getIdRecette());
        instruction.setLibelle("Battre les oeufs dans une assiette, y ajouter le sucre et le lait.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(2);
        instruction.setLibelle("Tremper les tranches de pain et les égoutter.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(3);
        instruction.setLibelle("Mettre le beurre à chauffer dans la poêle, y faire dorer doucement les tranches de chaque côté.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(4);
        instruction.setLibelle("Conseil: peut être mangé avec du sucre ou avec du nutella pour les plus groumands.");
        instructionManager.insertInstruction(instruction);
        Toast.makeText(this, "ajout de la recette: "+recette.getNomRecette(), Toast.LENGTH_SHORT).show();


        recette = new Recette();
        recette.setNomRecette(getResources().getString(R.string.nom_recette_defaut_dessert2));
        recette.setTpsPreparation(40);
        recette.setTpsCuisson(10);
        recette.setIngredients("240g chocolat noir, 6 oeuf, 60g sucre, 60g beurre");
        recette.setDifficulte("facile");
        recette.setCategorie("dessert");
        recette.setTag("sucré, froid");
        recette.setCheminImg("");
        recette.setNbPersonnes(7);
        recette.setFavoris(1);
        recetteManager.insertRecette(recette);

        instruction = new Instruction();
        instruction.setNumEtape(1);
        instruction.setIdRecette(recetteManager.getRecetteByNom(recette.getNomRecette()).getIdRecette());
        instruction.setLibelle("Casser les oeufs et séparez dans deux saladiers les blancs et les jaunes.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(2);
        instruction.setLibelle("Casser le chocolat en morceaux et le mettre à chauffer dans une casserolle à fond épais" +
                " (en ajoutant un filet d'eau).");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(3);
        instruction.setLibelle("Laisser le chocolat chauffer, sans cesser de remuer, jusqu'à ce que le chocolat " +
                "soit entièrement fondu.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(4);
        instruction.setLibelle("Monter les blancs en neige.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(5);
        instruction.setLibelle("Mélangez le chocolat et les jaunes en remuant frénétiquement afin d'évitez que les jaunes cuisent.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(6);
        instruction.setLibelle("Enfin, mélanger le chocolat et les blanc en neige (chocolat dans les blancs en neige). " +
                "Mélangez frénétiquement, toujours dans le même sens, jusqu'à ce qu'il n'y est plus de grumeaux.");
        instructionManager.insertInstruction(instruction);
        Toast.makeText(this, "ajout de la recette: "+recette.getNomRecette(), Toast.LENGTH_SHORT).show();


        recette = new Recette();
        recette.setNomRecette(getResources().getString(R.string.nom_recette_defaut_cocktail1));
        recette.setTpsPreparation(10);
        recette.setTpsCuisson(0);
        recette.setIngredients("1L rhum blanc(ou ambré), 1L jus d'orange, 1L jus de pamplemousse rose, 1L jus d'ananas, 50cL sirop de sucre de canne, 1c. à café de cannelle en poudre");
        recette.setDifficulte("moyenne");
        recette.setCategorie("cocktail");
        recette.setTag("sucré, froid, alcoolisé");
        recette.setCheminImg("");
        recette.setNbPersonnes(8);
        recette.setFavoris(0);
        recetteManager.insertRecette(recette);

        instruction = new Instruction();
        instruction.setNumEtape(1);
        instruction.setIdRecette(recetteManager.getRecetteByNom(recette.getNomRecette()).getIdRecette());
        instruction.setLibelle("Commencez votre punch antillais en prévoyant grand récipient comme un saladier ou même une cocotte." +
                        " Pensez que ce récipient doit ensuite entrer dans votre réfrigérateur pour conserver votre punch bien au frais."+
                        "Versez dans ce récipient le rhum, le jus d’orange, le pamplemousse rose, l’ananas, le sirop de sucre de canne et la cannelle en poudre.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(2);
        instruction.setIdRecette(recetteManager.getRecetteByNom(recette.getNomRecette()).getIdRecette());
        instruction.setLibelle("Veillez à bien respecter les doses indiquées. Vous pouvez toutefois augmenter ou diminuer la dose de rhum"+
                " en fonction de vos envies mais pensez à goûter votre punch avant de le servir pour éviter toute erreur de dosage." +
                " Mélangez bien le tout jusqu'à obtenir une couleur homogène à l'aide d'une spatule ou d'une cuillère en bois.");
        instructionManager.insertInstruction(instruction);

        instruction.setNumEtape(3);
        instruction.setIdRecette(recetteManager.getRecetteByNom(recette.getNomRecette()).getIdRecette());
        instruction.setLibelle("Réservez votre punch au réfrigérateur au moins 1 heure, jusqu’au moment de le consommer."+
                " Servez-le très frais dans des verres à cocktail sur lesquels vous pouvez placer un quartier d’orange ou de citron."+
                " Il est inutile d'ajouter des glaçons à votre punch planteur facile car l'eau pourrait en dénaturer le goût."+
                " Vous pouvez aussi présenter votre punch planteur dans un grand saladier avec une louche pour que vos invités se"+
                " servent eux-mêmes.");
        instructionManager.insertInstruction(instruction);


        Toast.makeText(this, "ajout de la recette: "+recette.getNomRecette(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new FragmentRecetteListe();
        Bundle bundle=new Bundle();
        if (id == R.id.nav_accueil) {
            fragment = new FragmentAccueil();

        } else if(id == R.id.nav_recettes){
            bundle.putString("categorie", "none");
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_entree) {
            bundle.putString("categorie", "entrée");
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_plat) {
            bundle.putString("categorie", "plat");
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_dessert) {
            bundle.putString("categorie", "dessert");
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_cocktail) {
            bundle.putString("categorie", "cocktail");
            fragment.setArguments(bundle);
        }else if(id == R.id.nav_favoris){
            bundle.putString("categorie", "favoris");
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_ajouter) {
            fragment = new FragmentAjouter();
        }
            if (fragment != null) {
                FragmentManager frM = getFragmentManager();
                frM.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }else{
                Toast.makeText(this, "problème de selection dans le menu.", Toast.LENGTH_SHORT).show();
            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkPermissions(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1052);

        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1052: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED ){

                    // permission was granted.

                } else {


                    // Permission denied - Show a message to inform the user that this app only works
                    // with these permissions granted

                }
                return;
            }

        }
    }


}
