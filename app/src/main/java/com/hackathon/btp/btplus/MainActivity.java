package com.hackathon.btp.btplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.hackathon.btp.btplus.adapter.CompromissoCustomAdapter;
import com.hackathon.btp.btplus.model.Compromisso;
import com.hackathon.btp.btplus.model.Payload;
import com.hackathon.btp.btplus.service.APIClient;
import com.hackathon.btp.btplus.service.APIInterface;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;

import android.support.v7.widget.Toolbar;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private final int SPEECH_RECOGNITION_CODE = 1;
    private ImageView btnMicrophone;
    private TextToSpeech t1;
    private ProgressDialog progress;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progress = ProgressDialog.show(MainActivity.this, "Carregando", "Aguarde alguns instantes...", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMicrophone = findViewById(R.id.btn_microphone);

        btnMicrophone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSpeechToText();

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 4000);
            }
        });

        context = this;

        // Instance RecyclerView for list biddings
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView = (RecyclerView) findViewById(R.id.listCompromisso);
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        progress.dismiss();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private TextToSpeech myTTS;
    private String textToSpeak;

    public void speak(String text) {
        textToSpeak = text;

        if (myTTS == null) {
            try {
                myTTS = new TextToSpeech(this, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sayText(textToSpeak);

        Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
    }

    private void sayText(String textToSpeak) {
        myTTS.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH,     null);

    }


    public void onInit(int initStatus) {
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.getDefault());
                }
            }
        });
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    protected void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                R.string.action);
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), R.string.sorry,
                    Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    progress = ProgressDialog.show(MainActivity.this, "Carregando", "Aguarde alguns instantes...", true);

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);

                    Log.e("audio0000",  "" + text.toUpperCase());

                    if(payload == null)
                        payload = new Payload();

                    payload.setText(text);


                    interact();
                }
                break;
            }
        }
    }

    private APIInterface apiService;
    private Call<Payload> callBalance;

    private Payload payload;

    private RecyclerView recyclerView;
    private ArrayList<Compromisso> listCompromisso;

    protected void interact() {

        apiService = APIClient.getService().create(APIInterface.class);
        callBalance = apiService.postVoice(payload);

        Log.e("INIT REQUEST", "" + payload.getText());

        try {
            getSSLSocketFactory();
        }catch (Exception e){
            Log.e("ERRO", "" + e);
        }
        Log.i("Request in API", "" + callBalance.request().url().toString());

        listCompromisso = new ArrayList<>();

        callBalance.enqueue(new Callback<Payload>() {
            @Override
            public void onResponse(Call<Payload> call, Response<Payload> response) {
                if (response.raw().code() == 200) {

                    Payload payloadResponse = response.body();

                    speak(payloadResponse.getOutput());

                    payload = payloadResponse;

                    if(payload.getCompromissos() != null ) {
                        if(payload.getCompromissos().size() > 0) {
                            for (Compromisso compromisso : payload.getCompromissos()) {
                                listCompromisso.add(new Compromisso(compromisso.getTitulo(), compromisso.getStatus(), compromisso.getTipo(), compromisso.getTipoCompromisso(), compromisso.getDataVencimento()));
                            }

                            CompromissoCustomAdapter compromissoCustomAdapter;
                            compromissoCustomAdapter = new CompromissoCustomAdapter(MainActivity.this, listCompromisso);

                            recyclerView.setAdapter(compromissoCustomAdapter);
                        }
                    } else {
                        listCompromisso = new ArrayList<Compromisso>();
                        CompromissoCustomAdapter compromissoCustomAdapter;
                        compromissoCustomAdapter = new CompromissoCustomAdapter(MainActivity.this, listCompromisso);

                        recyclerView.setAdapter(compromissoCustomAdapter);

                    }



                    Log.e("RESULT REQUEST", payload.getOutput());
                    progress.dismiss();
                }

                Log.e("RESULT REQUEST", "" + response.body());
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Payload> call, Throwable t) {
                Log.e("BALANCE", t.toString());
                progress.dismiss();
            }
        });
    }

    public SSLSocketFactory getSSLSocketFactory()
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = getResources().openRawResource(R.raw.cert); // your certificate file
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.oportunidade) {
            Intent intent = new Intent(MainActivity.this, OpportunityActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}