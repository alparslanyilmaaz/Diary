package com.example.diary;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends AppCompatActivity {

    LinearLayout pageLayout;
    int colorBack, colorText;
    DatabaseThemeChooser chooser;
    DatabaseAdminHelper helper;
    TextView create, delete, textAspect, textChooseTheme, textSettings, textAccount;
    Switch adminOpener;
    String themeHolder = "";
    RadioGroup rg;
    RadioButton rb1, rb2;
    FloatingActionButton themeCng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        pageLayout = (LinearLayout) findViewById(R.id.settingsLayout);

        create = (TextView) findViewById(R.id.crtAdmin);
        delete = (TextView) findViewById(R.id.dltAdmin);
        textAspect = (TextView) findViewById(R.id.textAspect);
        textChooseTheme = (TextView) findViewById(R.id.textChooseTheme);
        textSettings = (TextView) findViewById(R.id.textSettings);
        textAccount = (TextView) findViewById(R.id.textAccount);

        rg = (RadioGroup) findViewById(R.id.radioGroup1);
        rb1 = (RadioButton) findViewById(R.id.radioButton1);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);

        themeCng = (FloatingActionButton) findViewById(R.id.changeTheme);

        adminOpener = (Switch) findViewById(R.id.adminController);

        helper = new DatabaseAdminHelper(this);
        Cursor cr = helper.getAllData();
        if(cr != null){
            if(cr.moveToFirst()){
                String checkBox = cr.getString(3);
                if(checkBox.equals("1")){
                    adminOpener.setChecked(false);
                }
                else{
                    adminOpener.setChecked(true);
                }
            }
        }
        else{
            adminOpener.setChecked(false);
        }

        chooser = new DatabaseThemeChooser(this);
        Cursor theme = chooser.getAllData();
        int count = theme.getCount();
        if(count !=0 ){
            if(theme.moveToFirst()){
                themeHolder = theme.getString(1);
                if(themeHolder.equals("L")){
                    rg.check(R.id.radioButton2);
                }
                else{
                    colorBack = ContextCompat.getColor(Settings.this, R.color.darkMode);
                    colorText = ContextCompat.getColor(Settings.this, R.color.wheat);
                    pageLayout.setBackgroundColor(colorBack);
                    create.setTextColor(colorText);
                    adminOpener.setTextColor(colorText);
                    delete.setTextColor(colorText);
                    rb1.setTextColor(colorText);
                    rb2.setTextColor(colorText);
                    textAccount.setTextColor(colorText);
                    textSettings.setTextColor(colorText);
                    textAspect.setTextColor(colorText);
                    textChooseTheme.setTextColor(colorText);
                    rg.check(R.id.radioButton1);
                }
            }
        }
        else{
            chooser.insertData("L");
            rg.check(R.id.radioButton2);
        }

        themeCng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int choosen = rg.getCheckedRadioButtonId();
                String choosenTxt;

                switch (choosen){
                    case R.id.radioButton1:{
                        choosenTxt = "D";
                        Cursor generalize = chooser.getAllData();
                        int holderCount = generalize.getCount();
                        if(holderCount != 0){
                            if(generalize.moveToFirst()){
                                String id = generalize.getString(0);
                                chooser.updateData(id, choosenTxt);
                                Toast.makeText(Settings.this, "Theme have been changed.", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Settings.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                        else {
                            chooser.insertData(choosenTxt);
                        }
                        break;
                    }
                    case R.id.radioButton2:{
                        choosenTxt = "L";
                        Cursor generalize = chooser.getAllData();
                        int holderCount = generalize.getCount();
                        if(holderCount != 0){
                            if(generalize.moveToFirst()){
                                String id = generalize.getString(0);
                                chooser.updateData(id, choosenTxt);
                                Toast.makeText(Settings.this, "Theme have been changed.", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Settings.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                        else {
                            chooser.insertData(choosenTxt);
                        }
                        break;
                    }
                    default:{
                        choosenTxt = "L";
                        Cursor generalize = chooser.getAllData();
                        int holderCount = generalize.getCount();
                        if(holderCount != 0){
                            if(generalize.moveToFirst()){
                                String id = generalize.getString(0);
                                chooser.updateData(id, choosenTxt);
                            }
                        }
                        else {
                            chooser.insertData(choosenTxt);
                        }
                        break;
                    }
                }
            }
        });

        adminOpener.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Cursor cr = helper.getAllData();
                    int count = cr.getCount();
                    if(count == 0){
                        Toast.makeText(Settings.this, "There is no admin account.", Toast.LENGTH_LONG).show();
                        adminOpener.setChecked(false);
                    }
                    else{
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_confirm_template, null);

                        final EditText password = (EditText) mView.findViewById(R.id.passwordConfirmAlert);
                        Button login = (Button) mView.findViewById(R.id.btn_pass_enter);

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();

                        login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Cursor cr = helper.getAllData();
                                if(cr != null){
                                    if(cr.moveToFirst()){
                                        String passholder = cr.getString(2);
                                        if(password.getText().toString().equals(passholder)){
                                            Toast.makeText(Settings.this, "Admin account activated.", Toast.LENGTH_LONG).show();
                                            helper.updateData(cr.getString(0), cr.getString(1), cr.getString(2), "0");
                                            dialog.cancel();
                                        }
                                        else{
                                            Toast.makeText(Settings.this, "Password is incorrect.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(Settings.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(Settings.this, "Please create admin account.", Toast.LENGTH_LONG).show();
                                    adminOpener.setChecked(false);
                                }
                            }
                        });
                    }
                }
                if(!isChecked){
                    Cursor cr = helper.getAllData();
                    if(cr != null){
                        if(cr.moveToFirst()){
                            helper.updateData(cr.getString(0), cr.getString(1), cr.getString(2), "1");
                            Toast.makeText(Settings.this, "Admin accout deactivated.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor counter = helper.getAllData();
                int a = counter.getCount();
                if(a == 0){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings.this);
                    View mView = getLayoutInflater().inflate(R.layout.admin_create, null);
                    final EditText username = (EditText) mView.findViewById(R.id.adminUsername);
                    final EditText password = (EditText) mView.findViewById(R.id.adminPassword);
                    final EditText passwordCheck = (EditText) mView.findViewById(R.id.adminPasswordCheck);

                    Button nCreate = (Button) mView.findViewById(R.id.btn_createAdmin);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    final String status = "1";

                    nCreate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(password.getText().toString().isEmpty() || passwordCheck.getText().toString().isEmpty()){
                                Toast.makeText(Settings.this, "Please write a password.", Toast.LENGTH_LONG).show();
                            }
                            else if(!password.getText().toString().equals(passwordCheck.getText().toString())){
                                Toast.makeText(Settings.this, "Please Check your passwords.", Toast.LENGTH_LONG).show();
                            }
                            else if(username.getText().toString().isEmpty()){
                                Toast.makeText(Settings.this, "Please write a username.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                helper.insertData(username.getText().toString(), password.getText().toString(), status);
                                dialog.cancel();
                                Toast.makeText(Settings.this, "Admin account created.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Settings.this, "You have already admin account. You can not create one more.", Toast.LENGTH_LONG).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cr = helper.getAllData();
                int count = cr.getCount();
                if(count == 0){
                    Toast.makeText(Settings.this,"You don't have admin account please create one.", Toast.LENGTH_LONG).show();
                }
                else{
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings.this);
                    View mView = getLayoutInflater().inflate(R.layout.admin_delete, null);

                    final EditText password = (EditText) mView.findViewById(R.id.adminDeletePass);

                    Button nDelete = (Button) mView.findViewById(R.id.btn_deleteAdmin);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    nDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cursor cr = helper.getAllData();

                            if(cr != null){
                                if(cr.moveToFirst()){
                                    String passHolder = cr.getString(2);
                                    String idHolder = cr.getString(0);

                                    if(password.getText().toString().equals(passHolder)){
                                        helper.deleteData(idHolder);
                                        Toast.makeText(Settings.this, "Your account succesfully deleted.", Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    }
                                    else{
                                        Toast.makeText(Settings.this, "Password is incorrect. Retype it please.", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(Settings.this,"Something went wrong.", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(Settings.this, "You have to create new Admin user.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
