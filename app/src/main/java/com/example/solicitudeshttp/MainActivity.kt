package com.example.solicitudeshttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.CompletadoListener
import com.DescargaURL
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.OkHttpClient
import java.io.IOException
import java.lang.Exception




class MainActivity : AppCompatActivity(), CompletadoListener {

    override fun descargaCompleta(resultado: String) {
        Log.d("descargaCompleta", resultado)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bValidarRed = findViewById<Button>(R.id.bValidarRed)
        val bSolicitudHTTP = findViewById<Button>(R.id.bSolicitudHTTP)
        val bVolley = findViewById<Button>(R.id.bVolley)
        val bOK = findViewById<Button>(R.id.bOK)



        bValidarRed.setOnClickListener {
            //codigo para validar Red
            if (Network.hayRed(this)) {
                Toast.makeText(this, "Si hay Red!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No hay una conexion a Internet", Toast.LENGTH_LONG).show()
            }
        }

        bSolicitudHTTP.setOnClickListener {
            if (Network.hayRed(this)) {
                //Log.d("bSolicitudOnClick", descargarDatos("http://wwww.google.com"))

                DescargaURL(
                    this
                ).execute("http://wwww.google.com")
            } else {
                Toast.makeText(this, "No hay una conexion a Internet", Toast.LENGTH_LONG).show()
            }

        }

        bVolley.setOnClickListener {
            if (Network.hayRed(this)) {
                solicitudHTTPVolley("http://www.google.com")

            } else {
                Toast.makeText(this, "No hay una conexion a Internet", Toast.LENGTH_LONG).show()
            }


        }
        bOK.setOnClickListener {

            if (Network.hayRed(this)) {
                solicitudHTTPOkHTTP("http://wwww.google.com")


            } else {
                Toast.makeText(this, "No hay una conexion a Internet", Toast.LENGTH_LONG).show()
            }

        }

    }

    //Metodo para Volley
    private fun solicitudHTTPVolley(url: String) {

        val queue = Volley.newRequestQueue(this)

        val solicitud = StringRequest(
            Request.Method.GET, url, Response.Listener<String>{
                    response ->
                try {
                  Log.d("solicitudHTTPVolley", response)
                } catch (e: Exception) {

                }

            },
            Response.ErrorListener { })

        queue.add(solicitud)

    }

    //Metodo para OKHTTP
    private fun solicitudHTTPOkHTTP(url: String) {
        val cliente = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object: okhttp3.Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {

            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val resultado = response.body()?.string()

                this@MainActivity.runOnUiThread {
                    try {
                        Log.d("SolicitudHTTPOkHTTP", resultado)

                    } catch (e: Exception) {

                    }

                }
            }
        })
    }


}






