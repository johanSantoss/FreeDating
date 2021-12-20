package cat.smartcoding.mendez.freedating.ui.user

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cat.smartcoding.mendez.freedating.R
import cat.smartcoding.mendez.freedating.databinding.FragmentLoginBinding
import cat.smartcoding.mendez.freedating.databinding.UserFragmentBinding
import cat.smartcoding.mendez.freedating.ui.Login.LoginViewModel

class UserFragment : Fragment() {

    private lateinit var binding: UserFragmentBinding

    companion object {
        fun newInstance() = UserFragment()
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
        private  val CAMERA_CHOOSE = 1002;


    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


            binding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.user_fragment,
                    container,
                    false
            )


            viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

            binding.btnCamera.setOnClickListener {
                val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePhotoIntent, CAMERA_CHOOSE)
            }

            binding.btnGallery.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else{
                        chooseImageGallery();
                    }
                }else{
                    chooseImageGallery();
                }
            }
        return binding.root
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(requireContext(),"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK){
            binding.imgUserProfile.setImageURI(data?.data)
        }else if(requestCode == CAMERA_CHOOSE && resultCode == Activity.RESULT_OK){
            val imgBitMap : Bitmap?=data!!.getExtras()!!.get("data") as Bitmap?
            binding.imgUserProfile.setImageBitmap(imgBitMap)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}