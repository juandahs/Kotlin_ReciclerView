package com.example.reciclerview

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.reciclerview.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private  lateinit var binding: ActivityMainBinding
    private lateinit var linerLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash : SplashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        //Inicio de configuraciones de shareReference
        val preference = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = preference.getBoolean(R.string.sp_ft.toString(), true)
        Log.i("VARIABLE_SHARE_PREFERENCE", "El valor sharePreference = ${isFirstTime}");


        if (isFirstTime) {
            val dialogView = layoutInflater.inflate(R.layout.dialogo, null)
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false) //Se impide que se de click por fuera y se cierre el dialogo.
                .setPositiveButton(R.string.btnSave, {_, _ ->
                    val userName = dialogView.findViewById<TextInputEditText>(R.id.etUserName).toString()
                    //Todo lo que va a quí es para editar el sharePreference
                    with(preference.edit()){
                        putBoolean(getString(R.string.sp_ft), false)
                        putString(getString(R.string.sp_userName), userName)
                            .apply()
                    }
                }).show()
        }

        Log.i("VARIABLE_SHARE_PREFERENCE2"
            , "${preference.getString(getString(R.string.sp_userName).toString(), "N/A")}"
        )

        //Fin de configuraciones ShareREferences
        userAdapter = UserAdapter(getUsers())
        linerLayoutManager = LinearLayoutManager(this)

        binding.rvMain.apply {
            adapter = userAdapter
            layoutManager = linerLayoutManager
        }
    }

    private fun getUsers(): List<User> {

            val users = mutableListOf<User>()
            val url = "https://reqres.in/api/users"
            val queue = Volley.newRequestQueue(this)
            val jsonObjRect = JsonObjectRequest(
                Request.Method.GET, url, null,{ res ->
                //success
                    val arrayApi = res.getJSONArray("data")
                    for (i in 0..<arrayApi.length())
                    {
                        val jsonObj = arrayApi.getJSONObject(i)
                        var user =  User(
                            jsonObj.getInt("id"),
                            jsonObj.getString("first_name"),
                            jsonObj.getString("last_name"),
                            jsonObj.getString("email"),
                            jsonObj.getString("avatar"),
                        )
                        users.add(user)
                    }

                },
                //Error
                {
                    err -> err.printStackTrace()
                }
            )

            queue.add(jsonObjRect)
            return users

    }
}