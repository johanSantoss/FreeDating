package cat.smartcoding.mendez.freedating.ui.Login

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBindings
import cat.smartcoding.mendez.freedating.R
//import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    //private lateinit var auth: FirebaseAuth


//    var etEmail: String = ""
//    @SuppressLint("StaticFieldLeak")
//    private lateinit var etPassword: EditText
//    @SuppressLint("StaticFieldLeak")
//    private lateinit var btnLogin: Button


    private val _email = MutableLiveData<String>("")
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>("")
    val password: LiveData<String>
        get() = _password






}