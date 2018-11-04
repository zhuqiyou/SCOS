package es.source.code.activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.widget.EditText;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.text.TextWatcher;
import es.source.code.activity.R;
import es.source.code.activity.model.User;
import es.source.code.activity.model.Util;


public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {
    private Button btn_login,btn_return,btn_zhuce;
    private EditText et_name,et_psd;
    private ProgressDialog mProgressDialog;

    private EditText EtAccount;
    private EditText EtPassword;
    boolean bAcconut;
    boolean bPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        et_name =(EditText)findViewById(R.id.editText);
        et_psd = (EditText)findViewById(R.id.editText2);
        btn_login = (Button) findViewById(R.id.button3);
        btn_login.setOnClickListener(this);
        btn_return =(Button) findViewById(R.id.button7);
        btn_return.setOnClickListener(this);
        btn_zhuce=(Button) findViewById(R.id.btn_zhuce);
        btn_zhuce.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);

        Map<String,String> userMap = Util.getInfo(this);
        et_name.setText(userMap.get("name"));

        initDate();

//        正则表达式
        /*
        账号管理
        文本监听addTextChangedListener
        TextWatcher
         */
        EtAccount = ((EditText) findViewById(R.id.editText));
        EtAccount.addTextChangedListener(new TextWatcher() {
            //文字改变前的回调方法
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            //文字改变时的回调方法
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            //文字改变后的回调方法
            @Override
            public void afterTextChanged(Editable s) {
                //得到Editable对象的String
                String phoneStr = s.toString();
                //判断输入的内容是否为phone
                bAcconut = isAccountNumber(phoneStr);
//
            }
        });

        /*
        密码管理

         */
        EtPassword = ((EditText) findViewById(R.id.editText2));
        EtPassword.addTextChangedListener(new TextWatcher() {
            //文字改变前的回调方法
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            //文字改变时的回调方法
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            //文字改变后的回调方法
            @Override
            public void afterTextChanged(Editable s) {
                //得到Editable对象的String
                String passwordStr = s.toString();
                //判断输入的内容是否为正确的密码
                bPassword = isPassword(passwordStr);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button3://登录

                String name = et_name.getText().toString().trim();
                String password = et_psd.getText().toString().trim();
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                mProgressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
                mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                mProgressDialog.setIcon(R.mipmap.ic_launcher); // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                mProgressDialog.setTitle("注意：");
                //设置可点击的按钮，最多有三个(默认情况下)
                mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        });
                mProgressDialog.setMessage("请稍后：");
                mProgressDialog.show();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
//                        String name = et_name.getText().toString().trim();
//                        String password = et_psd.getText().toString().trim();
                        // TODO Auto-generated method stub
                        try {
                            Thread.sleep(2000);
                            if(bAcconut&&bPassword){
                                User loginUser=new User();
                                loginUser.getUserName();
                                loginUser.getPassword();
                                loginUser.setOldUser(true);
                                Intent intent = new Intent();
                                intent.putExtra("message", "LoginSuccess");
                                setResult(2, intent);
                                finish();
                            }
                            // mProgressDialog.dismiss();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                Log.i("LoginOrRegister", "记住密码" + name + "," + password);
                boolean isSaveSuccess = Util.saveInfo(LoginOrRegister.this, name, password);

                break;
            case R.id.button7://返回
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                intent.putExtra("message", "Return");
                startActivity(intent);

                finish();
                break;
            case R.id.btn_zhuce://zhuce

                if(bAcconut&&bPassword){
                    User loginUser=new User();
                    loginUser.getUserName();
                    loginUser.getPassword();
                    loginUser.setOldUser(true);

                    Intent intent_zhuce = new Intent();
                    intent_zhuce.putExtra("message", "RegisterSuccess");
                    setResult(2, intent_zhuce);
                    finish();
                    break;
                }else {
                    Intent intent_zhuce = new Intent();
                    intent_zhuce.putExtra("message", "false");
                    setResult(2, intent_zhuce);
                    finish();
                }
        }
    }
    private boolean isAccountNumber(String phoneStr) {
        String regex = "([a-zA-Z0-9]{6,12})";   //定义账号格式的正则表达式
        Pattern p = Pattern.compile(regex);//设定查看模式
        Matcher m = p.matcher(phoneStr);  //判断Str是否匹配，返回匹配结果
        return m.find();
    }
    private boolean isPassword(String passwordStr) {
        String regex = "([a-zA-Z0-9]{6,12})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(passwordStr);
        return m.find();

    }
    private void initDate(){
        Intent intent = getIntent();
        //SCOSEntry跳到MainActivity
        String data = intent.getStringExtra("message");
    }

    @Override
    public void onBackPressed(){
        Intent intent_zhuce = new Intent();
        intent_zhuce.putExtra("message", "false");
        setResult(2,intent_zhuce);
        finish();
    }
}
