package com.integration.popdeemtest;

import android.app.Application;

import com.popdeem.sdk.core.PopdeemSDK;
import com.popdeem.sdk.core.utils.PDSocialUtils;

import io.fabric.sdk.android.Fabric;

/**
 * Created by innerglowitsolutions on 27/04/16.
 */
public class FirstActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, PDSocialUtils.getTwitterKitForFabric(this));

        //Initializing PopDeem SDK
        PopdeemSDK.initializeSDK(this);
        PopdeemSDK.enableSocialLogin(MainActivity.class, 3);
    }
}
