package com.yenjay.android.fck;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import java.util.Random;
import static com.yenjay.android.fck.R.id.kurwaButton;
import android.os.Vibrator;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    String text = "";
    String badWord = "";
    int timesClicked=0;
    int repeated = 0;
    boolean isVibrating = true;
    Button mainButton = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        badWord = getString(R.string.bad_word);
        mainButton = (Button) findViewById(kurwaButton);
        mainButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "By YenJay and Psych0",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }



    public void onButtonClick(View view) {
        vibrate();
        if (mainButton.getText().charAt(0) == getString(R.string.welcome_message).charAt(0)) {
            int[] letterArray = new int[badWord.length()];
            Random random = new Random();
            for (int i = 0; i < badWord.length(); i++) {
                letterArray[i] = random.nextInt(8) + 3;
            }
            randomText(letterArray);
            mainButton.setText(""+text.charAt(0));
            timesClicked=1;
        } else {
            if (timesClicked<text.length()){
                mainButton.setText(mainButton.getText()+ "" + text.charAt(timesClicked));
                timesClicked++;
            } else {
                if (repeated==0){
                    mainButton.setText(badWord);
                    repeated++;
                }
                else if (repeated <6){
                    mainButton.setText(mainButton.getText()+"\n"+badWord);
                    repeated++;
                } else {
                    repeated=0;
                    mainButton.setText(""+text.charAt(0));
                    timesClicked=1;
                }

            }
        }
    }

    public void randomText(int[] array) {
        for (int i = 0; i < badWord.length(); i++) {
            for (int j = 0; j < array[i]; j++) {
                text = text + badWord.charAt(i);
            }
        }

    }
    public void vibrate(){
        if (isVibrating) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(30);
        }
    }
    public void restartClick(View view){
        mainButton.setText(getString(R.string.welcome_message));
        timesClicked=0;
        repeated=0;
        text="";
    }
    public void vibrationClick(View view){
        String msg="";
        isVibrating=!isVibrating;
        if (isVibrating) {msg = "On";} else {msg = "Off";}
        Toast.makeText(getApplicationContext(), "Vibrations " + msg, Toast.LENGTH_SHORT).show();
        }


    public void shareButtonClicked(View view){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.yenjay.android.fck");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}