package adcdev.simrev.simregrevolution;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class SMSActivity extends AppCompatActivity {
    private final static int SEND_SMS_PERMISSION_REQUEST_CODE=111;
    private Spinner spinner;
    String nama[]={"Pilih Registrasi","Registrasi Ulang","Registrasi Baru","Unreg"};
    ArrayAdapter<String> adapter;
    private String txtAwal, txtAkhir, textTengah = "#";;
    CardView cardView;
    EditText nik,nkk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String title = getIntent().getStringExtra("title");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        cardView = findViewById(R.id.cardView);
        nik = findViewById(R.id.editText);
        nkk = findViewById(R.id.editText2);

        this.setTitle(title);

        spinner = findViewById(R.id.spinner) ;

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nama);
        spinner.setAdapter(adapter);


        showStatusDialog();

        proses();

        cardView.setEnabled(false);

        if(checkPermission(Manifest.permission.SEND_SMS)){
            cardView.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
    }


    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case SEND_SMS_PERMISSION_REQUEST_CODE :
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    cardView.setEnabled(true);
                }
                break;
        }
    }

    protected void sendSMS() {

        String Nik = nik.getText().toString();
        String Nkk = nkk.getText().toString();
        String noHp = "4444";

        String Text = txtAwal + Nik + textTengah + Nkk + txtAkhir;

        if (txtAwal!=txtAkhir) {
            if(Nik.length()>0){
                if(checkPermission(Manifest.permission.SEND_SMS)){
                    Log.i("Send SMS", "");
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                    smsIntent.setData(Uri.parse("smsto:"));
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address"  , new String (noHp));
                    smsIntent.putExtra("sms_body"  , Text);

                    try {
                        startActivity(smsIntent);
                        finish();
                        Log.i("SMS berhasil dikirim...", "");
                    }
                    catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(),
                                "Sms gagal dikirim.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(),
                            "Permission Denied.",
                            Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(getBaseContext(),
                        "NIK dan NKK tidak boleh kosong.",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getBaseContext(),
                    "Pilih Registrasi dahulu.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void proses() {

        final String title = getIntent().getStringExtra("title");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        txtAwal="null";
                        txtAkhir="null";
                        Toast.makeText(getApplicationContext(),
                                "Pilih Registrasi terlebih dahulu.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if(title.equals("Telkomsel")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal ="ULANG ";
                            textTengah = "#";
                            txtAkhir ="#";
                        }else if(title.equals("Indosat")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal ="ULANG#";
                            textTengah = "#";
                            txtAkhir ="#";

                        }else if(title.equals("XL/Axis")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal = "ULANG#";
                            textTengah = "#";
                            txtAkhir = "";

                        }else if(title.equals("Tri")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal ="ULANG#";
                            textTengah = "#";
                            txtAkhir ="#";

                        }else if(title.equals("Smartfren")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal ="ULANG#";
                            textTengah = "#";
                            txtAkhir ="#";

                        }else if(title.equals("Bolt")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal ="ULANG#";
                            textTengah = "#";
                            txtAkhir ="#";

                        }
                        break;
                    case 2:
                        if(title.equals("Telkomsel")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal = "REG ";
                            textTengah = "#";
                            txtAkhir = "#";

                        }else if(title.equals("Indosat")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal = "";
                            textTengah = "#";
                            txtAkhir = "#";

                        }else if(title.equals("XL/Axis")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal = "DAFTAR#";
                            textTengah = "#";
                            txtAkhir = "";

                        }else if(title.equals("Tri")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal = "";
                            textTengah = "#";
                            txtAkhir = "#";

                        }else if(title.equals("Smartfren")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal = "";
                            textTengah = "#";
                            txtAkhir = "#";

                        }else if(title.equals("Bolt")){
                            nkk.setVisibility(View.VISIBLE);
                            nik.setHint("Nomor NIK (KTP)");
                            txtAwal = "";
                            textTengah = "#";
                            txtAkhir = "#";

                        }
                        break;
                    case 3:

                        if(title.equals("Telkomsel")){
                            nkk.setVisibility(View.INVISIBLE);
                            nkk.setText("");
                            nik.setText("");
                            txtAwal = "Unreg#";
                            textTengah = "";
                            txtAkhir = "#";
                        }else if(title.equals("XL/Axis")){
                            nkk.setVisibility(View.INVISIBLE);
                            nkk.setText("");
                            nik.setText("");
                            nik.setHint("Nomor HP");
                            txtAwal = "UNREG#";
                            textTengah = "";
                            txtAkhir = "";
                        }else if(title.equals("Indosat")){
                            nkk.setVisibility(View.INVISIBLE);
                            nkk.setText("");
                            nik.setText("");
                            nik.setHint("Nomor HP");
                            txtAwal = "UNPAIR#";
                            textTengah = "";
                            txtAkhir = "#";
                        }else if(title.equals("Smartfren")){
                            nkk.setVisibility(View.INVISIBLE);
                            nkk.setText("");
                            nik.setText("");
                            txtAwal = "UNREG#";
                            textTengah = "";
                            txtAkhir = "#";
                        }else if(title.equals("Tri")){
                            String Alamat = "https://registrasi.tri.co.id/unreg";
                            Intent intent = new Intent(SMSActivity.this,WebViewActivity.class);
                            intent.putExtra("Alamat",Alamat);
                            startActivity(intent);
                        }else if(title.equals("Bolt")){
                            Toast.makeText(getApplicationContext(), "Fitur ini belum tersedia oleh provider Bolt", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showStatusDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Sebelum Registrasi, Pilih Registrasi dahulu\n\n" +
                        "Registrasi Ulang\nRegistrasi untuk pelanggan lama (kartu lama)\n\n" +
                        "Registrasi Baru\nRegistrasi untuk pelanggan baru (Kartu baru beli)")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }
}
