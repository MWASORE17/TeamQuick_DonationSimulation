package com.simulasi_donasi.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simulasi_donasi.R;
import com.simulasi_donasi.model.entity.User;
import com.simulasi_donasi.model.session.SessionManager;


public class Dompet extends ParentActivity {
    private User user;
    private Button btntambahdompet;
    private EditText et_tambahdompet;
    private TextInputLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dompet);

        user = SessionManager.with(getApplicationContext()).getuserloggedin();

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Harap Tunggu...");
        progress.show();

        Thread _thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    progress.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        _thread.start();

        final TextView userDompet = (TextView) findViewById(R.id.activity_dompet_text);
        userDompet.setText(String.valueOf(user.getDompet()));

        et_tambahdompet = (EditText)findViewById(R.id.activity_dompet_edittext);
        container = (TextInputLayout)findViewById(R.id.activity_dompet_container);

        btntambahdompet = (Button)findViewById(R.id.activity_dompet_button);

        btntambahdompet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setErrorEnabled(false);
                String isi = et_tambahdompet.getText().toString();
                int intisi = Integer.parseInt(isi);
                int temp = user.getDompet();
                int total = temp + intisi;
                if(et_tambahdompet.getText().toString().length()==0){
                    container.setErrorEnabled(true);
                    container.setError("Data tidak boleh kosong");
                }
                else if(intisi == 0){
                    container.setError("Data tidak boleh 0");
                }
                else if(temp == 2000000000){
                    container.setError("Anda telah mencapai batas maksimum pengisian dompet");
                }
                else if(total > 2000000000){
                    container.setError("Jumlah maksimum dompet hanya Rp. 2000000000");
                }
                else if(total<0){
                    container.setError("Jumlah maksimum dompet hanya Rp. 2000000000");
                }
                else{
                    user.setDompet(total);
                    SessionManager sessionManager = SessionManager.with(getApplicationContext());
                    sessionManager.createsessionuser(user);
                    Toast.makeText(Dompet.this,"Dompet Anda telah ditambah Rp." + intisi,Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.logout:
                new AlertDialog.Builder(this).setMessage("Apakah kamu yakin ingin keluar?").setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SessionManager.with(getApplicationContext()).clearsession();
                        ParentActivity.doChangeActivity(getApplicationContext(), AuthActivity.class);
                    }
                }).setNegativeButton("Tidak", null).show();
                break;
            case R.id.quick:
                Intent quick = new Intent(Dompet.this, QuickDonateActivity.class);
                startActivity(quick);
                finish();
                break;
            case R.id.tambah:
                Intent dompet = new Intent(Dompet.this, Dompet.class);
                startActivity(dompet);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}