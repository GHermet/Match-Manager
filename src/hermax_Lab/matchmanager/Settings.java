package hermax_Lab.matchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Settings extends Activity {
    TextView team1_nbplayers;
    TextView team2_nbplayers;
    SeekBar team1_seekbar;
    SeekBar team2_seekbar;
    EditText team1;
    EditText team2;
    EditText referee;
    Button next;
    SQLiteDatabase db;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        // affichage du xml (partie graphique)
        View viewInflatedFromXml = inflater.inflate(R.layout.football_newmatch_layout, null);
        setContentView(viewInflatedFromXml);
        // ouverture de la base de données
        db = openOrCreateDatabase("database", MODE_PRIVATE, null);
        // identification des elements du xml (partie graphique)
        team1_seekbar = (SeekBar) this.findViewById(R.id.Team1_seekbar);
        team2_seekbar = (SeekBar) this.findViewById(R.id.Team2_seekbar);
        team1 = (EditText) this.findViewById(R.id.Team1_text);
        team2 = (EditText) this.findViewById(R.id.Team2_text);
        next = (Button) this.findViewById(R.id.next_button);
        team1_nbplayers = (TextView) this.findViewById(R.id.team1_nbplayers);
        team2_nbplayers = (TextView) this.findViewById(R.id.team2_nbplayers);
        referee = (EditText) this.findViewById(R.id.referee_text);

        // listener pour détecter les variations de la seekbar 1
        team1_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar team1_seekbar, int progress, boolean fromUser) {
                // affichage de la valeur de la seekbar au cours des évènements
                team1_nbplayers.setText(String.valueOf(progress) + " Players");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }


        });
        // listener pour detecter les variations de la seekbar 2
        team2_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar team1_seekbar, int progress, boolean fromUser) {
                // affichage de la valeur de la seekbar au cours des évènements
                team2_nbplayers.setText(String.valueOf(progress) + " Players");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }


        });



        next.setOnClickListener(new View.OnClickListener() {

            String t1;
            String t2;
            String r;
            int n1;
            int n2;

            @Override
            public void onClick(View v) {

                    // reccueil des paramètres
                    t1 = String.valueOf(team1.getText());
                    t2 = String.valueOf(team2.getText());
                    r = String.valueOf(referee.getText());
                    n1 = team1_seekbar.getProgress();
                    n2 = team2_seekbar.getProgress();


                    // ecritures des paramètres dans les fichiers intermédiaires
                    writeToFile(t1,"Team 1.txt");
                    writeToFile(t2,"Team 2.txt");
                    writeToFile(r,"Referee.txt");
                    writeToFile(String.valueOf(n1),"Team 1 numb.txt");
                    writeToFile(String.valueOf(n2),"Team 2 numb.txt");

                    // condition de remplissage de tous les paramètres
                    if(readFromFile("Team 1.txt")==""||readFromFile("Team 2.txt")==""||readFromFile("Team 1 numb.txt")==""||readFromFile("Team 2 numb.txt")==""){
                        // affichage message erreur nom des équipes et nombre de joueurs manquants
                        Toast.makeText(getApplicationContext(),"Set the Teams' name and the number of players", Toast.LENGTH_LONG).show();
                    }else{
                        // retour aux Tabs
                        final Intent i0 = new Intent(Settings.this, tab_Activity.class);
                        startActivity(i0);

                        // affichage sauvegarde des paramètres
                        Toast.makeText(getApplicationContext(), "Settings saved", Toast.LENGTH_LONG).show();// affichage message temporaire




                }

            }

        });
    }

        private void writeToFile(String data, String F) {
            // écriture dans un fichier
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(F,Context.MODE_PRIVATE));
                outputStreamWriter.write(data);
                outputStreamWriter.close();
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), "File write failed", Toast.LENGTH_LONG).show();
            }

        }

        private String readFromFile(String F) {
            // lecture d'un fichier

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
                // affichage message d'erreur fichier introuvable
                Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                // affichage message d'erreur fichier illisible
                Toast.makeText(getApplicationContext(), "Can not read file", Toast.LENGTH_LONG).show();
            }

            return ret;
        }
    }



