package hermax_Lab.matchmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Football_previousMatches extends Activity {

	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
           // ouverture base de donneées
		   db = openOrCreateDatabase("database",MODE_PRIVATE, null);
		   
		super.onCreate(savedInstanceState);
		setContentView(R.layout.football_previousmatches_layout);
        // affichage du sport en sous titre
        ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(readFromFile("Sport.txt"));
        String S = readFromFile("Sport.txt");
		Cursor c=db.rawQuery("SELECT team_1,score_1,score_2,team_2,duration,referee FROM MatchTable WHERE sport='"+S+"'", null); // selection de toute la table
	     int count= c.getCount();
	    c.moveToFirst();
      // creation du layout
	    TableLayout tableLayout = (TableLayout) this.findViewById(R.id.tlGridTable);
	    tableLayout.setVerticalScrollBarEnabled(true);
	    tableLayout.setHorizontalScrollBarEnabled(true);
        // creation de la grille
	   TableRow tableRow;
	   TextView textView,textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8;
	   tableRow = new TableRow(getApplicationContext());

// Insertion des titres de chaque colonne

	    textView=new TextView(getApplicationContext());
	    textView.setText("Team 1");
	    textView.setTextColor(Color.WHITE);
	  	textView.setTypeface(null, Typeface.BOLD);
	  	textView.setPadding(20, 20, 20, 20);
	  	tableRow.addView(textView);
	    textView4=new TextView(getApplicationContext());
	  	textView4.setText(" ");
	  	textView4.setTextColor(Color.WHITE);
	  	textView4.setTypeface(null, Typeface.BOLD);
	  	 textView4.setPadding(20, 20, 20, 20);
	  	tableRow.addView(textView4);
	    textView5=new TextView(getApplicationContext());
	  	textView5.setText(" ");
	  	textView5.setTextColor(Color.WHITE);
	  	textView5.setTypeface(null, Typeface.BOLD);
	  	textView5.setPadding(20, 20, 20, 20);
	  	tableRow.addView(textView5);
	  	textView6=new TextView(getApplicationContext());
	  	textView6.setText("Team 2");
	  	textView6.setTextColor(Color.WHITE);
	  	textView6.setTypeface(null, Typeface.BOLD);
	  	textView6.setPadding(20, 20, 20, 20);
	  	tableRow.addView(textView6);
	  	textView7=new TextView(getApplicationContext());
		textView7.setText("Duration");
	  	textView7.setTextColor(Color.WHITE);
	  	textView7.setTypeface(null, Typeface.BOLD);
	  	textView7.setPadding(20, 20, 20, 20);
	  	tableRow.addView(textView7);
	  	textView8=new TextView(getApplicationContext());
		textView8.setText("Referee");
	  	textView8.setTextColor(Color.WHITE);
	  	textView8.setTypeface(null, Typeface.BOLD);
	  	textView8.setPadding(20, 20, 20, 20);
	  	tableRow.addView(textView8);
	   tableLayout.addView(tableRow);
	   
	     for (Integer j = 0; j < count; j++)
	     {// Insertion des informations dans la table
	         tableRow = new TableRow(getApplicationContext());

	         textView1 = new TextView(getApplicationContext());
	         textView1.setText(c.getString(c.getColumnIndex("team_1")));
	     	 textView1.setTextColor(Color.WHITE);
	         textView2 = new TextView(getApplicationContext());
	         textView2.setText(c.getString(c.getColumnIndex("score_1")));
	         textView2.setTextColor(Color.WHITE);
	         textView3 = new TextView(getApplicationContext());
	         textView3.setText(c.getString(c.getColumnIndex("score_2")));
	         textView3.setTextColor(Color.WHITE);
	         textView4 = new TextView(getApplicationContext());
	         textView4.setText(c.getString(c.getColumnIndex("team_2")));
	         textView4.setTextColor(Color.WHITE);
	         textView5 = new TextView(getApplicationContext());
	         textView5.setText(c.getString(c.getColumnIndex("duration")));
	         textView5.setTextColor(Color.WHITE);
	         textView6 = new TextView(getApplicationContext());
	         textView6.setText(c.getString(c.getColumnIndex("referee")));
	         textView6.setTextColor(Color.WHITE);
	         textView1.setPadding(20, 20, 20, 20);
	         textView2.setPadding(20, 20, 20, 20);
	         textView3.setPadding(20, 20, 20, 20);
	         textView4.setPadding(20, 20, 20, 20);
	         textView5.setPadding(20, 20, 20, 20);
	         textView6.setPadding(20, 20, 20, 20);
	         tableRow.addView(textView1);
	         tableRow.addView(textView2);
	         tableRow.addView(textView3);
	         tableRow.addView(textView4);
	         tableRow.addView(textView5);
	         tableRow.addView(textView6);

	         tableLayout.addView(tableRow);
	         c.moveToNext() ;
	     }
// fermeture base de données
	     db.close();
	
      
		
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // creation du menu optionel pour supprimer les matchs de la grille
        getMenuInflater().inflate(R.menu.match_grid_search, menu);



        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // action du bouton "Clear Match Grid"
        switch (item.getItemId()) {
            case R.id.item1:
                // creation message d'alerte pour supprimer oui ou non les matchs de la grille (nominativement par sport)
                new AlertDialog.Builder(this)
                        .setTitle("Match Grid")
                        .setMessage("Clear "+readFromFile("Sport.txt")+" Match Grid ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               db = openOrCreateDatabase("database",MODE_PRIVATE, null);
                               db.delete("MatchTable","sport='"+readFromFile("Sport.txt")+"'",null);
                               db.close();

                                Intent i = new Intent(Football_previousMatches.this,Football_Activity.class);
                                startActivity(i);
                                // message infomation grille vide
                                Toast.makeText(getApplicationContext(), "Match Grid Cleared", Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // retour a la grille des matchs
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }



    }
}
