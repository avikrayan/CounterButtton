package com.example.avik.counterbuttton;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    private static final long REP_DELAY = 50;
    private TextView showCount;
    private Button btnPlus, btnMinus;

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    public int mValue = 0;
    public int minValue = 0;
    public int maxValue = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {

        showCount = (TextView) findViewById(R.id.tvShowCount);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);

        btnPlus.setOnClickListener(this);
        btnPlus.setOnLongClickListener(this);
        btnPlus.setOnTouchListener(this);

        btnMinus.setOnClickListener(this);
        btnMinus.setOnLongClickListener(this);
        btnMinus.setOnTouchListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnPlus:

                if (mValue >= maxValue){
                    return;
                }else {
                    showCount.setText(String.valueOf(++mValue));
                }
                break;
            case R.id.btnMinus:

                if (mValue <= minValue){
                    return;
                }else {
                    showCount.setText(String.valueOf(-- mValue));
                }
                break;
        }

    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {

            case R.id.btnPlus:

                mAutoIncrement = true;
                repeatUpdateHandler.post( new RptUpdater() );
                break;
            case R.id.btnMinus:

                mAutoDecrement = true;
                repeatUpdateHandler.post( new RptUpdater() );
                break;
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (view.getId()) {

            case R.id.btnPlus:

                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement ){
                    mAutoIncrement = false;
                }

                break;
            case R.id.btnMinus:

                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement ){
                    mAutoDecrement = false;
                }

                break;
        }
        return false;
    }


    class RptUpdater implements Runnable {
        public void run() {
            if( mAutoIncrement ){
                increment();
                repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
            } else if( mAutoDecrement ){
                decrement();
                repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
            }
        }
    }

    private void decrement() {

        if (mValue <= minValue){
            return;
        }else {
            showCount.setText("" + --mValue);
        }
    }

    private void increment() {
        if (mValue >= maxValue){
            return;
        }else {
            showCount.setText("" + ++mValue);
        }
    }
}
