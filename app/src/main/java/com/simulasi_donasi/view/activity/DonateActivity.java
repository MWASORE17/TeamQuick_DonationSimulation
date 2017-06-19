package com.simulasi_donasi.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simulasi_donasi.R;
import com.simulasi_donasi.model.entity.Data;
import com.simulasi_donasi.model.entity.User;
import com.simulasi_donasi.model.session.SessionManager;

public class DonateActivity extends ParentActivity{
    private Data data;
    private User user;
    private EditText et_jlhdonasi,et_password;
    private Button btndonasi;
    private TextInputLayout containerjumlah,containerpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        data = (Data) getIntent().getExtras().get("data");
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

        final TextView donateDompet = (TextView) findViewById(R.id.activity_donate_dompet);
        donateDompet.setText(String.valueOf(user.getDompet()));
        TextView title = (TextView) findViewById(R.id.activity_donate_judul);
        title.setText(data.getJudul());


        et_jlhdonasi= (EditText)findViewById(R.id.activity_donate_jumlah);
        et_password = (EditText)findViewById(R.id.activity_donate_password);

        containerjumlah = (TextInputLayout)findViewById(R.id.activity_donate_jumlah_container);
        containerpassword = (TextInputLayout)findViewById(R.id.activity_donate_password_container);

        btndonasi = (Button)findViewById(R.id.activity_donate_button);

        btndonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerjumlah.setErrorEnabled(false);
                containerpassword.setErrorEnabled(false);
                if(et_jlhdonasi.getText().toString().length()==0){
                    containerjumlah.setErrorEnabled(true);
                    containerjumlah.setError("Data tidak boleh kosong");
                }
                else if(et_password.getText().toString().length()==0){
                    containerpassword.setErrorEnabled(true);
                    containerpassword.setError("Password kosong");
                }
                else if (!user.getPassword().equals(et_password.getText().toString())){
                    containerpassword.setErrorEnabled(true);
                    containerpassword.setError("Password salah");
                }
                else if(Integer.parseInt(et_jlhdonasi.getText().toString())<1){
                    containerjumlah.setErrorEnabled(true);
                    containerjumlah.setError("Data tidak boleh kurang dari 1");
                }
                else if (Integer.parseInt(et_jlhdonasi.getText().toString())>user.getDompet()){
                    containerjumlah.setErrorEnabled(true);
                    containerjumlah.setError("Saldo dompet anda tidak mencukupi");
                }
                else if(et_jlhdonasi.getText().toString().contains(".")){
                    containerjumlah.setErrorEnabled(true);
                    containerjumlah.setError("Tidak boleh menggunakan \".\"");
                }
                else if(et_jlhdonasi.getText().toString().length()==1 && et_jlhdonasi.getText().toString().contains("0")){
                    containerjumlah.setErrorEnabled(true);
                    containerjumlah.setError("Angka pertama tidak boleh 0");
                }
                else {
                    int temp = user.getDompet();
                    int jumlahint = Integer.parseInt(et_jlhdonasi.getText().toString())-0;
                    String jumlah = Integer.toString(jumlahint);
                    int total = temp - Integer.parseInt(et_jlhdonasi.getText().toString());
                    user.setDompet(total);
                    SessionManager sessionManager = SessionManager.with(getApplicationContext());
                    sessionManager.createsessionuser(user);
                    Intent _intent = new Intent(DonateActivity.this,PrintActivity.class);
                    _intent.putExtra("data",data);
                    _intent.putExtra("jumlah",jumlah);
                    Toast.makeText(DonateActivity.this,"Berhasil",Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(_intent);
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
                Intent quick = new Intent(DonateActivity.this, QuickDonateActivity.class);
                startActivity(quick);
                finish();
                break;
            case R.id.tambah:
                Intent dompet = new Intent(DonateActivity.this, Dompet.class);
                startActivity(dompet);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}