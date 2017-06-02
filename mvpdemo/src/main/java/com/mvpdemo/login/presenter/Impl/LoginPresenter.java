package com.mvpdemo.login.presenter.Impl;

import android.util.Log;

import com.mvpdemo.login.model.Impl.LoginModel;
import com.mvpdemo.login.model.Interface.ILogin;
import com.mvpdemo.login.presenter.Interface.IPresenter;
import com.mvpdemo.login.view.Interface.IMainView;

/**
 * Created by Administrator on 2017/6/2.
 */

public class LoginPresenter implements IPresenter {
    private ILogin iLogin;
    private IMainView iMainView;

    public LoginPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        iLogin = new LoginModel();
    }

    @Override
    public void checkRightUser() {
        iLogin.checkRight(iMainView.getUserName(), iMainView.getUserPwd(), new ILogin.CallBack() {
            @Override
            public void onSuccess() {
                Log.i("TAG", "CALL onSuccess: ");
            }

            @Override
            public void onFail() {
                Log.i("TAG", "CALL onFail: ");
            }
        });
    }
}
