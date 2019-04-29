package com.qipai.gansuactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qipai.gansuactivity.NetWork.HttpHelper;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.OnNextListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginUserName;
    private EditText loginPsw;
    private Button bt_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        loginUserName = (EditText) findViewById(R.id.loginUserName);
        loginPsw = (EditText) findViewById(R.id.loginPsw);
        bt_Login = (Button) findViewById(R.id.bt_Login);
        bt_Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Login:

                String userName = loginUserName.getText().toString().trim();
                String psw = loginPsw.getText().toString().trim();
                if (!TextUtils.isEmpty(userName)){
                    if (!TextUtils.isEmpty(psw)){
                        initRSA(userName,psw);
                    }else{
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void initRSA(final String userName, final String psw) {
        HttpHelper.getRSA(new Callback.RSACallback() {
            @Override
            public void getRSASuccess(RSAEntity rsaEntity) {
                try {
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    BigInteger modulus = new BigInteger(rsaEntity.getModulus(),16);
                    BigInteger publicExponent = new BigInteger(rsaEntity.getExponent(),16);
                    RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus,publicExponent);
                    PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
                    Cipher cipher = Cipher.getInstance("RSA");
                    cipher.init(Cipher.ENCRYPT_MODE,publicKey);
                    StringBuilder sb=new StringBuilder();
                    sb.append(psw).append("&").append(System.currentTimeMillis()).reverse();
                    Log.e("RSA",sb.toString());
                    byte[]  encryptData = cipher.doFinal(sb.toString().getBytes());
                    String s=Base64.byte2HexStr(encryptData);
                    HttpHelper.getLogin(userName, s, new Callback.LoginCallback() {
                        @Override
                        public void getLoginSuccess(ResponseBody loginEntity) {
                            try {
                                Toast.makeText(MainActivity.this, loginEntity.string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void getLoginError(String error) {
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void getRSAError(String error) {
                Log.e("QQQ",error);
            }
        });
    }

}
