package hermax_Lab.matchmanager;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class tab_Activity extends TabActivity {
    TabHost tabhost;
    private Intent i1;
    private Intent i2;
    private Intent i3;
    private TabSpec tabSpecSettings;
    private TabSpec tabSpecPlayers;
    private TabSpec tabSpecMatch;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);
        setTabContent();
        // affichage du sport en sous titre
        actionBar = getActionBar();
        actionBar.setSubtitle(readFromFile("Sport.txt"));



    }

    private void setTabContent(){
        try {
            tabhost = getTabHost();
            //  creation tab Settings
            i1 = new Intent().setClass(this,Settings.class);
            tabSpecSettings = tabhost
                    .newTabSpec("Settings")
                    .setIndicator("settings")
                    .setContent(i1);

            tabhost.addTab(tabSpecSettings);

            //  creation tab Players
            i2 = new Intent().setClass(this, Players.class);
            tabSpecPlayers = tabhost
                    .newTabSpec("Players")
                    .setIndicator("players")
                    .setContent(i2);

            tabhost.addTab(tabSpecPlayers);

            // creation tab Match
            i3 = new Intent().setClass(this, Match.class);
            tabSpecMatch = tabhost
                    .newTabSpec("Match")
                    .setIndicator("match")
                    .setContent(i3);

            tabhost.addTab(tabSpecMatch);

            tabhost.setCurrentTab(0);
        }
    catch(Exception e)
    {
        System.out.println(e.toString());
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
