package com.example.usercenter;

import android.content.Intent;

import com.example.base.BaseApplication;
import com.example.common.autoservice.IUserCenterService;
import com.google.auto.service.AutoService;

@AutoService({IUserCenterService.class})
public class IUserCenterServiceImpl implements IUserCenterService {


    @Override
    public boolean isLogined() {
        return false;
    }

    @Override
    public void login() {
        Intent intent = new Intent(BaseApplication.sApplication, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.sApplication.startActivity(intent);
    }
}
