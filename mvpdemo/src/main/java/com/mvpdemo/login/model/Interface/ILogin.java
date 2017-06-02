package com.mvpdemo.login.model.Interface;

/**
 * Created by Administrator on 2017/6/2.
 */

public interface ILogin {
    public  interface CallBack{
        public void onSuccess();
        public void onFail();
    }

    public void checkRight(String name, String pwd, CallBack callBack);
}
