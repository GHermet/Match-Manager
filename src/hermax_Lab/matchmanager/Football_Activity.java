package hermax_Lab.matchmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Football_Activity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.football_layout);
		 ListView football_list = (ListView) this.findViewById(R.id.football_menu);
        // affichage du sport en sous titre dans la barre d'action
        ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(readFromFile("Sport.txt"));

        //reinitialisation des fichiers textes intermediaires
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
		

		 final Intent i3 = new Intent(Football_Activity.this, Football_previousMatches.class);
		 final Intent i0 = new Intent(Football_Activity.this, tab_Activity.class);

        // action du menu Football
		 football_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
					case 0: startActivity(i0); break; // nouveau match
					case 1:  startActivity(i3); break; // grille des matchs

				}}
		});


		

	
 
}
    private void writeToFile(String data, String F) {
        // ecrire dans un fichier
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(F, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            // affichage erreur d'ecriture
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
