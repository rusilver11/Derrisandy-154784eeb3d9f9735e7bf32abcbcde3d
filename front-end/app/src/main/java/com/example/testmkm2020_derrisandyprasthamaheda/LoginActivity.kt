package com.example.testmkm2020_derrisandyprasthamaheda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.testmkm2020_derrisandyprasthamaheda.Model.SharedPref
import com.example.testmkm2020_derrisandyprasthamaheda.Model.URLs
import com.example.testmkm2020_derrisandyprasthamaheda.Model.User
import com.example.testmkm2020_derrisandyprasthamaheda.Model.VolleySingleton
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class LoginActivity : AppCompatActivity() {
    lateinit var etName: EditText
    internal lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (SharedPref.getInstance(this).isLoggedIn) {
            if(SharedPref.getInstance(this).user.loginstate == "") {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }else {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
//            else if(SharedPref.getInstance(this).user.loginstate == "Login") {
//                finish()
//                startActivity(Intent(this, LoginActivity::class.java))
//            }
        }
        etName = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etUserPassword)
        btnLogin.setOnClickListener(View.OnClickListener {
            userLogin()
        })
        tvRegister.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        })
    }

    private fun userLogin() {
        val username = etName.text.toString()
        val password = etPassword.text.toString()

        if (TextUtils.isEmpty(username)) {
            etName.error = "Please enter your username"
            etName.requestFocus()
            return
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Please enter your password"
            etPassword.requestFocus()
            return
        }
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_LOGIN,
            Response.Listener { response ->


                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        val userJson = obj.getJSONObject("user")

                        val user = User(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getString("logintime"),
                            userJson.getString("loginstate")
                        )
                        SharedPref.getInstance(applicationContext).userLogin(user)
                        if(user.loginstate.toString() == ""){
                            finish()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }
}
