package com.example.testmkm2020_derrisandyprasthamaheda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.testmkm2020_derrisandyprasthamaheda.Model.SharedPref
import com.example.testmkm2020_derrisandyprasthamaheda.Model.URLs
import com.example.testmkm2020_derrisandyprasthamaheda.Model.User
import com.example.testmkm2020_derrisandyprasthamaheda.Model.VolleySingleton
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {
    internal lateinit var editTextUsername: EditText
    internal lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (SharedPref.getInstance(this).isLoggedIn) {
            if(SharedPref.getInstance(this).user.loginstate == "Login") {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }else {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)

        buttonRegister.setOnClickListener(View.OnClickListener {
            registerUser()
        })

        textViewLogin.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        })
    }
    override fun onBackPressed() {
        finish()
    }
    private fun registerUser() {
        val username = editTextUsername.text.toString().trim { it <= ' ' }
        val password = editTextPassword.text.toString().trim { it <= ' ' }

        if (TextUtils.isEmpty(username)) {
            editTextUsername.error = "Please enter username"
            editTextUsername.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.error = "Enter a password"
            editTextPassword.requestFocus()
            return
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_REGISTER,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()

                        val userJson = obj.getJSONObject("user")
                        val user = User(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getString("logintime"),
                            userJson.getString("loginstate")
                        )
                        SharedPref.getInstance(applicationContext).userLogin(user)
                        finish()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
        finish()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }
}
