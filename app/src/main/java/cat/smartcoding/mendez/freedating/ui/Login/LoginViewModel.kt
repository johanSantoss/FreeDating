package cat.smartcoding.mendez.freedating.ui.Login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBindings
import cat.smartcoding.mendez.freedating.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    // authentication
    private val _auth  = MutableLiveData<FirebaseAuth>(Firebase.auth)
    val auth: LiveData<FirebaseAuth> get() = _auth

    // mail
    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> get() = _email
    fun setEmail (email : String){
        _email.value = email
    }



    // password
    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> get() = _password
    fun setPassword (pass : String){
        _password.value = pass
    }







}