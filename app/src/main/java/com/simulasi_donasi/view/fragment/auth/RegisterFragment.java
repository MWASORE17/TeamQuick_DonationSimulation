package com.simulasi_donasi.view.fragment.auth;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simulasi_donasi.R;
import com.simulasi_donasi.model.entity.User;
import com.simulasi_donasi.view.activity.AuthActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    private TextView login;
    private TextInputLayout register_name;
    private TextInputLayout register_email;
    private TextInputLayout register_password;
    private TextInputLayout register_repassword;
    private EditText et_name;
    private EditText et_email;
    private EditText et_password;
    private EditText et_repassword;
    private Button btn_register;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View _view = inflater.inflate(R.layout.fragment_register, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view) {
        login = (TextView) view.findViewById(R.id.register_login);
        register_name = (TextInputLayout) view.findViewById(R.id.register_name_container);
        register_email = (TextInputLayout) view.findViewById(R.id.register_email_container);
        register_password = (TextInputLayout) view.findViewById(R.id.register_password_container);
        register_repassword = (TextInputLayout) view.findViewById(R.id.register_repassword_container);
        btn_register = (Button) view.findViewById(R.id.register_register);
        et_name = (EditText) view.findViewById(R.id.register_name);
        et_email = (EditText) view.findViewById(R.id.register_email);
        et_password = (EditText) view.findViewById(R.id.register_password);
        et_repassword = (EditText) view.findViewById(R.id.register_repassword);
    }

    private void event() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity) getActivity()).changefragment(new LoginFragment());
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean _isvalid = true;
                register_name.setErrorEnabled(false);
                register_email.setErrorEnabled(false);
                register_password.setErrorEnabled(false);
                register_repassword.setErrorEnabled(false);

                if (TextUtils.isEmpty(et_name.getText())) {
                    _isvalid = false;
                    register_name.setErrorEnabled(true);
                    register_name.setError("Nama masih kosong");
                } else if (et_name.getText().length() < 7) {
                    _isvalid = false;
                    register_name.setErrorEnabled(true);
                    register_name.setError("Nama minimal 7 huruf/angka");

                } else if (TextUtils.isEmpty(et_email.getText())) {
                    _isvalid = false;
                    register_email.setErrorEnabled(true);
                    register_email.setError("Email masih kosong");
                } else if (!AuthActivity.isemailvalid(et_email.getText().toString())) {
                    _isvalid = false;
                    register_email.setErrorEnabled(true);
                    register_email.setError("Email tidak valid");
                } else if (TextUtils.isEmpty(et_password.getText())) {
                    _isvalid = false;
                    register_password.setErrorEnabled(true);
                    register_password.setError("Password masih kosong");
                } else if (!AuthActivity.ispasswordvalid(et_password.getText().toString())) {
                    _isvalid = false;
                    register_password.setErrorEnabled(true);
                    register_password.setError("Password tidak valid. Password harus mengandung 1 huruf kecil, 1 huruf kapital, 1 nomor, 1 simbol dan minimal 8 karakter");
                } else if (TextUtils.isEmpty(et_repassword.getText())) {
                    _isvalid = false;
                    register_repassword.setErrorEnabled(true);
                    register_repassword.setError("Re-Password masih kosong");
                } else if (!et_password.getText().toString().equals(et_repassword.getText().toString())) {
                    _isvalid = false;
                    register_repassword.setErrorEnabled(true);
                    register_repassword.setError("Password tidak sama");

                }

                if (_isvalid) {
                    User userNew = new User(et_name.getText().toString(), et_email.getText().toString(), et_password.getText().toString(),0);
                    User.users.add(userNew);
                    Toast.makeText(getActivity(),"Register Berhasil",Toast.LENGTH_SHORT).show();
                    ((AuthActivity) getActivity()).changefragment(new LoginFragment());
                }
                else {
                    Toast.makeText(getActivity(),"Register Gagal",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
