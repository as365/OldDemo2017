package com.mvpdemo.login.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class LoginModel implements ILogin {
    @Override
    public void checkRight(String name, String pwd, CallBack callBack) {
        if("lcj".equals(name)&&"123456".equals(pwd)){
            callBack.onSuccess();
        }else {
            callBack.onFail();
        }
    }
}
