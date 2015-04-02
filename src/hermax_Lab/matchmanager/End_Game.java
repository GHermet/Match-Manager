package hermax_Lab.matchmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class End_Game extends Activity {
    private TextView team1;
    private TextView team2;
    private TextView score1;
    private TextView score2;
    private TextView duration;
    private EditText phone;
    private Button OK;
    private ImageButton sendSMS;
    private SQLiteDatabase db;
    private String s1;
    private String s2;
    private String D;
    private String t1;
    private String t2;
    private String r;
    private String S;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_game_layout);
        // affichage du sport en sous titre
        ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(readFromFile("Sport.txt"));
        // identification des elements du xml (partie graphique)
        team1 = (TextView) this.findViewById(R.id.team1_endgame);
        team2 = (TextView) this.findViewById(R.id.team2_endgame);
        score1 = (TextView) this.findViewById(R.id.score1_endgame);
        score2 = (TextView) this.findViewById(R.id.score2_endgame);
        duration = (TextView) this.findViewById(R.id.duration_endgame);
        phone = (EditText) this.findViewById(R.id.phonenumb_text);
        OK = (Button) this.findViewById(R.id.ok_button);
        sendSMS = (ImageButton) this.findViewById(R.id.sendSMS_button);

        // affichage des informations du match
        team1.setText(readFromFile("Team 1.txt"));
        team2.setText(readFromFile("Team 2.txt"));
        score1.setText(readFromFile("Score Team 1.txt"));
        score2.setText(readFromFile("Score Team 2.txt"));
        duration.setText(readFromFile("Duration.txt"));

        // reccueil de toutes les informations du match
        t1 = readFromFile("Team 1.txt");
        t2 = readFromFile("Team 2.txt");
        s1 = readFromFile("Score Team 1.txt");
        s2 = readFromFile("Score Team 2.txt");
        r = readFromFile("Referee.txt");
        D = readFromFile("Duration.txt");
        S = readFromFile("Sport.txt");

        // listener pour sauvegarder dans la base de données le match en appuyant sur le bouton OK
		OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // ouverture de la base de données
                db = openOrCreateDatabase("database",MODE_PRIVATE, null);
                // insertion des informations dans la grille des matchs
                db.execSQL("INSERT INTO  MatchTable VALUES('"+S+ "','" +t1+ "','" +s1+ "','" +s2+ "','" + t2 + "','" +D+ "','" + r + "');");
                db.close();
                // sauvegarde dans la carte SD du match
                SaveToSD(t1+" - "+t2);

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
                // retour au menu des sports

                Intent i0 = new Intent(End_Game.this, SportActivity.class);
                startActivity(i0);
            }
        });
// listener pour envoyer par SMS les scores finaux du match a une tiers personne en appuyant sur le bouton Send
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String PhoneNumber = String.valueOf(phone.getText());

                    SMS(S+": "+t1+" "+s1+" - "+s2+" "+t2+" sent by Match Manager",PhoneNumber);
                    phone.setText("");

            }
        });
	}





    private void writeToFile(String data, String F) {
        // ecriture dans un fichier
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
            // message erreur fichier introuvable
            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // message erreur fichier illisible
            Toast.makeText(getApplicationContext(), "Can not read file", Toast.LENGTH_LONG).show();
        }

        return ret;
    }

    private void SMS (String text, String phonenumb){
        // envoie d'un message par SMS
        if(phonenumb.equals("")){
            Toast.makeText(getApplicationContext(), "Failed to send SMS", Toast.LENGTH_LONG).show();
        }
        else{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumb, null, text, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();

        }

    }

    private void SaveToSD(String F){
        // Sauvegarde d'un texte dans la carte SD du telephone
        try {
            String filename = F;
            File myFile = new File(Environment
                    .getExternalStorageDirectory(),F);
            if (!myFile.exists())
                try {
                    myFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            FileOutputStream fos;
            String string=S+"\n"+t1+"\n"+s1+"\n"+s2+"\n"+t2+"\n"+D+"\n"+r+"\n";
            byte[] data = string.getBytes();

                fos = new FileOutputStream(myFile);
                fos.write(data);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



	




