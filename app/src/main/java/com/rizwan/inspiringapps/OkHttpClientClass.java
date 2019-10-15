package com.rizwan.inspiringapps;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.rizwan.inspiringapps.model.ApacheAccessLogModel;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static java.util.stream.Collectors.counting;

public class OkHttpClientClass implements Runnable {
    OkHttpClient okHttpClient = new OkHttpClient();
    private final String baseUrl = "http://dev.inspiringapps.com/Files/IAChallenge/30E02AAA-B947-4D4B-8FB6-9C57C43872A9/Apache.log";
private TableLayout tableLayout;
private Activity activity;
    public OkHttpClientClass(TableLayout tableLayout, Activity activity) {
   this.tableLayout= tableLayout;
        this.activity= activity;
    }

    @Override
    public void run() {
        Request request = new Request.Builder().url(baseUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call call, Response response) throws IOException {


                ResponseBody responseBody = response.body();
                String data = responseBody.string();
                //  Log.d("Testing", "onResponse: " + data);
                findSuccessIpCount(data);


            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void findSuccessIpCount(String record) {
        // Creating a regular expression for the records
        final String APACHe_LOG_PATTERN =
                // 1:IP  2:client 3:user 4:date time                   5:method 6:req 7:proto   8:respcode 9:size
                "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+)";

        final Pattern pattern = Pattern.compile(APACHe_LOG_PATTERN, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(record);

        // Creating a Hashmap containing string as
        // the key and integer as the value.
        //  HashMap<String, Integer> dataModelClassHashMap = new HashMap<>();
        List<ApacheAccessLogModel> apacheAccessLogModelsList = new ArrayList<>();
        ApacheAccessLogModel apacheAccessLogModel = null;
        while (matcher.find()) {

            String ipAddress = matcher.group(1);
            String page = matcher.group(6);

            //   Log.d("Matcher", "group1: "+matcher.group(1)+" group:2 "+matcher.group(2)+" group:3 "+matcher.group(3)+" group4:  "+matcher.group(4)+" group 5 "+matcher.group(5)+ " group 6 "+matcher.group(6)+ " group 7 "+matcher.group(7)+" group 8 "+matcher.group(8)+ " group 9 "+matcher.group(9));
            apacheAccessLogModel = new ApacheAccessLogModel(ipAddress, page);
            apacheAccessLogModelsList.add(apacheAccessLogModel);


        }


        Map<String,Map<String,Long>> frequenctSequenceCount =
                apacheAccessLogModelsList.stream()
                        .collect(Collectors.groupingBy(ApacheAccessLogModel::getIdAddess,
                                Collectors.groupingBy(ApacheAccessLogModel::getPages, Collectors.counting()))
                        );
//
       // frequenctSequenceCount.forEach((k,v)-> v.forEach((a,b)));//Log.d("findSuccessIpCount", ": "+a + " " +  k + " " + b)));

        for (Map.Entry<String, Map<String, Long>> letterEntry : frequenctSequenceCount.entrySet()) {

            String page = letterEntry.getKey();

            for (Map.Entry<String, Long> nameEntry : letterEntry.getValue().entrySet()) {
                String ipAddress = nameEntry.getKey();

                String countSequence =Long.toString( nameEntry.getValue());
                activity.runOnUiThread(() -> {

                    TableRow tableRow = new TableRow(activity.getApplicationContext());

                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tableRow.setLayoutParams(layoutParams);

                    TextView countSequencetv = new TextView(activity.getApplicationContext());
                    countSequencetv.setText(countSequence);
                    tableRow.addView(countSequencetv, 0);
                    TextView pageVisitedTV = new TextView(activity.getApplicationContext());
                    pageVisitedTV.setText(page);
                    tableRow.addView(pageVisitedTV, 0);
                    TextView ipTextView = new TextView(activity.getApplicationContext());
                    ipTextView.setText(ipAddress);
                    tableRow.addView(ipTextView, 0);



                    tableLayout.addView(tableRow);
                });
            }
        }
    }

}
