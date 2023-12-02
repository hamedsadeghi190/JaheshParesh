package ir.yeksaco.jahesh.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.models.app.VersionHistoryResponse;
import ir.yeksaco.jahesh.utility.DownloadFileAsync;
import ir.yeksaco.jahesh.webService.services.ContentService;

public class UpdateActivity extends AppCompatActivity {
    private ContentService contentService;
    private TextView tv_content;
    private String updateUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        RemoveStausBar();
        tv_content = findViewById(R.id.tv_content);
        Bundle bundle = getIntent().getExtras();
        String history = bundle.getString("history");
        checkPermissions();
        Gson gson = new Gson();
        TypeToken<List<VersionHistoryResponse>> historyListType = new TypeToken<List<VersionHistoryResponse>>() {
        };
        List<VersionHistoryResponse> HistoryData = gson.fromJson(history, historyListType.getType());

        String content = "";
        for (VersionHistoryResponse version : HistoryData) {
            content += "● " + "نگارش " + version.getVersion() + "\n";
            for (String change : version.getChanges()) {
                content += "  ◄ " + change + "\n";
            }
        }
        VersionHistoryResponse lastVer = HistoryData.get(HistoryData.size() - 1);
        updateUrl = lastVer.DirectUrl;
        Log.i("jaheshTag", updateUrl);

        tv_content.setText(content);

        contentService = new ContentService();
        Button btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
                startActivity(intent);

//                String fileUrl = updateUrl;
//                String localFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/update.apk";
//                ProgressBar prg_download = findViewById(R.id.prg_download);
//
//
//                prg_download.setVisibility(View.VISIBLE);
//                Log.i("jaheshTag", localFilePath);
//                DownloadFileAsync downloadFileAsync = new DownloadFileAsync(fileUrl, localFilePath, prg_download);
//                downloadFileAsync.execute();
//                String path = Environment.getExternalStorageDirectory().getAbsolutePath() ;
////                File file = new File(path);
////
////                if (!file.exists()) {
////                    if (!file.mkdirs()) {
////                        Log.e("TAG", "Failed to create directory");
////                    }
////                }
////                Log.i("jaheshTag", "download finished");
//                Uri uri = Uri.parse(localFilePath);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            }
        });
    }

    protected void RemoveStausBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
    }

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your logic
            } else {
                // Permission denied, inform the user to grant the permission
            }
        }
    }
}