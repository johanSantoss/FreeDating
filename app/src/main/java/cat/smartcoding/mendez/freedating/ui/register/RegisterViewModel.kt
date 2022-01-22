package cat.smartcoding.mendez.freedating.ui.register

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.ui.Login.LoginViewModel

class RegisterViewModel : ViewModel() {

    // nom
    private val _nom = MutableLiveData<String>("")
    val nom: LiveData<String> get() = _nom
    fun setNom (userName : String){
        _nom.value = userName
    }

    // edat
    private val _edat = MutableLiveData<String>("")
    val edat: LiveData<String> get() = _edat
    fun setEdatUser (edatUser : String){
        _edat.value = edatUser
    }

    // sexe
    private val _sexe = MutableLiveData<Int?>(null)
    val sexe: LiveData<Int?> get() = _sexe
    fun setSexeUser (sexeUser : Int){
        _sexe.value = sexeUser
    }

    // ciutat
    private val _ciutat = MutableLiveData<String>("")
    val ciutat: LiveData<String> get() = _ciutat
    fun setCiutatUser (ciutatUser : String){
        _ciutat.value = ciutatUser
    }

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


    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoRegistro = MutableLiveData(0)
    val estadoRegistro: LiveData<Int> get() = _estadoRegistro
    fun setEstadoRegistro (estadoRegistro : Int){
        _estadoRegistro.value = estadoRegistro
    }



}