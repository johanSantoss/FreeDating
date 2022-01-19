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
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.R
import cat.smartcoding.mendez.freedating.databinding.FragmentLoginBinding
import cat.smartcoding.mendez.freedating.databinding.UserFragmentBinding
import cat.smartcoding.mendez.freedating.ui.Login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class UserFragment : Fragment() {

    private lateinit var binding: UserFragmentBinding
    private lateinit var storageRef : StorageReference
    private val list = mutableListOf<CarouselItem>()
    private lateinit var carousel : ImageCarousel

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
        cargarCarousel()
        //  Storage variables
        val storage = FirebaseStorage.getInstance("gs://freedating-9dbd7.appspot.com/")
        storageRef = storage.reference
//        // abrir una imagen por defecto
//        val pathReference = storageRef.child( "imagenes/imageProfile.jpeg")
//        val im = pathReference.getBytes(50000)
//        // listener para setear el ImageView
//        im.addOnSuccessListener {
//            //llegim la imatge que estarà en "it"
//            var bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
//            binding.imgUserProfile.setImageBitmap( bitmap ) //posa el bitmap a la imatge
//        }
        // añadir imagen al viewModel

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

//    private fun upImageProfile(){
//        val auth: FirebaseAuth = Firebase.auth
//
//        val bitmaps = binding.imgUserProfile.drawable.toBitmap(binding.imgUserProfile.width, binding.imgUserProfile.height)
//        val outba = ByteArrayOutputStream()
//        bitmaps.compress(Bitmap.CompressFormat.JPEG, 50, outba)
//        val dadesbytes = outba.toByteArray() //passa les dades a ByteArray
//        val c: Calendar = Calendar.getInstance()
//        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
//        val strDate: String = sdf.format(c.getTime())
//        val pathReferenceSubir = storageRef.child( "${auth.currentUser?.uid}/imageProfile/"+ strDate)
//        pathReferenceSubir.putBytes( dadesbytes )
//        Toast.makeText(requireContext(),"Image Saved", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK){
//            binding.imgUserProfile.setImageURI(data?.data)
////            val imgBitMap : Bitmap?=data!!.getExtras()!!.get("data") as Bitmap?
////            binding.imgUserProfile.setImageBitmap(imgBitMap)
//            upImageProfile()
//        }else if(requestCode == CAMERA_CHOOSE && resultCode == Activity.RESULT_OK){
//            val imgBitMap : Bitmap?=data!!.getExtras()!!.get("data") as Bitmap?
//            binding.imgUserProfile.setImageBitmap(imgBitMap)
//            upImageProfile()
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun cargarCarousel(){
        carousel = binding.carousel1
        list.add(CarouselItem("https://as01.epimg.net/meristation/imagenes/2021/02/08/noticias/1612786479_151283_1612786596_portada_normal.jpg", "primera imagen"))
        list.add(CarouselItem("https://as01.epimg.net/meristation/imagenes/2021/05/18/noticias/1621331371_078391_1621331484_noticia_normal.jpg", "segunda imagen"))
        list.add(CarouselItem("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/one-piece-1636660305.png?crop=1.00xw:0.365xh;0,0.153xh&resize=640:*", "tercera imagen"))
        carousel.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                Toast.makeText(requireContext(),"${carouselItem.caption}", Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                TODO("Not yet implemented")
            }
        }

        carousel.addData(list)
    }

}