package es.source.code.activity.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import es.source.code.activity.Bean.SendMailUtil;
import es.source.code.activity.R;


public class EmailActivity extends AppCompatActivity {
    private EditText et_email,et_email_content;
    private Button btn_help_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_email);
        et_email= (EditText)findViewById(R.id.et_help_email);
        et_email_content=(EditText)findViewById(R.id.et_help_email_content);
        btn_help_email=(Button)findViewById(R.id.btn_help__email_send);
        btn_help_email.setOnClickListener(listener);
    }
    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            SendMailUtil.send(et_email.getText().toString(), et_email_content.getText().toString());
            Toast.makeText(getApplicationContext(), "求助邮件发送成功", Toast.LENGTH_SHORT).show();
        }
    };
    public void sendFileMail(View view) {

        File file = new File(Environment.getExternalStorageDirectory()+ File.separator+"test.txt");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            String str = "hello world";
            byte[] data = str.getBytes();
            os.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if (os != null)os.close();
            } catch (IOException e) {
            }
        }
    }
}