package adcdev.simrev.simregrevolution;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        webView =  findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        frameLayout =findViewById(R.id.frameProgress);

        LoadWeb();
    }

    private void LoadWeb() {
        String link = getIntent().getStringExtra("Alamat");

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        progressBar.setMax(100);
        webView.setWebViewClient(new HelpClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            public  void  onPageFinished(WebView view, String url){

                //Hide the SwipeReefreshLayout
                super.onPageFinished(view, url);
                progressBar.setVisibility(view.GONE);

                swipe.setRefreshing(false);
            }

        });
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view,int progress){
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                setTitle("Memuat..");

                if(progress == 100){
                    frameLayout.setVisibility(View.GONE );
                    setTitle(view.getTitle());
                }
                super.onProgressChanged(view,progress);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(link);

        progressBar.setProgress(0);
    }

    private class HelpClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            frameLayout.setVisibility(View.VISIBLE);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh){
            webView.reload();
        }
        return super.onOptionsItemSelected(item);
    }
}
