package es.source.code.activity.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.activity.R;

public class MassageActivity extends AppCompatActivity {
    private Button btn_send;
    private EditText et_phone;
    private EditText et_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.massage);
        ActivityCompat.requestPermissions(MassageActivity.this, new String[]{"android.permission.SEND_SMS",}, 1);
        btn_send = (Button)this.findViewById(R.id.btn_help_send);
        et_phone = (EditText)this.findViewById(R.id.et_help_phone);
        et_content = (EditText)this.findViewById(R.id.et_help_content);
        btn_send.setOnClickListener(listener);

        et_phone.setText("5554");
        et_content.setText("test scos helper");
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String phone = et_phone.getText().toString();
            String context = et_content.getText().toString();
            SmsManager manager = SmsManager.getDefault();
            ArrayList<String> list = manager.divideMessage(context);  //因为一条短信有字数限制，因此要将长短信拆分
            for(String text:list){
                manager.sendTextMessage(phone, null, text, null, null);
            }
            Toast.makeText(getApplicationContext(), "求助短信发送成功", Toast.LENGTH_SHORT).show();
        }
    };
}
