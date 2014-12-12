package pegatrampo.webinfor.com.pegatrampo;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.IntentSender.SendIntentException;
import android.content.Intent;
import android.widget.Toast;

// Imports do google(+) plus
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;

// Importando o plus cliente
import com.google.android.gms.plus.*;*/


/**
 * Created by 98287028191 on 10/12/14.
 */

// Sua atividade detecta quando a conexão foi estabelecida ou não, implementando as interfaces ConnectionCallbacks e OnConnectionFailedListener.

public class LogarFragment extends Activity {

    private static final String TAG = "ExampleActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_main,container,false);
        // teste de commit github
        // Commit de testes sdsd dfasdfa
        //d asdfasdfas dfsasdf asdfasdfs asdfasdfasd
    }

/*dd
    //implements View.OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {


    private ProgressDialog mConnectionProgressDialog;
    private PlusClient mPlusClient = new Builder(this, this, this)
            .setVisibleActivities("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
            .build();
    private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // A barra de progresso deve ser exibida se a falha de conexão não for resolvida.
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlusClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlusClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
            } catch (SendIntentException e) {
                mPlusClient.connect();
            }
        }
        // Salvar o resultado e solucionar a falha de conexão mediante clique do usuário.
        mConnectionResult = result;
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
            mConnectionResult = null;
            mPlusClient.connect();
        }
    }

    public void onConnected() {
        String accountName = mPlusClient.getAccountName();
        Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "disconnected");*//*
    }*/


}