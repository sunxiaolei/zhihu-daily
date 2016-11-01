package xiaolei.sun.zhihu_daily.ui.settings;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;

import xiaolei.sun.zhihu_daily.R;

/**
 * Created by sunxl8 on 2016/11/1.
 */

public class UpdateService extends Service {

    //下载
    private Long taskId;
    private DownloadManager manager;
    private String appSaveName = "ZhDaily.apk";
    private String downloadUrl;
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            downloadUrl = intent.getStringExtra("DOWNLOAD_URL");
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                        File apkFile = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + appSaveName);
                        if (apkFile.exists()) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
                                    "application/vnd.android.package-archive");
                            context.startActivity(i);
                        }
                    }
                }
            };
            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            new Thread(new DownloadRunable()).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class DownloadRunable implements Runnable {

        @Override
        public void run() {
            startDownload();
        }

        private void startDownload() {
            File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + appSaveName);
            if (file.exists()) {
                file.delete();
            }
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
            request.setDestinationInExternalFilesDir(UpdateService.this, Environment.DIRECTORY_DOWNLOADS, appSaveName);
            request.setTitle(getString(R.string.app_name));
            request.setDescription(getString(R.string.downloading));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            taskId = manager.enqueue(request);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
