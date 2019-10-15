package com.rizwan.inspiringapps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.rizwan.inspiringapps.model.ApacheAccessLogModel;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity  {
    private ProgressBar progressBar;

    List<ApacheAccessLogModel> apacheAccessLogModelList = null;
    private Map<String, Map<String, Long>> groupedAceesLog;
    private TextView ipAddressTV;
    private TextView pagesVisited;
    private TextView countSequence;
    OkHttpClient okHttpClient = new OkHttpClient();
    private List<ApacheAccessLogModel> apacheAccessLogModelsList;
    private final String baseUrl = "http://dev.inspiringapps.com/Files/IAChallenge/30E02AAA-B947-4D4B-8FB6-9C57C43872A9/Apache.log";
    private TableLayout tableLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.mainTable);
        ipAddressTV = findViewById(R.id.ipAddress);
        pagesVisited = findViewById(R.id.pagesVisited);
        countSequence = findViewById(R.id.countSequence);
      //  initProgressBar();


    }




    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
        progressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels,
                250);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addContentView(progressBar, params);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void GetData(View view) {
        OkHttpClientClass okHttpClientClass= new OkHttpClientClass(tableLayout, this);
        Thread getAccessCode= new Thread(okHttpClientClass);
        getAccessCode.start();

    }
}
