package com.halfplatepoha.accesibility;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.halfplatepoha.accesibility.flipkart.FlipkartLoginStages;
import com.halfplatepoha.accesibility.util.IConstants;
import com.halfplatepoha.accesibility.util.Utils;

/**
 * Created by surajkumarsau on 18/06/16.
 *
 * Transparent Background activity that is used to show Dialog with options
 */
public class HelperActivity extends AppCompatActivity implements IConstants, View.OnClickListener, TextToSpeech.OnInitListener {

    private static final String TAG = HelperActivity.class.getSimpleName();

    private Button btnChoice1;
    private Button btnChoice2;

    /**
     * The current stage in the flow during which this activity opens
     */
    private FlipkartLoginStages mStage;

    /**
     * On Clicking an option sometimes, you would need to show indicator on a view on the current
     * screen itself. Since, the TYPE_WINDOW_CONTENT_CHANGED isn't called again on the screen you
     * cannot get the node again. Hence, passing the next to be indicated node using intent.
     */
    private Rect toBeIndicatedRect;

    /**
     * TextToSpeech will be work for most of the phones as they use language packs
     * Google provide. However, MI uses a different TTS altogether. So, better approach
     * would be to record voices and play audio files instead
     */
    private TextToSpeech mTts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //--required to prevent activity from showing any title at all
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getDataFromIntent();
        setContentView(R.layout.activity_helper);

        initResources();
        setTextInButtons();

        speak();
    }

    private void speak() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mTts.speak(Utils.getSpeechString(mStage), TextToSpeech.QUEUE_FLUSH, null, null);
        else
            mTts.speak(Utils.getSpeechString(mStage), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void getDataFromIntent() {
        if(getIntent() != null) {
            mStage = (FlipkartLoginStages) getIntent().getSerializableExtra(SOURCE_STAGE);
            toBeIndicatedRect = getIntent().getParcelableExtra(RECT_TO_BE_INDICATED);
        }
    }

    private void initResources() {
        mTts = new TextToSpeech(this, this);

        btnChoice1 = (Button) findViewById(R.id.btnChoice1);
        btnChoice2 = (Button) findViewById(R.id.btnChoice2);

        btnChoice1.setOnClickListener(this);
        btnChoice2.setOnClickListener(this);
    }

    private void setTextInButtons() {
        btnChoice1.setText(Utils.getFirstChoice(mStage));
        btnChoice2.setText(Utils.getSecondChoice(mStage));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChoice1:{
                Intent choiceIntent = new Intent(HelperActivity.this, AccessibilityTestService.class);
                choiceIntent.putExtra(CHOICE_RESULT, Utils.getFirstChoiceResult(mStage));
                choiceIntent.putExtra(RECT_TO_BE_INDICATED, toBeIndicatedRect);
                startService(choiceIntent);
                finish();
            }
            break;

            case R.id.btnChoice2: {
                Intent choiceIntent = new Intent(HelperActivity.this, AccessibilityTestService.class);
                choiceIntent.putExtra(CHOICE_RESULT, Utils.getSecondChoiceResult(mStage));
                choiceIntent.putExtra(RECT_TO_BE_INDICATED, toBeIndicatedRect);
                startService(choiceIntent);
                finish();
            }
            break;
        }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
        } else {
            Log.e(TAG, "Couldn't initialize text to speech");
        }
    }

    @Override
    protected void onPause() {
        if(mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onPause();
    }
}
