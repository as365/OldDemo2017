package com.mvpdemo.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvpdemo.R;
import com.mvpdemo.login.presenter.Impl.LoginPresenter;
import com.mvpdemo.login.presenter.Interface.IPresenter;
import com.mvpdemo.login.view.Interface.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView {
    private EditText edName;
    private EditText edPwd;
    private Button button;
    private IPresenter iPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName = (EditText) findViewById(R.id.name);
        edPwd = (EditText) findViewById(R.id.pwd);
        button = (Button) findViewById(R.id.button);
        iPresenter = new LoginPresenter(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPresenter.checkRightUser();
            }
        });
    }

    @Override
    public String getUserName() {
        return edName.getText().toString();
    }

    @Override
    public String getUserPwd() {
        return edPwd.getText().toString();
    }

    @Override
    public void showSuccessMessage() {
        Log.i("TAG", "showSuccessMessage: 啦啦啦");
    }

    @Override
    public void showFailMessage() {
        Log.i("TAG", "showFailMessage: 呜呜呜");
    }
}
