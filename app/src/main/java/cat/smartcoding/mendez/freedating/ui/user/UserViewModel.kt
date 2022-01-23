package cat.smartcoding.mendez.freedating.ui.user

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _imgProfile = MutableLiveData<Bitmap>()
    val imgProfile: LiveData<Bitmap> get() = _imgProfile
    fun setEmail (imgProfile : Bitmap){
        _imgProfile.value = imgProfile
    }

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
    private val _sexe = MutableLiveData<String>("")
    val sexe: LiveData<String> get() = _sexe
    fun setSexeUser (sexeUser : String){
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

    // contador datos descargados
    // 1 - datos descargados
    private val _estado = MutableLiveData(0)
    val estado: LiveData<Int> get() = _estado
    fun setEstado (estado : Int){
        _estado.value = estado
    }



}