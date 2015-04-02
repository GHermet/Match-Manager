package hermax_Lab.matchmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;



public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.animation);




         ImageView Img = (ImageView) this.findViewById(R.id.imageView);
        //affichage du logo pendant 5 secondes,
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
               // demarrage menu des sports
                Intent i = new Intent(MainActivity.this,SportActivity.class);
                startActivity(i);

            }
        }.start();


    }


}
