package com.algonquincollege.lach0192.hsvcolorpicker;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * HSV Color Picker: Simple color picker app using Hue, Saturation & Brightness values.
 *
 * @author Eric Lachapelle (lach0192@algonquinlive.com)
 */

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    public float hueVal = 0;
    public float saturationVal = 0;
    public float brightnessVal = 0;
    public float[] hsv = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar hueSB = findViewById(R.id.hueSB);
        hueSB.setOnSeekBarChangeListener(new onHueUpdate());

        final SeekBar saturationSB = findViewById(R.id.saturationSB);
        saturationSB.setOnSeekBarChangeListener(new onSaturationUpdate());

        final SeekBar brightnessSB = findViewById(R.id.brightnessSB);
        brightnessSB.setOnSeekBarChangeListener(new onBrightnessUpdate());

        final TextView colorSwatch = findViewById(R.id.colorSwatch);
        colorSwatch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                String msg = "H: " + hsv[0] + "\u00b0 S: " + (hsv[1] * 100) + "% V: " + (hsv[2] * 100) + "%";

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    public void btnClicked(View v) {

        // background color of button
        ColorDrawable buttonColor = (ColorDrawable) v.getBackground();
        int colorId = buttonColor.getColor();

        // convert to hsv values
        Color.colorToHSV(colorId, hsv);

        Log.i("lach0192", "Hue: " + hsv[0] + ", Sat: " + hsv[1] + ", Val: " + hsv[2]);

        SeekBar huePos = findViewById(R.id.hueSB);
        SeekBar satPos = findViewById(R.id.saturationSB);
        SeekBar valPos = findViewById(R.id.brightnessSB);

        // Update SeekBar positions
        huePos.setProgress((int) hsv[0]);
        satPos.setProgress((int) (hsv[1] * 100));
        valPos.setProgress((int) (hsv[2] * 100));

        updateColor();

        TextView hueLabel = findViewById(R.id.hue);
        TextView satLabel = findViewById(R.id.saturation);
        TextView valLabel = findViewById(R.id.brightness);

        hueLabel.setText(R.string.hue);
        satLabel.setText(R.string.saturation);
        valLabel.setText(R.string.brightness);

        String msg = "H: " + hsv[0] + "\u00b0 S: " + (hsv[1] * 100) + "% V: " + (hsv[2] * 100) + "%";

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private class onHueUpdate implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            hueVal = progress;
            Log.i("lach0192", "Hue Value: " + hueVal);

            hsv[0] = hueVal;

            //--------------------------------------------------
            TextView hueLabel = findViewById(R.id.hue);

            String hueLabelText = String.valueOf(hueVal) + "%";

            hueLabel.setText(hueLabelText);
            //--------------------------------------------------

            updateColor();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {

            TextView hueLabel = findViewById(R.id.hue);
            hueLabel.setText(R.string.hue);
        }

    }

    private class onSaturationUpdate implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            saturationVal = progress;
            saturationVal = saturationVal / 100;
            Log.i("lach0192", "Saturation Value: " + saturationVal);

            hsv[1] = saturationVal;

            //--------------------------------------------------
            TextView saturationLabel = findViewById(R.id.saturation);

            String saturationLabelText = String.valueOf(saturationVal * 100) + "%";

            saturationLabel.setText(saturationLabelText);
            //--------------------------------------------------

            updateColor();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {

            TextView saturationLabel = findViewById(R.id.saturation);
            saturationLabel.setText(R.string.saturation);
        }

    }

    private class onBrightnessUpdate implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            brightnessVal = progress;
            brightnessVal = brightnessVal / 100;
            Log.i("lach0192", "Brightness Value: " + brightnessVal);

            hsv[2] = brightnessVal;

            //--------------------------------------------------
            TextView brightnessLabel = findViewById(R.id.brightness);

            String brightnessLabelText = String.valueOf(brightnessVal * 100) + "%";

            brightnessLabel.setText(brightnessLabelText);
            //--------------------------------------------------

            updateColor();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {

            TextView brightnessLabel = findViewById(R.id.brightness);
            brightnessLabel.setText(R.string.brightness);
        }

    }

    public void updateColor() {

        TextView colorSwatch = findViewById(R.id.colorSwatch);

        int swatchColor = Color.HSVToColor(hsv);

        colorSwatch.setBackgroundColor(swatchColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
