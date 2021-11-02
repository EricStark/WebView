package com.example.common.autoservice;

/**
 * UserCenter 模块对 common的接口下沉
 */
public interface IUserCenterService {

    boolean isLogined();

    void login();
}
