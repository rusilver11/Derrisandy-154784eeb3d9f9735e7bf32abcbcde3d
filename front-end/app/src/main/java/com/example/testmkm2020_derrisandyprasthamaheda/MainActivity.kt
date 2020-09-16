package com.example.testmkm2020_derrisandyprasthamaheda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testmkm2020_derrisandyprasthamaheda.Model.SharedPref
import com.example.testmkm2020_derrisandyprasthamaheda.Model.URLs
import com.example.testmkm2020_derrisandyprasthamaheda.Model.User
import com.example.testmkm2020_derrisandyprasthamaheda.Model.VolleySingleton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogout.setOnClickListener(View.OnClickListener {
            SharedPref.getInstance(applicationContext).logout()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        })
        btnHello.setOnClickListener(){
            loginset()
        }
        tvclock.format24Hour = "hh:mm:ss"
        shusername.setText(SharedPref.getInstance(this).user.username.toString())
    }
    fun loginset(){
        val request = object : StringRequest(
            Method.POST, URLs.URL_LOGINSET,
            Response.Listener { response ->

                Toast.makeText(this,"Operasi Berhasil!", Toast.LENGTH_LONG).show()

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Tidak Dapat Terhubung Ke Server!", Toast.LENGTH_LONG)
                    .show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val hm = HashMap<String,String>()

                hm.put("username",shusername.toString())
                hm.put("logintime",tvclock.text.toString())
                hm.put("loginstate","Login")

                return hm
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)


    }
}
