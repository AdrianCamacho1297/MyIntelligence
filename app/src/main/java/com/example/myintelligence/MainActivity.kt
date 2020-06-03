package com.example.myintelligence

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    companion object {
        val EXTRA_CONTROL = "Control"
        val EXTRA_CONTRASENA = "Contrasena"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Shared Preferences
        preferences = getSharedPreferences(Preferencias.DATOS, Context.MODE_PRIVATE)
        val cont = preferences.getString(Preferencias.CONTROL, "No Disponible")
        val pass = preferences.getString(Preferencias.CONTRASENA, "No Disponible")
        //Tab Layout
        toolBar.setTitle("I N T E L L I G E N T")
        setSupportActionBar(toolBar)
        //ViewPager
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        //Icons
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_school_black_24dp)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_timeline_black_24dp)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_face_black_24dp)
        //Data
        val res = intent
        if (res != null && res.hasExtra(EXTRA_CONTROL) && res.hasExtra(EXTRA_CONTRASENA)) {
            val controlUser = res.getStringExtra(EXTRA_CONTROL)
            val contrasenaUser = res.getStringExtra(EXTRA_CONTRASENA)
            //Saved data in the Shared Preferences
            preferences.edit().putString(Preferencias.CONTROL, controlUser).apply()
            preferences.edit().putString(Preferencias.CONTRASENA, contrasenaUser).apply()
        } else if (cont == "No Disponible" || pass == "No Disponible") {
            val actLogin: Intent = Intent(this, MainLogin::class.java)
            startActivity(actLogin)
        }
    }
}
