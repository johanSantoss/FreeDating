package cat.smartcoding.mendez.freedating.ui.register

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.ui.Login.LoginViewModel

class RegisterViewModel : ViewModel() {


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

    // password2
    private val _password2 = MutableLiveData<String>("")
    val password2: LiveData<String> get() = _password2
    fun setPassword2 (pass : String){
        _password2.value = pass
    }




}