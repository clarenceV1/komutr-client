package com.komutr.client.common;

import android.content.Context;

import com.cai.framework.dagger.ActivityScope;

import java.io.File;

import javax.inject.Inject;

@ActivityScope
public class AppFileStore {
    Context context;

    @Inject
    public AppFileStore(Context context) {
        this.context = context;
    }

    private String getCachePath() {
        return context.getCacheDir().getPath() + "/";
    }

    private String getQRcodePath() {
        return getCachePath() + "_QR_Code";
    }

    /**
     * 当前用户的分享二维码路径
     *
     * @return
     */
    public File getQRcodeFile(long userId) {
        File rootFile = new File(getQRcodePath());
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        return new File(rootFile, getQRcodeName(userId));
    }

    public static String getQRcodeName(long userId) {
        return "qr_code_" + userId + ".jpg";
    }

}
