package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var datos: TextView
    private lateinit var crear: Button
    private lateinit var modificar: Button
    private lateinit var eliminar:Button
    private lateinit var ver:Button

    private lateinit var referencia: DatabaseReference
    private lateinit var nueva_persona: Persona
    private lateinit var identificador:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        datos=findViewById(R.id.datos) as TextView
        crear=findViewById(R.id.crear) as Button
        modificar=findViewById(R.id.modificar) as Button
        eliminar=findViewById(R.id.eliminar) as Button
        referencia=FirebaseDatabase.getInstance().getReference()
        ver=findViewById(R.id.ver)

        crear.setOnClickListener(View.OnClickListener { view:View? -> datos.setText("Datos creados")
            identificador=referencia.child("Personas").push().key!!
            nueva_persona=Persona("Juana","Jordan","Fernandez","jose@gmail.com","123456",20)
            referencia.child("personas").child(identificador).setValue(nueva_persona)})


        eliminar.setOnClickListener(View.OnClickListener { view:View? -> datos.setText("Datos eliminados")
            referencia.child("personas").child(identificador).removeValue()})


        modificar.setOnClickListener(View.OnClickListener { view:View? -> datos.setText("Datos modificados")
            var otra_persona: Persona=
                Persona("Raúl","Pablo","Jorge","raulypabloforever@gmail.com","12345678",10)
            referencia.child("personas").child(identificador).setValue(otra_persona)
        })

        ver.setOnClickListener(View.OnClickListener { view: View? ->
            var res = ""
            referencia.child("personas")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { hijo: DataSnapshot? ->
                            val pojo_persona = hijo?.getValue(Persona::class.java)
                            res += pojo_persona?.nombre + "\n" + pojo_persona?.telefono + "\n"
                        }
                        datos.setText(res)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println(error.message)
                    }


                })
        })


    }







}