package wrckngcrw.metronome;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button mStartButton;
    EditText mTempo;
    Spinner mSpinner_Numer;
    Spinner mSpinner_Denom;
    Timer mTimer;

    Handler mHandler = new Handler();

    private void getContentsInfo() {
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer == null) {
                    ((Button) findViewById(R.id.start_button)).setText("Stop");
                    mTempo.setEnabled(false);
                    mSpinner_Numer.setEnabled(false);
                    mSpinner_Denom.setEnabled(false);
                    mTimer = new Timer();
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(cursor.isLast()) {
                                        cursor.moveToFirst();
                                    } else {
                                        cursor.moveToNext();
                                    }
                                    int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                    Long id = cursor.getLong(fieldIndex);
                                    Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                    ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
                                    imageVIew.setImageURI(imageUri);
                                }
                            });
                        }
                    }, 60 / mTempo * mSpinner_Denom *1000, 60 / mTempo * mSpinner_Denom *1000);
                } else {
                    mTimer.cancel();
                    mTimer = null;
                    ((Button) findViewById(R.id.start_button)).setText("再生");
                    mTempo.setEnabled(true);
                    mSpinner_Numer.setEnabled(true);
                    mSpinner_Denom.setEnabled(true);
                }
            }
        });

    }
}