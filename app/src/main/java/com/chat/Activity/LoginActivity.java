package com.chat.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.BackgroundWork;
import com.chat.Completion;
import com.chat.R;
import com.chat.Tasks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.entity.StringEntity;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.protocol.HTTP;

public class LoginActivity extends AppCompatActivity {
    JSONObject jsonObject;
    String success = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        final EditText username = (EditText) findViewById(R.id.user_name);
        final EditText password = (EditText) findViewById(R.id.password);
        TextView tvSignUp = (TextView) findViewById(R.id.sign_up);
        TextView tvForgotPasswor = (TextView) findViewById(R.id.forgot_password);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(username.getText().toString(), password.getText().toString());
            }
        });
    }

    void login(final String userName, final String password) {
        Tasks.executeInBackground(this, new BackgroundWork<Object>() {
            @Override
            public Object doInBackground() {
                String result;
                HttpClient client = new DefaultHttpClient();
                try {
                    String url = getString(R.string.base_url) + getString(R.string.login_url);
                    HttpPost post = new HttpPost(url);
                    JSONObject object = new JSONObject();
                    object.put("email", userName);
                    object.put("password", password);

                    StringEntity entity = new StringEntity(object.toString());
                    post.setHeader(HTTP.CONTENT_TYPE,
                            "application/json");
                    post.setEntity(entity);
                    HttpResponse response = client.execute(post);

                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer content = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        content.append(line);
                    }

                    jsonObject = new JSONObject(content.toString());

                    Log.i("response ", response.toString());
                    Log.i("json value ", jsonObject.toString());

                    result = success;
                } catch (Exception e) {
                    result = e.getMessage();
                }
                return result;
            }

        }, new Completion<Object>() {
            @Override
            public void onSuccess(Context context, Object result) {
                if (success.equals(result)) {
                    try {
                        if (jsonObject.get("responseCode").equals("200")) {
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject object = (JSONObject) jsonObject.get("alertMessage");
                            Toast.makeText(LoginActivity.this, object.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Context context, Exception e) {
            }

        });
    }
}
