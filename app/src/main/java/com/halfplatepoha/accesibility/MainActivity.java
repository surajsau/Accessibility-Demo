package com.halfplatepoha.accesibility;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_BIND_ACCESSIBILITY = 1001;
    private static final int REQUEST_TTS_AVAILABILITY = 1002;

    private Button btnPermitAccessibility;
    private Button btnPermitOverlay;
    private Button btnDownloadHindi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        askAccessbilityPermission();

        btnPermitAccessibility = (Button) findViewById(R.id.btnPermitAccessibility);
        btnPermitOverlay = (Button) findViewById(R.id.btnPermitOverlay);
        btnDownloadHindi = (Button) findViewById(R.id.btnDownloadHindi);

        btnPermitAccessibility.setOnClickListener(this);
        btnPermitOverlay.setOnClickListener(this);
        btnDownloadHindi.setOnClickListener(this);
    }

    private void askAccessbilityPermission() {
//        if(!isAccessibilitySeriveEnabled()) {
//            int accessibilityPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_ACCESSIBILITY_SERVICE);
            int drawOverAppsPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);

            if (drawOverAppsPermissionCheck !=  PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                        REQUEST_BIND_ACCESSIBILITY);
            }
//        } else {
//            Toast.makeText(this, "Accessibility Granted", Toast.LENGTH_SHORT).show();
//        }
    }

    private boolean isAccessibilitySeriveEnabled() throws Exception {
        AccessibilityManager am = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> svcs = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for(AccessibilityServiceInfo svc : svcs) {
            if(TextUtils.equals(svc.getId(), "com.halfplatepoha.accesibility/.AccessibilityTestService")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPermitAccessibility: {
                startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), REQUEST_BIND_ACCESSIBILITY);
            }
            break;

            case R.id.btnPermitOverlay: {
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
            }
            break;

            case R.id.btnDownloadHindi: {
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
