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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Update extends AppCompatActivity {

    RelativeLayout relativeLayout;
    DatabaseHelper db;
    DatabaseAdminHelper helper;
    FloatingActionButton back, settings, updateContent, backGroundColorPicker;
    EditText content, header;
    String adminEntry = "";
    int flag=0;
    String passTransfer = "";
    int mTextDefaultColor, mBackDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = new DatabaseHelper(this);
        final String gettingId = getIntent().getStringExtra("id");
        Cursor cr = db.getSelectedData(gettingId);
        String colorGetter = cr.getString(5);
        String backGroundColorGetter = cr.getString(7);

        helper = new DatabaseAdminHelper(this);
        Cursor crAdmin = helper.getAllData();
        if(crAdmin != null){
            if(crAdmin.moveToFirst()){
                String checkBox = crAdmin.getString(3);
                if(checkBox.equals("1")){
                    adminEntry = "1";
                }
                else{
                    adminEntry = "0";
                }
            }
        }

        mBackDefaultColor = Integer.parseInt(backGroundColorGetter);
        mTextDefaultColor = Integer.parseInt(colorGetter);

        back = (FloatingActionButton) findViewById(R.id.post);
        updateContent = (FloatingActionButton) findViewById(R.id.post2);
        settings = (FloatingActionButton) findViewById(R.id.post3);
        backGroundColorPicker = (FloatingActionButton) findViewById(R.id.post4);

        content = (EditText) findViewById(R.id.thingsUpdate);
        header = (EditText) findViewById(R.id.thingsUpdateHeader);
        relativeLayout = (RelativeLayout) findViewById(R.id.UpdateRelav);

        if(adminEntry.equals("0")){
            content.setText(cr.getString(3));
            header.setText(cr.getString(6));

            String color = cr.getString(5);
            final int textColor = Integer.parseInt(color);
            String backColor = cr.getString(7);
            final int backgroundColor = Integer.parseInt(backColor);
            relativeLayout.setBackgroundColor(backgroundColor);

            content.setTextColor(textColor);
            flag = 1;
            Toast.makeText(Update.this, "Admin entry mode.", Toast.LENGTH_LONG).show();
        }
        else{
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Update.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_confirm_template, null);

            final EditText mPassword = (EditText) mView.findViewById(R.id.passwordConfirmAlert);
            Button checkButton = (Button) mView.findViewById(R.id.btn_pass_enter);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cr = db.getSelectedData(gettingId);
                    final String passHolder = cr.getString(4);
                    passTransfer = passHolder;
                    if(passHolder.equals(mPassword.getText().toString())){
                        content.setText(cr.getString(3));
                        header.setText(cr.getString(6));

                        String color = cr.getString(5);
                        final int textColor = Integer.parseInt(color);
                        String backColor = cr.getString(7);
                        final int backgroundColor = Integer.parseInt(backColor);
                        relativeLayout.setBackgroundColor(backgroundColor);

                        content.setTextColor(textColor);
                        Toast.makeText(Update.this,"Password is correct.", Toast.LENGTH_LONG).show();
                        flag = 1;
                        dialog.cancel();
                    }
                    else{
                        Toast.makeText(Update.this, "Password is incorrect.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(Update.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_update_settings, null);
                    FloatingActionButton textColor = (FloatingActionButton) mView.findViewById(R.id.colorPicker);
                    FloatingActionButton password = (FloatingActionButton) mView.findViewById(R.id.passwordCreator);
                    FloatingActionButton remove = (FloatingActionButton) mView.findViewById(R.id.remover);

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
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Update.this);
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
                                        Toast.makeText(Update.this, "Passwords does not match.", Toast.LENGTH_LONG).show();
                                    }
                                    else if(mPassword.getText().toString().isEmpty() || mPasswordCheck.getText().toString().isEmpty()){
                                        passHolder = "";
                                        passTransfer = passHolder;
                                        Toast.makeText(Update.this, "Empty password is saved for text.", Toast.LENGTH_LONG);
                                    }
                                    else if(passCheckHolder.equals("") || passCheckHolder.equals("")){
                                        Toast.makeText(Update.this, "You didn't write a password.", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        if(mPassword.getText().toString().isEmpty()){
                                            passTransfer = "";
                                        }
                                        else{
                                            passTransfer = passHolder;
                                        }
                                        dialog.cancel();
                                        Toast.makeText(Update.this, "Password saved for text", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Update.this);
                            View mView = getLayoutInflater().inflate(R.layout.question_template, null);
                            Button delete = (Button) mView.findViewById(R.id.delete);
                            Button ndelete = (Button) mView.findViewById(R.id.nDelete);

                            mBuilder.setView(mView);
                            final AlertDialog dialog = mBuilder.create();
                            dialog.show();

                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db.deleteData(gettingId);
                                    Intent i = new Intent(Update.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                            ndelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                        }
                    });
                }
                else{
                    Toast.makeText(Update.this, "You are not allowed to do that.", Toast.LENGTH_LONG).show();
                }
            }
        });

        updateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1) {
                    Calendar calendar = Calendar.getInstance();
                    final String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    final String currentTime = simpleDateFormat.format(calendar.getTime());
                    String lastVersionOfContent = content.getText().toString();
                    String lastVersionOfHeader = header.getText().toString();

                    final int textColor = mTextDefaultColor;
                    content.setTextColor(textColor);
                    String typeCastTextColor = String.valueOf(textColor);

                    final int bckColorLast = mBackDefaultColor;
                    String typeCastBackColor = String.valueOf(bckColorLast);

                    db.updateData(gettingId, currentDate, currentTime, lastVersionOfContent, passTransfer, typeCastTextColor, lastVersionOfHeader, typeCastBackColor);
                    Intent i = new Intent(Update.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Update.this, "You are not allowed to do that.", Toast.LENGTH_LONG).show();
                }
            }
        });
        backGroundColorPicker.setOnClickListener(new View.OnClickListener() {
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
                mBackDefaultColor = color;
                relativeLayout.setBackgroundColor(mBackDefaultColor);
            }
        });
        cp.show();
    }
}
