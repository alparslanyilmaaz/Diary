package com.example.diary;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Add extends AppCompatActivity {
    DatabaseThemeChooser theme;
    RelativeLayout relativeLayout;
    FloatingActionButton settings, close, share, bckColorPicker;
    DatabaseHelper db;
    String casualTheme = "";
    String passTransfer = "";
    EditText content, header;
    String cnt = "", hdr = "";
    int mTextDefaultColor, mBackgroundDefaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mTextDefaultColor = ContextCompat.getColor(Add.this, R.color.white);
        mBackgroundDefaultColor = ContextCompat.getColor(Add.this, R.color.defaultBack);

        content = (EditText) findViewById(R.id.things);
        header = (EditText) findViewById(R.id.thingsHeader);

        settings = (FloatingActionButton) findViewById(R.id.post3);
        close = (FloatingActionButton) findViewById(R.id.post);
        share = (FloatingActionButton) findViewById(R.id.post2);
        bckColorPicker = (FloatingActionButton) findViewById(R.id.post4);

        relativeLayout = (RelativeLayout) findViewById(R.id.AddRelative);
        theme = new DatabaseThemeChooser(Add.this);
        Cursor cr = theme.getAllData();
        int counter = cr.getCount();
        if(counter != 0){
            if(cr.moveToFirst()){
                casualTheme = cr.getString(1);
            }
        }
        else{
            casualTheme = "L";
        }
        if(casualTheme.equals("D")){
            mBackgroundDefaultColor = ContextCompat.getColor(Add.this, R.color.defaultBack);
            relativeLayout.setBackgroundColor(mBackgroundDefaultColor);
        }
        else{
            mBackgroundDefaultColor = ContextCompat.getColor(Add.this, R.color.defaultBack);
            relativeLayout.setBackgroundColor(mBackgroundDefaultColor);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Add.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_settings_template, null);

                FloatingActionButton textColor = (FloatingActionButton) mView.findViewById(R.id.colorPicker);
                FloatingActionButton password = (FloatingActionButton) mView.findViewById(R.id.passwordCreator);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                textColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        colorpickText();
                    }
                });

                password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Add.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_template, null);
                        final EditText mPassword = (EditText) mView.findViewById(R.id.passwordAlert);
                        final EditText mPasswordCheck = (EditText) mView.findViewById(R.id.passwordCheckAlert);
                        Button btn = (Button) mView.findViewById(R.id.btn_confirm);

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String passHolder = mPassword.getText().toString();
                                String passCheckHolder = mPasswordCheck.getText().toString();

                                if(!passHolder.equals(passCheckHolder)){
                                    Toast.makeText(Add.this, "Passwords does not match.", Toast.LENGTH_LONG).show();
                                }
                                else if(mPassword.getText().toString().isEmpty() || mPasswordCheck.getText().toString().isEmpty()){
                                    passHolder = "";
                                    passTransfer = passHolder;
                                    Toast.makeText(Add.this, "Empty password is saved for text.", Toast.LENGTH_LONG);
                                }
                                else if(passCheckHolder.equals("") || passCheckHolder.equals("")){
                                    Toast.makeText(Add.this, "You didn't write a password.", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    if(mPassword.getText().toString().isEmpty()){
                                        passTransfer = "";
                                    }
                                    else{
                                        passTransfer = passHolder;
                                    }
                                    dialog.cancel();
                                    Toast.makeText(Add.this, "Password saved for text", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(Add.this);

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String currentTime = simpleDateFormat.format(calendar.getTime());

                if(!content.getText().toString().isEmpty()){
                    cnt = content.getText().toString();
                }
                else{
                    cnt = "Maybe it was a great day. Maybe not. The point is, it was a DAY.";
                }
                if(!header.getText().toString().isEmpty()){
                    hdr = header.getText().toString();
                }
                else{
                    hdr = "Casual day, but different time";
                }
                String typeCastTextColor = String.valueOf(mTextDefaultColor);
                String typeCastBackColor = String.valueOf(mBackgroundDefaultColor);
                db.insertData(currentDate, currentTime, cnt, passTransfer, typeCastTextColor, hdr, typeCastBackColor);

                Intent i = new Intent(Add.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
        bckColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorpickBackground();
            }
        });
    }
    public void colorpickText(){
        AmbilWarnaDialog cp = new AmbilWarnaDialog(this, mTextDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mTextDefaultColor = color;
                content.setTextColor(mTextDefaultColor);
            }
        });
        cp.show();
    }
    public void colorpickBackground(){
        AmbilWarnaDialog cp = new AmbilWarnaDialog(this, mTextDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mBackgroundDefaultColor = color;
                relativeLayout.setBackgroundColor(mBackgroundDefaultColor);
            }
        });
        cp.show();
    }
}