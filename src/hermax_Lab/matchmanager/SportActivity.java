package hermax_Lab.matchmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Guillaume Hermet on 5/2/14.
 */
public class SportActivity extends Activity {
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // reinitialisation des fichiers intermediaires
        writeToFile("","Team 1.txt");
        writeToFile("","Team 2.txt");
        writeToFile("","Team 1 numb.txt");
        writeToFile("","Team 2 numb.txt");
        writeToFile("","Score Team 1.txt");
        writeToFile("","Score Team 2.txt");
        writeToFile("","Team 1 players.txt");
        writeToFile("","Team 2 players.txt");
        writeToFile("","Duration.txt");
        writeToFile("","Referee.txt");
        writeToFile("","Sport.txt");

        setContentView(R.layout.activity_main);// affichage du xml (partie graphique)
        db = openOrCreateDatabase("database", MODE_PRIVATE, null);// ouverture de la base de donnees
        db.execSQL("CREATE TABLE IF NOT EXISTS MatchTable(sport VARCHAR,team_1 VARCHAR,score_1 INTEGER,score_2 INTEGER,team_2 VARCHAR, duration VARCHAR, referee VARCHAR);");// creation de la table des matchs
        db.close();// fermeture base de donnees
        // identification des elements du xml (partie graphique)
        ImageButton football = (ImageButton) this.findViewById(R.id.football_button);
        ImageButton basketball = (ImageButton) this.findViewById(R.id.basketball_button);
        ImageButton volleyball = (ImageButton) this.findViewById(R.id.volleyball_button);
        ImageButton rugby = (ImageButton) this.findViewById(R.id.rugby_button);
       // ImageButton tennis = (ImageButton) this.findViewById(R.id.tennis_button);
        ImageButton pingpong = (ImageButton) this.findViewById(R.id.pingpong_button);
        ImageButton badminton = (ImageButton) this.findViewById(R.id.badminton_button);



        // definition des actions pour chaque bouton :
        football.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // aller au menu football
                Intent i1 = new Intent(SportActivity.this, Football_Activity.class);
                startActivity(i1);
                // ecrire le sport dans le fichier
                writeToFile("Football", "Sport.txt");


            }
        });

        basketball.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // aller au menu basketball
                Intent i1 = new Intent(SportActivity.this, Football_Activity.class);
                startActivity(i1);
                writeToFile("Basketball", "Sport.txt");

            }
        });

        volleyball.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // aller au menu volleyball
                Intent i1 = new Intent(SportActivity.this, Football_Activity.class);
                startActivity(i1);
                writeToFile("Volleyball", "Sport.txt");

            }
        });

        rugby.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // aller au menu rugby
                Intent i1 = new Intent(SportActivity.this, Football_Activity.class);
                startActivity(i1);
                writeToFile("Rugby", "Sport.txt");

            }
        });


        pingpong.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // aller au menu ping pong
                Intent i1 = new Intent(SportActivity.this, Football_Activity.class);
                startActivity(i1);
                writeToFile("Table Tennis", "Sport.txt");

            }
        });

        badminton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // aller au menu badminton
                Intent i1 = new Intent(SportActivity.this, Football_Activity.class);
                startActivity(i1);
                writeToFile("Badminton", "Sport.txt");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // creéer le menu des options dans la barre d'action
        getMenuInflater().inflate(R.menu.main, menu);

return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // action du bouton "about"
        switch (item.getItemId()) {
            case R.id.item1:
                // creation message information
                new AlertDialog.Builder(this)
                        .setTitle("About")
                        .setMessage("Match Manager 2.3\nDev team : Hermet Guillaume\n\t\t\t\t\t\t\tGuibaud Nicolas")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // retour au menu des sports
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void writeToFile(String data, String F) {
    // ecrire dans un fichier
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(F, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File write failed", Toast.LENGTH_LONG).show();
        }

    }

    private String readFromFile(String F) {
    // lire un fichier

        String ret = "";


        try {
            InputStream inputStream = openFileInput(F);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            // affichage erreur  fichier non trouvé
            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // affichage erreur  fichier illisible
            Toast.makeText(getApplicationContext(), "Can not read file", Toast.LENGTH_LONG).show();
        }

        return ret;
    }
}
