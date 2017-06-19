package com.simulasi_donasi.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simulasi_donasi.R;
import com.simulasi_donasi.model.entity.Data;
import com.simulasi_donasi.model.session.SessionManager;

public class DonateDetailActivity extends ParentActivity implements View.OnClickListener{
    private Data data;
    private Button btndonatedetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (Data) getIntent().getExtras().get("data");
        setContentView(R.layout.activity_donate_detail);

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

        ImageView img = (ImageView) findViewById(R.id.activity_donate_detail_img);
        img.setImageResource(data.getImg());
        TextView title = (TextView) findViewById(R.id.activity_donate_detail_title);
        title.setText(data.getJudul());
        TextView info = (TextView) findViewById(R.id.activity_donate_detail_info);
        info.setText(data.getInformasi());
        TextView lokasi = (TextView) findViewById(R.id.activity_donate_detail_lokasi);
        lokasi.setText(data.getLokasi());


        btndonatedetail = (Button)findViewById(R.id.activity_donate_detail_button);
        btndonatedetail.setOnClickListener(this);

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
    public void onClick(View v){
        Intent _intent = new Intent(getApplicationContext(),DonateActivity.class);
        _intent.putExtra("data",data);
        Toast.makeText(DonateDetailActivity.this,"Silahkan masukkan jumlah donasi",Toast.LENGTH_SHORT).show();
        startActivity(_intent);
        finish();
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
                Intent quick = new Intent(DonateDetailActivity.this, QuickDonateActivity.class);
                startActivity(quick);
                finish();
                break;
            case R.id.tambah:
                Intent dompet = new Intent(DonateDetailActivity.this, Dompet.class);
                startActivity(dompet);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}