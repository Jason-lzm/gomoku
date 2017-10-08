package com.lzm.gomoku.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzm.gomoku.R;
import com.lzm.gomoku.common.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mSingleBtn;
    private Button mMutilBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSingleBtn = (Button) findViewById(R.id.singlePlayer);
        mMutilBtn = (Button) findViewById(R.id.multiPlayer);

        mSingleBtn.setOnClickListener(this);
        mMutilBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, GameActivity.class);
        switch (v.getId()){
            case R.id.singlePlayer:
                intent.putExtra("model", Constants.MODEL.SINGLE);
                startActivity(intent);
                break;
            case R.id.multiPlayer:
                intent.putExtra("model", Constants.MODEL.MULTI);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
