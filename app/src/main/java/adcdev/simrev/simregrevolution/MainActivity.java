package adcdev.simrev.simregrevolution;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ImageButton ib_tsel, ib_xl, ib_isat, ib_tri, ib_smart, ib_bolt;
    private ImageView on_tsel, on_xl, on_isat, on_tri, on_smart, on_bolt;
    Dialog myDialog;
    private TextView about, desAbout;
    private Button btn_tsel, btn_xl, btn_isat, btn_tri, btn_smart, btn_bolt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        sliderDotspanel = findViewById(R.id.SliderDots);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        MetodeOnline();

        MetodeSMS();

        dotImage();

        SharedPreferences prefs  = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        if (firstStart){
            showStatusDialog();
        }

    }

    private void MetodeSMS() {
        //casting metode sms
        btn_tsel = findViewById(R.id.btn_tsel);
        btn_isat = findViewById(R.id.btn_isat);
        btn_xl = findViewById(R.id.btn_xl);
        btn_tri = findViewById(R.id.btn_tri);
        btn_smart = findViewById(R.id.btn_smart);
        btn_bolt = findViewById(R.id.btn_bolt);

        //proses
        btn_tsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SMSActivity.class);
                intent.putExtra("title","Telkomsel");
                startActivity(intent);
            }
        });
        btn_isat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SMSActivity.class);
                intent.putExtra("title","Indosat");
                startActivity(intent);
            }
        });
        btn_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SMSActivity.class);
                intent.putExtra("title","XL/Axis");
                startActivity(intent);
            }
        });
        btn_tri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SMSActivity.class);
                intent.putExtra("title","Tri");
                startActivity(intent);
            }
        });
        btn_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SMSActivity.class);
                intent.putExtra("title","Smartfren");
                startActivity(intent);
            }
        });
        btn_bolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SMSActivity.class);
                intent.putExtra("title","Bolt");
                startActivity(intent);
            }
        });
    }

    private void showStatusDialog() {
        new AlertDialog.Builder(this)
                .setTitle("SELAMAT DATANG DI SIM REG REVOLUTION")
                .setMessage("Pengguna dapat menggunakan salah satu metode untuk registrasi kartu\n" +
                        "\nMetode SMS\nMetode ini menggunakan fitur sms" +
                        "\n\nMetode Online\nMetode ini memerlukan koneksi INTERNET")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    private void MetodeOnline() {
        //<<-- casting tombol metode online-->>
        on_tsel = findViewById(R.id.on_tsel);
        on_isat = findViewById(R.id.on_isat);
        on_xl = findViewById(R.id.on_xl);
        on_tri = findViewById(R.id.on_tri);
        on_smart = findViewById(R.id.on_smart);
        on_bolt = findViewById(R.id.on_bolt);

        //<<-- logika ketika metode online di klik -->>
        on_tsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Alamat = "https://mobi.telkomsel.com/ulang";
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("Alamat",Alamat);
                startActivity(intent);
            }
        });
        on_isat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Alamat = "https://myim3.indosatooredoo.com/registration";
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("Alamat",Alamat);
                startActivity(intent);
            }
        });
        on_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Alamat = "https://registrasi.xl.co.id/ulang";
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("Alamat",Alamat);
                startActivity(intent);
            }
        });
        on_tri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Alamat = "https://registrasi.tri.co.id/";
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("Alamat",Alamat);
                startActivity(intent);
            }
        });
        on_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Alamat = "https://my.smartfren.com/prepaid_reg.php";
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("Alamat",Alamat);
                startActivity(intent);
            }
        });
        on_bolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Alamat = "http://www.bolt.id/aktivasi.html";
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("Alamat",Alamat);
                startActivity(intent);
            }
        });
    }

    private void dotImage() {
        //<<-- casting tombol cek nomor -->>
        ib_tsel = findViewById(R.id.op_tsel);
        ib_isat = findViewById(R.id.op_isat);
        ib_xl = findViewById(R.id.op_xl);
        ib_tri = findViewById(R.id.op_tri);
        ib_smart = findViewById(R.id.op_smart);
        ib_bolt = findViewById(R.id.op_bolt);

        //<<-- logika ketika cek nomor di klik -->>
        ib_tsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), ib_tsel);
                popup.inflate(R.menu.popupmenu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.cekNomor){
                            String Alamat = "https://www.telkomsel.com/cek-prepaid";
                            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                            intent.putExtra("Alamat",Alamat);
                            startActivity(intent);
                        }else if(item.getItemId() == R.id.unregNomor){
                            Toast.makeText(getApplicationContext(), "Fitur ini belum tersedia oleh provider Telkomsel", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        ib_isat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), ib_isat);
                popup.inflate(R.menu.popupmenu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.cekNomor){
                            String Alamat = "https://myim3.indosatooredoo.com/ceknomor/index";
                            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                            intent.putExtra("Alamat",Alamat);
                            startActivity(intent);
                        }else if(item.getItemId() == R.id.unregNomor){
                            Toast.makeText(getApplicationContext(), "Fitur ini belum tersedia oleh provider Indosat", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        ib_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), ib_xl);
                popup.inflate(R.menu.popupmenu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.cekNomor){
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+Uri.encode("*123*444#")));
                            startActivity(intent);
                        }else if(item.getItemId() == R.id.unregNomor){
                            Toast.makeText(getApplicationContext(), "Fitur ini belum tersedia oleh provider XL/Axis", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        ib_tri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), ib_tri);
                popup.inflate(R.menu.popupmenu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.cekNomor){
                            String Alamat = "https://registrasi.tri.co.id/ceknomor";
                            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                            intent.putExtra("Alamat",Alamat);
                            startActivity(intent);
                        }else if(item.getItemId() == R.id.unregNomor){
                            String Alamat = "https://registrasi.tri.co.id/unreg";
                            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                            intent.putExtra("Alamat",Alamat);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        ib_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), ib_smart);
                popup.inflate(R.menu.popupmenu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.cekNomor){
                            String Alamat = "https://my.smartfren.com/check_nik.php";
                            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                            intent.putExtra("Alamat",Alamat);
                            startActivity(intent);
                        }else if(item.getItemId() == R.id.unregNomor){
                            String Alamat = "http://my.smartfren.com/mysmartfren/prepaid_unreg.php";
                            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                            intent.putExtra("Alamat",Alamat);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        ib_bolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), ib_bolt);
                popup.inflate(R.menu.popupmenu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.cekNomor){
                            Toast.makeText(getApplicationContext(), "Fitur ini belum tersedia oleh provider Bolt", Toast.LENGTH_LONG).show();
                        }else if(item.getItemId() == R.id.unregNomor){
                            Toast.makeText(getApplicationContext(), "Fitur ini belum tersedia oleh provider Bolt", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saveData){
            Toast.makeText(getApplicationContext(), "Fitur ini masih Coming Soon", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_tsel) {
            String Alamat = "https://www.telkomsel.com/";
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            intent.putExtra("Alamat",Alamat);
            startActivity(intent);
        } else if (id == R.id.item_isat) {
            String Alamat = "https://indosatooredoo.com/id";
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            intent.putExtra("Alamat",Alamat);
            startActivity(intent);
        } else if (id == R.id.item_xl) {
            String Alamat = "https://www.xl.co.id/";
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            intent.putExtra("Alamat",Alamat);
            startActivity(intent);
        } else if (id == R.id.item_tri) {
            String Alamat = "https://tri.co.id";
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            intent.putExtra("Alamat",Alamat);
            startActivity(intent);
        } else if (id == R.id.item_smart) {
            String Alamat = "http://www.smartfren.com/id/";
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            intent.putExtra("Alamat",Alamat);
            startActivity(intent);
        } else if (id == R.id.item_bolt) {
            String Alamat = "https://www.bolt.id/";
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            intent.putExtra("Alamat",Alamat);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            String url = "https://1drv.ms/u/s!Aumkfw08pkFCpy9yHUsxa5Ea-7NH";
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT,".:: SIM Reg Revolution ::.\n\nAplikasi untuk mempermudah registrasi kartu prabayar.\nCoba aplikasinya di "+url);
            share.setType("text/plain");
            startActivity(share);

        } else if (id == R.id.nav_feedback) {
            String url = "https://www.facebook.com/aditshinoda";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.nav_about) {
            myAboutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void myAboutDialog() {
        myDialog = new Dialog(MainActivity.this);
        myDialog.setContentView(R.layout.custom_about);
        myDialog.setTitle("TENTANG");

        String versionName = BuildConfig.VERSION_NAME;

        desAbout = myDialog.findViewById(R.id.des_about);

        desAbout.append("Dibuat oleh : \n" +
                "Aris Sudaryanto\n" +
                "Aditya Nanda\n\n" +
                "Instagram : adit.nanda\n" +
                "Telegram : @aditnanda\n" +
                "\n" +
                "Versi : "+versionName);
        myDialog.setCancelable(true);
        myDialog.show();
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if(viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }

}
