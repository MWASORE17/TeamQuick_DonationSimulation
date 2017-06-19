package com.simulasi_donasi.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.simulasi_donasi.model.entity.Data;
import com.simulasi_donasi.model.entity.User;

import com.simulasi_donasi.R;
import com.simulasi_donasi.model.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends ParentActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    RecyclerviewAdapter myRecAdapter;
    String searchString="";
    LinearLayout kosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kosong = (LinearLayout)findViewById(R.id.datakosong);

        //Data User
        Data.datas.clear();
        User a = new User("STMIK - Mikroskil Medan", "a@mobile.id", "password", 500000);
        User b = new User("Kampus A", "b@mobile.id", "password", 3500000);
        User c = new User("Kampus B", "c@mobile.id", "password",50000000);
        User d = new User("Kampus C", "d@mobile.id", "password",450000);
        User e = new User("Kampus D - Thamrin Plaza", "e@mobile.id", "password",1000000);
        User f = new User("f", "f@mobile.id", "password",0);
        User g = new User("g", "g@mobile.id", "password",10000000);
        User h = new User("h", "h@mobile.id", "password",230000);
        User i = new User("i", "i@mobile.id", "password",25000000);
        User j = new User("j", "j@mobile.id", "password",23000000);
        User k = new User("k", "k@mobile.id", "password",200000);
        User l = new User("l", "l@mobile.id", "password",100000);
        User m = new User("Macdoze", "macdoze@mobile.id", "macdoze123",100000);
        User.users.add(a);
        User.users.add(b);
        User.users.add(c);
        User.users.add(d);
        User.users.add(e);
        User.users.add(f);
        User.users.add(g);
        User.users.add(h);
        User.users.add(i);
        User.users.add(j);
        User.users.add(k);
        User.users.add(l);
        User.users.add(m);

        //Data Donasi
        Data da = new Data(R.drawable.gempa,"Gempa Bumi","Bencana Alam","Rabu (6/12) pukul 05.03 WIB, Kabupaten Pidie Jaya, Aceh diguncang gempa bumi 6,4 Skala Richter (SR)." +
                "\n\n\"Info sementara 1 orang luka-luka, 10 ruko roboh, beberapa tiang listrik roboh dan 4 rumah di jalan desa rusak berat,\" Kepala Pusat Gempabumi dan Tsunami BMKG, M Riyadi dalam keterangan tertulis yang diterima detikcom, Rabu (7/12/2016)." +
                "\n\nDemikian juga sejumlah fasilitas umum dan sekolah rusak parah. Salah satunya, Sekolah Tinggi Agama Islam Aziziyah, Jamalanga, Kabupaten Bireuen, Nanggroe Aceh Darussalam (NAD). Tembok bercat kuning pada bagunan ini retak-retak." +
                "\n\nTidak hanya itu, masjid-masjid juga mengalami kerusakan. Ada masjid berlantai dua yang mengalami rusak parah. Sebagian bangunannya roboh."
                ,"Kabupaten Pidie Jaya (NAD)");
        Data db = new Data(R.drawable.banjir,"Banjir","Bencana Alam", "REPUBLIKA.CO.ID, MATARAM -- Kepala Penanggulangan Bencana Daerah (BPBD) Provinsi Nusa Tenggara Barat Muhammad Rum mengatakan, bencana banjir melanda Kota Bima. Banjir terjadi sejak pukul 14.00 WITA akibat tingginya curah hujan yang melanda kota di Pulau Sumbawa tersebut. " +
                "\n\n\"Bannjir meliputi Kota Bima. Hampir seluruh kota dikepung banjir,\" katanya di Mataram, Rabu (21/12)." +
                "\n\nIa menyampaikan, banjir membuat ribuan rumah di Kota Bima tenggelam. Rum belum bisa merinci jumlah pastinya. " +
                "\n\n\"Warga terdampak saat ini sedang menyelamatkan diri ke tempat-tempat yang aman,\" ujarnya" +
                "\n\nSelain itu, banjir juga membuat sejumlah tahanan di LP Kota Bima ikut dievakuasi. Ia menyebutkan, sejumlah wilayah di Kota Bima seperti Lewirato, Sadia, Jati Wangi, Melayu, dan Pena Na'e terendam banjir hingga dua meter. " +
                "\n\nSedangkan, laporan sementara untuk Kabupaten Bima terdapat 25 rumah rusak berat di desa Maria Utara, lima rumah hanyut dan tiga rumah rusak sedang di Desa Kambilo. " +
                "\n\n\"Masyarakat dievakuasi sebanyak 50 orang di Paruga To'i dan satu unit jembatan provinsi putus,\" katanya. " +
                "\n\nIa melanjutkan, sejumlah elemen di NTB dari pemerintah, TNI, Polri, MDMC, dan sejumlah relawan lain saat ini masih berupaya melakukan evakuasi dan pengobatan bagi warga yang mengalami luka."
                ,"Kota Bima (NTT)");
        Data dc = new Data(R.drawable.tsunami,"Tsunami","Bencana Alam","test","test");
        Data dd = new Data(R.drawable.masjid,"Pembangunan Masjid","Pembangunan","blablablablablablablablablabla","test");
        Data de = new Data(R.drawable.gereja,"Pembangunan Gereja","Pembangunan","blablablablablablablabla","test");
        Data df = new Data(R.drawable.vihara,"Pembangunan Vihara","Pembangunan","blablablablablablabla","test");
        Data dg = new Data(R.drawable.katarak,"Operasi Katarak","Operasi","blablablablablablablablablabla","test");
        Data dh = new Data(R.drawable.kakipalsu,"Pembuatan Kaki Palsu","Sumbangan","blablablablablablablabla","test");
        Data di = new Data(R.drawable.bibirsumbing,"Operasi Bibir Sumbing","Operasi","blablablablablablabla","test");
        Data.datas.add(da);
        Data.datas.add(db);
        Data.datas.add(dc);
        Data.datas.add(dd);
        Data.datas.add(de);
        Data.datas.add(df);
        Data.datas.add(dg);
        Data.datas.add(dh);
        Data.datas.add(di);

             /* checking the session */
        if (!SessionManager.with(getApplicationContext()).isuserlogin()) {
            this.doChangeActivity(getApplicationContext(), AuthActivity.class);
        }
        initSimpleSpinner();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        myRecAdapter = new RecyclerviewAdapter(Data.datas, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(myRecAdapter);
    }

    //Filter Kategori lewat Spinner
    private void initSimpleSpinner() {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_toolbar);

        List<String> languages = new ArrayList<String>();
        languages.add("Semua Kategori");
        languages.add("Bencana Alam");
        languages.add("Operasi");
        languages.add("Pembangunan");
        languages.add("Sumbangan");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.matches("Semua Kategori")){
                    myRecAdapter = new RecyclerviewAdapter(Data.datas, MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(myRecAdapter);
                }else{
                    final List<Data> categoryModelList = categoryfilter(Data.datas,spinner);
                    myRecAdapter.setFilter(categoryModelList);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Data> filteredModelList = filter(Data.datas, newText);
        if (filteredModelList.size() > 0) {
            myRecAdapter.setFilter(filteredModelList);
            kosong.setVisibility(View.INVISIBLE);
            return true;
        } else {
            kosong.setVisibility(View.VISIBLE);
            return false;
        }
    }

    // Adapter
    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.VH> {

        public List<Data> DataList;
        public Context context;
        ArrayList<Data> mModel;

        public RecyclerviewAdapter(List<Data> parkingList, Context context) {
            this.DataList = parkingList;
            this.context = context;
        }

        @Override
        public RecyclerviewAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerviewAdapter.VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_linear, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerviewAdapter.VH holder, int position) {
            final Data _data = this.DataList.get(position);

            holder.img.setImageResource(_data.getImg());
            holder.title.setText(_data.getJudul());
            holder.location.setText(_data.getLokasi());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent _intent = new Intent(v.getContext(), DonateDetailActivity.class);
                    _intent.putExtra("data", _data);
                    v.getContext().startActivity(_intent);
                }
            });

            Data txt = DataList.get(position);
            String name = txt.getJudul().toLowerCase(Locale.getDefault());
            if (name.contains(searchString)) {

                int startPos = name.indexOf(searchString);
                int endPos = startPos + searchString.length();

                Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.title.getText());
                spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.title.setText(spanString);
            }
        }

        @Override
        public int getItemCount() {
            return DataList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            ImageView img;
            TextView title,location;

            public VH(View itemView) {
                super(itemView);

                img = (ImageView)itemView.findViewById(R.id.item_data_linear_image);
                title = (TextView) itemView.findViewById(R.id.item_data_linear_judul);
                location = (TextView) itemView.findViewById(R.id.item_data_linear_lokasi);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }

        }

        public void setFilter(List<Data> countryModels) {
            mModel = new ArrayList<>();
            mModel.addAll(countryModels);
            notifyDataSetChanged();
        }

    }
    //Filter
    private List<Data> categoryfilter(List<Data>models, Spinner spinner){

        String text = spinner.getSelectedItem().toString().toLowerCase();

        final List<Data>categoryModelList = new ArrayList<>();
        for (Data model : models){
            final String category = model.getKategori().toLowerCase();
            if(category.contains(text)){
                categoryModelList.add(model);
            }
        }
        myRecAdapter = new RecyclerviewAdapter(categoryModelList,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(myRecAdapter);
        myRecAdapter.notifyDataSetChanged();

        return categoryModelList;
    }
    // Search Method
    private List<Data> filter(List<Data> models, String query) {

        query = query.toLowerCase();
        this.searchString=query;

        final List<Data> filteredModelList = new ArrayList<>();
        for (Data model : models) {
            final String text = model.getJudul().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        myRecAdapter = new RecyclerviewAdapter(filteredModelList, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(myRecAdapter);
        myRecAdapter.notifyDataSetChanged();

        return filteredModelList;
    }

    //Spinner Method


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        TextView searchText = (TextView)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchText.setTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHintTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHint("Cari...");
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                new AlertDialog.Builder(this).setMessage("Apakah kamu yakin ingin keluar?").setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SessionManager.with(getApplicationContext()).clearsession();
                        ParentActivity.doChangeActivity(getApplicationContext(), AuthActivity.class);
                    }
                }).setNegativeButton("Tidak", null).show();
                break;
            case R.id.quick:
                Intent quick = new Intent(MainActivity.this, QuickDonateActivity.class);
                startActivity(quick);
                break;
            case R.id.tambah:
                Intent dompet = new Intent(MainActivity.this, Dompet.class);
                startActivity(dompet);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan tombol kembali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}