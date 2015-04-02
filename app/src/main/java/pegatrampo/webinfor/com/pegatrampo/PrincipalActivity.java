package pegatrampo.webinfor.com.pegatrampo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PrincipalActivity extends Activity {

    private static final String TAG = "PegaTrampos - AsyncTask";
    private UiLifecycleHelper uiHelper;
    private String Nome;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChanged(session, state, exception);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        LoginButton lb = (LoginButton) findViewById(R.id.authButton);
        lb.setPublishPermissions(Arrays.asList("email", "public_profile", "user_friends"));
        Log.i("PEGATRAMPOS", "Valor" + Nome + "gfjgf");
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent telaInicioApp = new Intent(PrincipalActivity.this, MainActivity.class);
                PrincipalActivity.this.startActivity(telaInicioApp);


                Bundle b = new Bundle();
                b.putString("Nome", Nome); //Your id
                telaInicioApp.putExtras(b); //Put your id to your next Intent
                startActivity(telaInicioApp);

                //PrincipalActivity.this.finish();

            }
        });


        // metodo para buscar qual KeyHash estamos usando no aplicativo
        try {
            PackageInfo info = getPackageManager().getPackageInfo("pegatrampo.webinfor.com.pegatrampo", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null && (session.isClosed() || session.isOpened())) {
            onSessionStateChanged(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uiHelper.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        uiHelper.onSaveInstanceState(bundle);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    // METHODS FACEBOOK
    public void onSessionStateChanged(final Session session, SessionState state, Exception exception) {
        if (session != null && session.isOpened()) {
            Log.i("Script", "Usuário conectado");
            Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {

                        Log.i(TAG, "Nome do usuario: " + user.getFirstName());
                        Log.i(TAG, "Email do usuario: " + user.getProperty("email").toString());
                        Log.i(TAG, "ID do usuario: " + user.getId());

                        TextView tv = (TextView) findViewById(R.id.name);
                        tv.setText(user.getFirstName() + " " + user.getLastName());

                        tv = (TextView) findViewById(R.id.email);
                        tv.setText(user.getProperty("email").toString());

                       /* tv = (TextView) findViewById(R.id.id);
                        tv.setText(user.getId());*/

                        ProfilePictureView ppv = (ProfilePictureView) findViewById(R.id.fbImg);
                        ppv.setProfileId(user.getId());


                        //PrincipalActivity.this.finish();

                        //getFriends(session);
                    }
                }
            }).executeAsync();
        } else {
            Log.i("Script", "Usuário não conectado");
        }
    }


   /* public void getFriends(Session session){
        Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
            @Override
            public void onCompleted(List<GraphUser> users, Response response) {
                if(users != null){
                    Log.i("Script", "Friends: "+users.size());
                }
                Log.i("Script", "response: "+response);
            }
        }).executeAsync();
    }*/


}
