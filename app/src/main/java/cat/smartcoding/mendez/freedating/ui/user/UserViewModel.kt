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
}