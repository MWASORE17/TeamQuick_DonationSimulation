package com.simulasi_donasi.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.simulasi_donasi.R;
import com.simulasi_donasi.model.entity.Data;
import com.simulasi_donasi.model.entity.User;
import com.simulasi_donasi.model.session.SessionManager;
import com.simulasi_donasi.view.utils.PermissionTaker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Macdoze on 10-Jun-17.
 */

public class PrintActivity extends ParentActivity implements View.OnClickListener{
    private User user;
    private Data data;
    private Button btnprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        PermissionTaker.PermissionTaker(this);
        data = (Data) getIntent().getExtras().get("data");

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

        user = SessionManager.with(getApplicationContext()).getuserloggedin();

        final TextView dompet = (TextView) findViewById(R.id.activity_print_user);
        dompet.setText(String.valueOf(user.getName()));
        TextView title = (TextView) findViewById(R.id.activity_print_judul);
        title.setText(data.getJudul());

        TextView jumlah = (TextView)findViewById(R.id.activity_print_jumlah);
        jumlah.setText(String.valueOf(getIntent().getStringExtra("jumlah")));

        btnprint = (Button)findViewById(R.id.activity_print_pdf);
        btnprint.setOnClickListener(this);


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

    public void onClick(View v){
        int viewId = v.getId();
        switch (viewId){
            case R.id.activity_print_pdf:
                btnprint.setVisibility(View.INVISIBLE);
                new PdfGenerationTask().execute();
                super.finish();
        }
    }

    // Background task to generate pdf from users content
    private class PdfGenerationTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            PdfDocument document = new PdfDocument();

            View content = findViewById(R.id.activity_print);

            int pageNumber = 1;
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),
                    content.getHeight() - 20, pageNumber).create();

            PdfDocument.Page page = document.startPage(pageInfo);
            content.draw(page.getCanvas());

            document.finishPage(page);

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
            String pdfName = "PdfTransaksiSimulasiDOnasi"
                    + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

            File outputFile = new File(Environment.getExternalStorageDirectory(), pdfName);

            try {
                outputFile.createNewFile();
                OutputStream out = new FileOutputStream(outputFile);
                document.writeTo(out);
                document.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return outputFile.getPath();
        }

        @Override
        protected void onPostExecute(String filePath) {
            if (filePath != null) {
                btnprint.setEnabled(true);
                Toast.makeText(getApplicationContext(),
                        "Pdf saved at " + filePath, Toast.LENGTH_SHORT).show();
                Log.d("pdf saved in"," on post execute ......................... "+filePath);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error in Pdf creation" + filePath, Toast.LENGTH_SHORT)
                        .show();
            }

        }
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
                Intent quick = new Intent(PrintActivity.this, QuickDonateActivity.class);
                startActivity(quick);
                break;
            case R.id.tambah:
                Intent dompet = new Intent(PrintActivity.this, Dompet.class);
                startActivity(dompet);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}