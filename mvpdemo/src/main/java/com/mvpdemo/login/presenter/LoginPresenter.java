package com.mvpdemo.login.presenter;

import com.mvpdemo.login.model.LoginModel;
import com.mvpdemo.login.model.ILogin;
import com.mvpdemo.login.view.IMainView;

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
                iMainView.setResult("登录成功!");
            }

            @Override
            public void onFail() {
                iMainView.setResult("登录失败!");
            }
        });
    }
}
