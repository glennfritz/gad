package com.afrovision.gaad.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afrovision.gaad.R;
import com.afrovision.gaad.database.RemoteDB;

public class SubmitActivity extends AppCompatActivity {
    EditText firstname,lastname, email, link;
    Button submit;
    ProgressDialog pd;
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setIndeterminate(true);
        pd.setMessage(getString(R.string.submitting));

        container = findViewById(R.id.container);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        link = findViewById(R.id.link);
        submit = findViewById(R.id.submit);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

    }

    public void submitForm(){
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String em = email.getText().toString();
        String url = link.getText().toString();
        if(fname.isEmpty() || lname.isEmpty() || em.isEmpty() || url.isEmpty()){
            if(fname.isEmpty()){
                Toast.makeText(this, getString(R.string.enter_first_name),Toast.LENGTH_SHORT).show();
                firstname.setError("required");
            }else if(lname.isEmpty()){
                Toast.makeText(this, getString(R.string.enter_last_name),Toast.LENGTH_SHORT).show();
                lastname.setError("required");
            }else if(em.isEmpty()){
                Toast.makeText(this, getString(R.string.enter_email_name),Toast.LENGTH_SHORT).show();
                email.setError("required");
            }else{
                Toast.makeText(this, getString(R.string.enter_github_link),Toast.LENGTH_SHORT).show();
                link.setError("required");
            }
        }else{
            container.setVisibility(View.GONE);
            showConfirmDialog(fname,lname,em,url);

        }

    }

    public void showConfirmDialog(final String fname, final String lname, final String em, final String url){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.confirm_dialog, null);
        builder.setView(customLayout);
        ImageButton close = customLayout.findViewById(R.id.close);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button yes = customLayout.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                new RemoteDB(SubmitActivity.this, new RemoteDB.transactionCompleted() {
                    @Override
                    public void completeTransaction(String status) {
                          if(status.equals("200"))   {
                              showSuccessDialog();
                          }else{
                              showErrorDialog();
                          }
                    }
                }).submitProject(fname,lname,em,url);
            }
        });

    }


    public void showSuccessDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.success, null);
        builder.setView(customLayout);        // add a button
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void showErrorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.error, null);
        builder.setView(customLayout);        // add a button
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
