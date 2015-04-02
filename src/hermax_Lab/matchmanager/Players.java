package hermax_Lab.matchmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class Players extends Activity {
    ArrayList<String> team1 ;
    ArrayList<String> team2;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2 ;
    EditText player_name ;
    Button add_team1;
    Button add_team2;
    ImageButton check;
    ListView team1_list;
    ListView team2_list;
    SeekBar team1_seekbar;
    SeekBar team2_seekbar;
    String S;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        // affichage du xml (partie graphique)
        View viewInflatedFromXml = inflater.inflate(R.layout.football_newmatch_players_layout, null);
        setContentView(viewInflatedFromXml);
        // identification des elements du xml (partie graphique)
        team1 = new ArrayList<String>();
        team2 = new ArrayList<String>();
        team1_list = (ListView) this.findViewById(R.id.team1_list);
        team2_list = (ListView) this.findViewById(R.id.team2_list);
        player_name = (EditText) this.findViewById(R.id.playersname_text);
        add_team1 = (Button) this.findViewById(R.id.add_to_team1);
        add_team2 = (Button) this.findViewById(R.id.add_to_team2);
        check = (ImageButton) this.findViewById(R.id.checkPlayerslist_button);
        adapter2 =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,team2);
        adapter1 =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,team1);
        team1_list.setAdapter(adapter1);
        team2_list.setAdapter(adapter2);
        team1_seekbar = (SeekBar) this.findViewById(R.id.Team1_seekbar);
        team2_seekbar = (SeekBar) this.findViewById(R.id.Team2_seekbar);
        // affichage nom des équipes sur chaque bouton
        add_team1.setText(readFromFile("Team 1.txt"));
        add_team2.setText(readFromFile("Team 2.txt"));
        team1.trimToSize();
        team2.trimToSize();
       // listener sur le bouton add_team1 pour ajouter un joueur dans l'équipe 1
        add_team1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                S = String.valueOf(player_name.getText());

                if(team1.size()==Integer.valueOf(readFromFile("Team 1 numb.txt"))){
                    // affichaqe message erreur équipe complète
                    Toast.makeText(getApplicationContext(),readFromFile("Team 1.txt")+" team is full", Toast.LENGTH_LONG).show();
                }
                if (S.matches("")) {
                    // affichage message erreur pas de nom tapé
                    Toast.makeText(getApplicationContext(), "Type a name", Toast.LENGTH_LONG).show();
                }
                else {
                    // ajout du joueur dans la liste
                    team1.add(S);
                    add_team1.setText(readFromFile("Team 1.txt")+" ("+team1.size()+")");
                    adapter1.notifyDataSetChanged();
                    player_name.setText("");

                }


            }
        });

        // listener pour retirer un joueur de la liste 1 en restant appuyé dessus
        team1_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, final View Arg2,
                                           final int arg2, long arg3) {
// creation message d'alerte pour choisir de retirer les cartons ou de retirer le joueur
                new AlertDialog.Builder(Players.this)
                        .setTitle("Player Status")
                        .setMessage("Remove penalty card or Remove player ?")
                        .setPositiveButton("Remove Penalty Card", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                              Arg2.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                            }
                        })
                        .setNegativeButton("Remove player", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // retire le joueur de la liste 1
                                team1.remove(arg2);
                                add_team1.setText(readFromFile("Team 1.txt")+" ("+team1.size()+")");
                                // notification a la liste 1 pour se mettre a jour
                                adapter1.notifyDataSetChanged();
                            }
                        })
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



                return false;
            }



        });

        // listener pour mettre des cartons aux joueurs en appuyant sur leur nom
        team1_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, final View arg1, int arg2,
                                    long arg3) {
                // creation message d'alerte pour choisir de mettre un carton jaune ou rouge
                new AlertDialog.Builder(Players.this)
                        .setTitle("Penalty Card")
                        .setMessage("Which penalty card would you like to apply ?")
                        .setPositiveButton("Yellow", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final View arg= arg1;
                                arg.setBackgroundColor(Color.rgb(200,200,0));



                            }
                        })
                        .setNegativeButton("Red", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final View arg= arg1;
                                arg.setBackgroundColor(Color.RED);

                            }
                        })
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        });




        // listener sur le bouton add_team2 pour ajouter un joueur dans l'équipe 2
        add_team2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                S = String.valueOf(player_name.getText());

                if(team2.size()==Integer.valueOf(readFromFile("Team 2 numb.txt"))){
                    Toast.makeText(getApplicationContext(),readFromFile("Team 2.txt")+" team is full", Toast.LENGTH_LONG).show();
                }
                if (S.matches("")) {
                    // affichage message erreur pas de nom tapé
                    Toast.makeText(getApplicationContext(), "Type a name", Toast.LENGTH_LONG).show();
                }
                else {
                    // ajout du joueur dans la liste 2
                    team2.add(S);
                    add_team2.setText(readFromFile("Team 2.txt")+" ("+team2.size()+")");
                    adapter2.notifyDataSetChanged();
                    player_name.setText("");

                }

            }
        });

        // listener pour mettre des cartons aux joueurs en appuyant sur leur nom
        team2_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view1, int i, long l) {
                // creation message d'alerte pour choisir de mettre un carton jaune ou rouge
                new AlertDialog.Builder(Players.this)
                        .setTitle("Penalty Card")
                        .setMessage("Which penalty card would you like to apply ?")
                        .setPositiveButton("Yellow", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                view1.setBackgroundColor(Color.rgb(200, 200, 0));
                            }
                        })
                        .setNegativeButton("Red", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                view1.setBackgroundColor(Color.RED);
                            }
                        })
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });



        // listener pour retirer un joueur de la liste 2 en restant appuyé dessus
        team2_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, final View Arg,
                                           final int arg, long arg3) {
// creation message d'alerte pour choisir de retirer les cartons ou de retirer le joueur
                new AlertDialog.Builder(Players.this)
                        .setTitle("Player Status")
                        .setMessage("Remove penalty card or Remove player ?")
                        .setPositiveButton("Remove Penalty Card", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Arg.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                            }
                        })
                        .setNegativeButton("Remove player", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // retire le joueur de la liste 2
                                team2.remove(arg);
                                add_team2.setText(readFromFile("Team 2.txt")+" ("+team2.size()+")");
                                // notification a la liste 2 pour se mettre a jour
                                adapter2.notifyDataSetChanged();
                            }
                        })
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return false;
            }



        });
// listener pour enregistrer les équipes en appuyant sur le bouton check
        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writeToFile(ToString(team1),"Team 1 players");
                writeToFile(ToString(team2),"Team 2 players");
                Toast.makeText(getApplicationContext(), "Teams saved", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void writeToFile(String data, String F) {
        // Ecrire dans un fichier
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
        // Lire un fichier

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

    private String ToString(ArrayList<String> A){
        String S ="";
        int i=0;
       Iterator<String> iter = A.iterator();
        while(iter.hasNext()){
            S=S+iter+"/n";
            iter.next();
        }

       return S ;
    }
}
