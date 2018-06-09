package trek.visdrotech.com.trek_o_hunt;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.hypertrack.lib.models.User;
import com.hypertrack.lib.models.UserParams;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import trek.visdrotech.com.trek_o_hunt.utils.*;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static final String LOG_TAG="LOGIN_FRAGMENT";
    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
//    public static final String HT_QUICK_START_SHARED_PREFS_KEY = "com.hypertrack.quickstart:SharedPreference";

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.xml.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                Utils.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Utils.VALID_EMAIL_ADDRESS_REGEX;

        Matcher m = p.matcher(getEmailId);
//        Intent i = new Intent(getActivity(),HomePageActivity.class);
//        Bundle b = new Bundle();
//        b.putString("name","name");
//        b.putString("phone","phone");
//        b.putString("uuid","uuid");
//        i.putExtras(b);
//        startActivity(i);
        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    getString(R.string.email_invalid));
            // Else do login and do your stuff
        else
            sendData(getEmailId,getPassword);
//            Toast.makeText(getActivity(), "Do Login.", Toast.LENGTH_SHORT)
//                    .show();

    }

    private void saveUser(User user) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.HT_QUICK_START_SHARED_PREFS_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", new GsonBuilder().create().toJson(user));
        editor.putBoolean("loginStatus", true);
        editor.apply();
    }

    private void checkForLocationSettings() {
        // Check for Location permission
        if (!HyperTrack.checkLocationPermission(getActivity())) {
            HyperTrack.requestPermissions(getActivity());
            return;
        }

        // Check for Location settings
        if (!HyperTrack.checkLocationServices(getActivity())) {
            HyperTrack.requestLocationServices(getActivity());
        }
    }


    public void sendData(String email, String pass){
        RequestParams params = new RequestParams();
        params.put("username", email);
        params.put("password", pass);
        serverRestClient.post("trekohunt/login", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
//                Login with our server successful
                try {
                    Log.d(LOG_TAG, "In response");
                    Log.d(LOG_TAG, response.toString());
                    Integer status = response.getInt("status");
                    if (status==200){
                        String phone = response.getString("phone");
                        String name = response.getString("name");
                        String uuid = response.getString(("user_id"));

                        UserParams userParams = new UserParams().setName(name).setPhone(phone).setUniqueId(uuid);
                        HyperTrack.getOrCreateUser(userParams, new HyperTrackCallback() {
                            @Override
                            public void onSuccess(@NonNull SuccessResponse successResponse) {
//                                Hypertrack object found
                                User user = (User) successResponse.getResponseObject();
                                saveUser(user);
                                Log.d(LOG_TAG,"Login successful");
//                        User has sucessfully logged in take him to homepage
                                Intent i = new Intent(getActivity(),HomePageActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                Bundle b = new Bundle();
//                                b.putString("name","name");
//                                b.putString("phone","phone");
//                                b.putString("uuid","uuid");
//                                i.putExtras(b);
                                startActivity(i);

                            }

                            @Override
                            public void onError(@NonNull ErrorResponse errorResponse) {
//                                    Some error occured
                                new CustomToast().Show_Toast(getActivity(), view,
                                        R.string.login_fail
                                                + " " + errorResponse.getErrorMessage());
//                                Toast.makeText(getActivity(), R.string.login_fail
//                                                + " " + errorResponse.getErrorMessage(),
//                                        Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                    else {
                        new CustomToast().Show_Toast(getActivity(), view,
                                getString(R.string.login_fail));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    new CustomToast().Show_Toast(getActivity(), view,
                            getString(R.string.login_fail));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(LOG_TAG,errorResponse.toString());
                new CustomToast().Show_Toast(getActivity(), view,
                        getString(R.string.login_fail));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                new CustomToast().Show_Toast(getActivity(), view,
                        getString(R.string.login_fail));
            }


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);

        if (requestCode == HyperTrack.REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                // Check if Location Settings are enabled to proceed
                checkForLocationSettings();

            } else {
                // Handle Location Permission denied error
                new CustomToast().Show_Toast(getActivity(), view,
                        getString(R.string.location_permisssion_denied));
//                Toast.makeText(this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HyperTrack.REQUEST_CODE_LOCATION_SERVICES) {
            if (resultCode == Activity.RESULT_OK) {
                // Check if Location Settings are enabled to proceed
                checkForLocationSettings();

            } else {
                // Handle Enable Location Services request denied error
                new CustomToast().Show_Toast(getActivity(), view,
                        getString(R.string.enable_location_settings));
//                Toast.makeText(this, R.string.enable_location_settings, Toast.LENGTH_SHORT).show();
            }
        }
    }



}
//    public void sendData(String email, String pass) {
//        RequestParams params = new RequestParams();
//        params.put("email", email);
//        params.put("pass", pass);
//        if (flag == 1) {
//
////        } else {
////            params.put("first_name", f_name.getText().toString());
////            params.put("last_name", l_name.getText().toString());
////            params.put("contact_no", contact_no.getText().toString());
////            params.put("password", password.getText().toString());
////            params.put("email", email_id.getText().toString());
////        }
////        params.setUseJsonStreamer(true);
//
////        params.put("first_name", f_name.getText().toString());
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//
//
//        AsyncHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
////            @Override
//            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//
////                String str = new String(bytes, "UTF-8");
//                try {
//                    String login_status;
//                    login_status = response.getString("status");
//                    if (login_status.equals("1")) {
//                        dialog.dismiss();
//                        Log.d("Login", "Success");
//                        startActivity(new Intent(homepage.this, MainPage.class));
//                        Toast.makeText(homepage.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
//
//                    } else if (login_status.equals("noemail")) {
//                        dialog.dismiss();
//                        Toast.makeText(homepage.this, "Wrong E-mail entered", Toast.LENGTH_SHORT).show();
////                        register.setVisibility(View.VISIBLE);
//                        Log.d("Login", "No email");
//                    } else if (login_status.equals("0")) {
//                        dialog.dismiss();
//                        Toast.makeText(homepage.this, "Wrong password entered", Toast.LENGTH_SHORT).show();
//                        Log.d("Login", "Fail");
//                    } else if (response.getString("status").equals("dup_user")) {
//                        dialog.dismiss();
//                        f_name.setVisibility(View.GONE);
//                        l_name.setVisibility(View.GONE);
//                        password.setVisibility(View.GONE);
//                        re_password.setVisibility(View.GONE);
//                        contact_no.setVisibility(View.GONE);
//                        email_id.setVisibility(View.GONE);
//
//                        email.setVisibility(View.VISIBLE);
//                        pass.setVisibility(View.VISIBLE);
//                        Toast.makeText(homepage.this, "Hi " + f_name.getText().toString() + ". You are already registered. Please log in to continue.", Toast.LENGTH_LONG).show();
////                        Intent login_page = new Intent("com.example.ayush.krishi_help.activities.homepage") ;
////                        startActivity(login_page);
//                    } else if (response.getString("status").equals("ok")) {
//                        dialog.dismiss();
//                        f_name.setVisibility(View.GONE);
//                        l_name.setVisibility(View.GONE);
//                        password.setVisibility(View.GONE);
//                        re_password.setVisibility(View.GONE);
//                        contact_no.setVisibility(View.GONE);
//                        email_id.setVisibility(View.GONE);
//
//                        email.setVisibility(View.VISIBLE);
//                        pass.setVisibility(View.VISIBLE);
//                        Toast.makeText(homepage.this, "Hi " + f_name.getText().toString() + ". You have been successfully registered", Toast.LENGTH_SHORT).show();
//
////                      Intent login_page = new Intent("com.example.ayush.krishi_help.activities.homepage") ;
////                        startActivity(login_page);
//                    }
//
//                    Log.d("JSON", response.getString("status"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
////                response.getInt("status");
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//                dialog.dismiss();
//                Toast.makeText(homepage.this, "Please try again later", Toast.LENGTH_SHORT).show();
//            }
//        };
//
//
//        client.post(url, params, responseHandler);
//    }
//
//}


