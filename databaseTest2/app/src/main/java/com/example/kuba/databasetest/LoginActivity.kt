package com.example.kuba.databasetest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast



class LoginActivity : AppCompatActivity() {

    lateinit var RegistLink: TextView
    lateinit var LoginButton: Button
    lateinit var LoginLog: EditText
    lateinit var PasswordLog: EditText
    lateinit var ViewAllUser: Button
    lateinit var myDb: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        RegistLink=findViewById(R.id.registLink)
        LoginButton=findViewById(R.id.loginButton)
        LoginLog=findViewById(R.id.loginLog)
        PasswordLog=findViewById(R.id.passwordLog)
        ViewAllUser=findViewById(R.id.viewAllUser)
        myDb = DatabaseHelper(this)

        goRegist()
        logIn()
        viewAllData()
    }

    fun goRegist(){
        RegistLink.setOnClickListener(View.OnClickListener{

            val Intent = Intent(this,RegistFinal::class.java)
            startActivity(Intent);
            return@OnClickListener
        })
    }

    fun logIn (){
        LoginButton.setOnClickListener(View.OnClickListener {
            var userExist: Boolean =false
            val res = myDb.getAllData()

            if(res.getCount()==0){

                return@OnClickListener
            }else{
                while (res.moveToNext()){
                    if(LoginLog.text.toString().trim()==res.getString(1)&& PasswordLog.text.toString().trim()==res.getString(2)){
                        val Intent = Intent(this,MainActivity::class.java)
                        startActivity(Intent);
                        userExist =true
                    }else{

                    }
                }
                if(!userExist){
                    Toast.makeText(applicationContext,"Wrong login or password", Toast.LENGTH_SHORT).show()
                }


            }
        })
    }

    fun viewAllData(){

        ViewAllUser.setOnClickListener(View.OnClickListener{
            val res = myDb.getAllData()

            if(res.getCount()==0){

                return@OnClickListener
            }else{
                val buffer = StringBuffer()
                while (res.moveToNext()){
                    buffer.append("id:"+res.getString(0)+"\n")
                    buffer.append("login:"+res.getString(1)+"\n")
                    buffer.append("paasword:"+res.getString(2)+"\n")
                    buffer.append("sex:"+res.getString(3)+"\n\n")
                }

                showMessage("Data", buffer.toString())
                res.close()
            }
        })
    }

    private fun showMessage(title: String, message: String?) {

        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

}
