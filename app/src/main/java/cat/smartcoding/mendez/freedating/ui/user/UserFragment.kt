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
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener
import java.io.ByteArrayOutputStream
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2

class UserFragment : Fragment() {

    private lateinit var binding: UserFragmentBinding
    private lateinit var storageRef : StorageReference
    private val list = mutableListOf<CarouselItem>()
    private lateinit var carousel : ImageCarousel
    private lateinit var database: FirebaseDatabase
    private lateinit var bitmaps: Bitmap
    val auth: FirebaseAuth = Firebase.auth
    val pathServer = "gs://freedating-9dbd7.appspot.com"
    private lateinit var varGlobal: String

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
        //cargarCarousel()

        //  Storage variables
        val storage = FirebaseStorage.getInstance(pathServer)
        storageRef = storage.reference

        // Conection to DataBase
        // cambiar el link del Storage por el correcto, este no es valido --------------------------------------------------------------------------------------------------------------------
        database = FirebaseDatabase.getInstance("https://freedating-9dbd7-default-rtdb.europe-west1.firebasedatabase.app/")

        // cargar los datos del usuario

        if (viewModel.estado.value == 1 ) {
            restaurarDatos()
        } else {
            cargarDatosUsuario()
        }

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
//            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePhotoIntent, CAMERA_CHOOSE)
            listaImgUser()
            cargarCarousel()
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

    private fun upImageProfile(){

        val outba = ByteArrayOutputStream()
        bitmaps.compress(Bitmap.CompressFormat.JPEG, 50, outba)
        val dadesbytes = outba.toByteArray() //passa les dades a ByteArray
        val c: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val strDate: String = sdf.format(c.getTime())
        val pathReferenceSubir = storageRef.child( "${auth.currentUser?.uid}/imageProfile/"+ strDate)
        pathReferenceSubir.putBytes( dadesbytes )
        val myRef = database.getReference("${auth.currentUser?.uid}/userDates/imgProfile")
        myRef.setValue(strDate)
        Toast.makeText(requireContext(),"Image Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK){
            val uri = data?.data
            bitmaps = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            upImageProfile()
        }else if(requestCode == CAMERA_CHOOSE && resultCode == Activity.RESULT_OK){
            bitmaps = data!!.extras!!.get("data") as Bitmap
            upImageProfile()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun listaImgUser(){
        //val storage = FirebaseStorage.getInstance("gs://freedating-9dbd7.appspot.com/")
        val path = "${auth.currentUser?.uid}/imageProfile"
        Log.d("Path comosea", path)
        val listRef = storageRef.child(path)

// You'll need to import com.google.firebase.storage.ktx.component1 and
// com.google.firebase.storage.ktx.component2
        listRef.listAll()
            .addOnSuccessListener { (items, prefixes) ->
                prefixes.forEach { prefix ->
                    // All the prefixes under listRef.
                    // You may call listAll() recursively on them.
                    Log.d("Lista path:", prefix.path)
                }

                items.forEach { item ->
                    // All the items under listRef.
                    item.downloadUrl.addOnSuccessListener {
                        val ruta = it.toString()
                        list.add(CarouselItem(ruta))
                    }
                }
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
                Log.d("Error","${it.message}")
            }

    }

    private fun cargarCarousel(){
        carousel = binding.carousel1
         carousel.addData(list)
    }

    private fun restaurarDatos(){
        if (viewModel.nom.value != "") binding.editTextNomUser.setText(viewModel.nom.value)
        if (viewModel.edat.value != "") binding.editTextEdatUser.setText(viewModel.edat.value)
        if (viewModel.sexe.value != "") binding.editTextSexeUser.setText(viewModel.sexe.value)
        if (viewModel.ciutat.value != "") binding.editTextCiutatUser.setText(viewModel.ciutat.value)
        if(viewModel.email.value != "") binding.editTextMailUser.setText(viewModel.email.value)
    }

    class DatosUsuari {
        val nom: String = ""
        val edat: String = ""
        val sexe: String = ""
        val ciutat: String = ""
        val email: String = ""
//        constructor() {
//            this.nom = ""
//            this.edat = ""
//            this.sexe = ""
//            this.ciutat = ""
//            this.email = ""
//        }
    }

    private fun cargarDatosUsuario(){
        val auth = (activity as MainActivity).getAuth()

        val myRef = database.getReference("${auth.currentUser?.uid}/userDates")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<DatosUsuari>()
                binding.editTextNomUser.setText(value?.nom?:"")
                binding.editTextEdatUser.setText(value?.edat?:"")
                binding.editTextSexeUser.setText(value?.sexe?:"")
                binding.editTextCiutatUser.setText(value?.ciutat?:"")
                binding.editTextMailUser.setText(value?.email?:"")
                saveDatesUserViewModel()
            }
            override fun onCancelled(error: DatabaseError) {
                binding.editTextNomUser.setText("Error al cargar los datos")
                binding.editTextEdatUser.setText("Error al cargar los datos")
                binding.editTextSexeUser.setText("Error al cargar los datos")
                binding.editTextCiutatUser.setText("Error al cargar los datos")
                binding.editTextMailUser.setText("Error al cargar los datos")
            }
        })
    }

    private fun saveDatesUserViewModel(){
        viewModel.setNom(binding.editTextNomUser.text.toString().trim())
        viewModel.setEdatUser(binding.editTextEdatUser.text.toString().trim())
        viewModel.setSexeUser(binding.editTextSexeUser.text.toString().trim())
        viewModel.setCiutatUser(binding.editTextCiutatUser.text.toString().trim())
        viewModel.setEmail(binding.editTextMailUser.text.toString().trim())
        viewModel.setEstado(1)
    }
}