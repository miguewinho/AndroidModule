package com.example.myhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bstar,bhash,bcall,short1,short2,short3;
    ImageButton back;
    TextView tv;

    String num1 ="", num2 ="", num3 ="";

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    Button newcontactpopup_cancel, newcontactpopup_save;
    EditText newcontactpopup_name, newcontactpopup_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b0 = findViewById(R.id.b0);
        bstar = findViewById(R.id.bstar);
        bhash = findViewById(R.id.bhash);
        bcall = findViewById(R.id.bcall);
        back = findViewById(R.id.ibtn);
        tv = findViewById(R.id.txtphone);
        short1 = findViewById(R.id.short1);
        short2 = findViewById(R.id.short2);
        short3 = findViewById(R.id.short3);

        Dexter.withContext(this).withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"1");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"2");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"3");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"4");
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"5");
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"6");
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"7");
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"8");
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"9");
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"0");
            }
        });
        bstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"*");
            }
        });
        bhash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText().toString()+"#");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder(tv.getText());
                stringBuilder.deleteCharAt(tv.getText().length()-1);
                String newString = stringBuilder.toString();
                tv.setText(newString);
            }
        });
        back.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv.setText("");
                return true;
            }
        });
        bcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence dialNumber = tv.getText();
                if(dialNumber.length()>0) makephonecall(dialNumber.toString());
            }
        });

        short1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence dialNumber = num1;
                if(dialNumber.length()>0) makephonecall(dialNumber.toString());
            }
        });

        short2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence dialNumber = num2;
                if(dialNumber.length()>0) makephonecall(dialNumber.toString());
            }
        });

        short3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence dialNumber = num3;
                if(dialNumber.length()>0) makephonecall(dialNumber.toString());
            }
        });

        short1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createNewDial("1");
                return true;
            }
        });
        short2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createNewDial("2");
                return true;
            }
        });
        short3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createNewDial("3");
                return true;
            }
        });


    }

    private void makephonecall(String number) {
        String dial = "tel:"+number;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
    }

    public void createNewDial(String id){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup,null);
        newcontactpopup_name = (EditText) contactPopupView.findViewById(R.id.newcontactpopup_name);
        newcontactpopup_number = (EditText) contactPopupView.findViewById(R.id.newcontactpopup_number);

        newcontactpopup_save = (Button) contactPopupView.findViewById(R.id.saveButton);
        newcontactpopup_cancel = (Button) contactPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newcontactpopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence tmp1 = newcontactpopup_name.getText();
                CharSequence tmp2 = newcontactpopup_number.getText();
                if(tmp1.length()>0 && tmp2.length()>0){
                    switch(id){
                        case "1":
                            num1=tmp2.toString();
                            short1.setText(tmp1.toString());
                            break;
                        case "2":
                            num2=tmp2.toString();
                            short2.setText(tmp1.toString());
                            break;
                        case "3":
                            num3=tmp2.toString();
                            short3.setText(tmp1.toString());
                            break;
                    }
                    dialog.dismiss();
                }
            }
        });

        newcontactpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}