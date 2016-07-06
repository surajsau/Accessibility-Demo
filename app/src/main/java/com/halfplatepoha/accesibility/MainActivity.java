package com.halfplatepoha.accesibility;

import android.content.Intent;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_BIND_ACCESSIBILITY = 1001;
    private static final int REQUEST_TTS_AVAILABILITY = 1002;

    private Button btnPermitAccessibility;
    private Button btnPermitOverlay;
    private Button btnCheckTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        askAccessbilityPermission();

        btnPermitAccessibility = (Button) findViewById(R.id.btnPermitAccessibility);
        btnPermitOverlay = (Button) findViewById(R.id.btnPermitOverlay);
        btnCheckTTS = (Button) findViewById(R.id.btnCheckTTS);

        btnPermitAccessibility.setOnClickListener(this);
        btnPermitOverlay.setOnClickListener(this);
        btnCheckTTS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPermitAccessibility: {
                //--open accessibility settings screen
                startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), REQUEST_BIND_ACCESSIBILITY);
            }
            break;

            case R.id.btnPermitOverlay: {
                //--open draw over screen permissons screen
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
            }
            break;

            case R.id.btnCheckTTS: {
                Intent checkIntent = new Intent();
                checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivityForResult(checkIntent, REQUEST_TTS_AVAILABILITY);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TTS_AVAILABILITY:{
                if(resultCode != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    Intent installIntent = new Intent();
                    installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installIntent);
                }
            }
            break;
        }
    }
}
