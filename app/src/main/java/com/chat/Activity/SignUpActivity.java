package com.chat.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chat.BackgroundWork;
import com.chat.Completion;
import com.chat.R;
import com.chat.Tasks;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.entity.StringEntity;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.protocol.HTTP;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUserName, etEmailId, etPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        etUserName = (EditText) findViewById(R.id.user_name);
        etEmailId = (EditText) findViewById(R.id.email_id);
        etPassword = (EditText) findViewById(R.id.password);
        btnSignUp.setOnClickListener(this);
    }

    private void connectWebSocket() {
        Tasks.executeInBackground(this, new BackgroundWork<Object>() {
            @Override
            public Object doInBackground() {
                String result = null;
                HttpClient client = new DefaultHttpClient();
                try {
                    String url = getString(R.string.base_url) + getString(R.string.register_url);
                    HttpPost post = new HttpPost(url);
                    JSONObject object = new JSONObject();
                    object.put("email", etEmailId.getText().toString());
                    object.put("username", etUserName.getText().toString());
                    object.put("password", etPassword.getText().toString());

                    StringEntity entity = new StringEntity(object.toString());
                    post.setHeader(HTTP.CONTENT_TYPE,
                            "application/json");
                    post.setEntity(entity);
                    HttpResponse response = client.execute(post);

                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer content = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        content.append(line);
                    }

                    JSONObject o = new JSONObject(content.toString());

                    Log.i("response ", response.toString());
                    Log.i("json value ", o.toString());

                } catch (Exception e) {
                    result = e.getMessage();
                }
                return result;
            }

        }, new Completion<Object>() {
            @Override
            public void onSuccess(Context context, Object result) {

            }

            @Override
            public void onError(Context context, Exception e) {
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                connectWebSocket();
                break;
        }
    }
}
