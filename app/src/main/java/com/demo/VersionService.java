package com.demo;
import android.content.Intent;
import android.util.Log;

import com.allenliu.versionchecklib.AVersionService;
public class VersionService extends AVersionService {

    private String downloadUrl="http://101.200.196.164:8070/com.weishuhui.apk";
    private String title="重大更新";
    private String updateMsg="界面更加美观了！";

    @Override
    public void onResponses(AVersionService service, String response) {
        Intent intent = new Intent(this,Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        stopSelf();;
    }
}
