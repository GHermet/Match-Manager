package hermax_Lab.matchmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Match extends Activity {

    ImageButton start;
    ImageButton pause;
    NumberPicker team1_score;
    NumberPicker team2_score;
    TextView team1_match;
    TextView team2_match;
    TextView Sport;
    Button end_game;
    Button cancel;
    Chronometer chrono;
    private int s1;
    private int s2;
    private String D;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        // affichage du xml (partie graphique)
        View viewInflatedFromXml = inflater.inflate(R.layout.football_match, null);
        setContentView(viewInflatedFromXml);

         // identification des elements du xml (partie graphique)
            start = (ImageButton) this.findViewById(R.id.play_button);
            pause = (ImageButton) this.findViewById(R.id.pause_button);

            team1_score= (NumberPicker) this.findViewById(R.id.team1_score);
            team2_score = (NumberPicker) this.findViewById(R.id.team2_score);
            team1_match =(TextView) this.findViewById(R.id.match_team1);
            team2_match = (TextView) this.findViewById(R.id.match_team2);
            cancel =(Button) this.findViewById(R.id.cancel_button);
            end_game = (Button) this.findViewById(R.id.end_game_Button);
            chrono = (Chronometer) this.findViewById(R.id.chronometer1);
            Sport = (TextView) this.findViewById(R.id.Sport);

            team1_score.setMinValue(0);
            team2_score.setMinValue(0);
            team1_score.setValue(0);
            team2_score.setValue(0);
            team1_score.setMaxValue(200);
            team2_score.setMaxValue(200);

       // affichage du sport
        Sport.setText(readFromFile("Sport.txt"));

        // affichage du nom des équipes
        team1_match.setText(readFromFile("Team 1.txt"));
        team2_match.setText(readFromFile("Team 2.txt"));
// listener pour annuler le match en appuyant sur cancel
   cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // creation message d'alerte
        new AlertDialog.Builder(Match.this)
                .setTitle("Cancel")
                .setMessage("Cancel Match ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Match.this,SportActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // revient au match
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
});

// listener pour remettre a zero le chrono en restant appuyé dessus
   chrono.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        // creation message d'alerte
        new AlertDialog.Builder(Match.this)
                .setTitle("Reset")
                .setMessage("Reset Chrono ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        chrono.setBase(SystemClock.elapsedRealtime());
                        chrono.stop();
                    }
                })
                .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        return false;
    }
});
        // listener pour terminer le match en appuyant sur End Game
            end_game.setOnClickListener(new View.OnClickListener() {





                @Override
                public void onClick(View v) {
// condition pour que les noms d'équipes soient renseignées
                    if(readFromFile("Team 1.txt")==""||readFromFile("Team 2.txt")==""){
                        Toast.makeText(getApplicationContext(), "Set the Settings first", Toast.LENGTH_LONG).show();
                    }
                    else{
                        // reccueil du temps et des scores
                        D = String.valueOf(chrono.getText());
                        s1=team1_score.getValue();
                        s2=team2_score.getValue();
                        // ecriture des informations dans les fichiers intermediaires
                        writeToFile(String.valueOf(team1_score.getValue()),"Score Team 1.txt");
                        writeToFile(String.valueOf(team2_score.getValue()),"Score Team 2.txt");
                        writeToFile(String.valueOf(s1),"Score team 1.txt");
                        writeToFile(String.valueOf(s2),"Score team 2.txt");
                        writeToFile(D,"Duration.txt");

                        // demarrage de l'activité finale End_Game
                        Intent i1 = new Intent(Match.this, End_Game.class);
                        startActivity(i1);
                    }




                }


            });

// listener pour demarrer le chrono en appuyant sur le bouton play
            start.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int stoppedMilliseconds = 0;

                    String chronoText = chrono.getText().toString();
                    String array[] = chronoText.split(":");
                    // sauvegarde en temps réel de la valeur du chrono pour reprendre le chrono la ou il s'est arreté
                    if (array.length == 2) {
                        stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                                + Integer.parseInt(array[1]) * 1000;
                    } else if (array.length == 3) {
                        stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                                + Integer.parseInt(array[1]) * 60 * 1000
                                + Integer.parseInt(array[2]) * 1000;
                    }

                   chrono.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
                   chrono.start();




                }
            });
// listener pour stopper le chrono en appuyant sur le bouton pause
            pause.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    chrono.stop();

                }
            });


        }


    private void writeToFile(String data, String F) {
        // Ecriture dans un fichier
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
        // Lecture d'un fichier

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
            // message erreur fichier non trouvé
            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // message erreur fichier illisible
            Toast.makeText(getApplicationContext(), "Can not read file", Toast.LENGTH_LONG).show();
        }

        return ret;
    }

    }


