package cat.smartcoding.mendez.freedating.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.R
import cat.smartcoding.mendez.freedating.databinding.FragmentRegisterBinding
import cat.smartcoding.mendez.freedating.ui.Login.LoginDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Register : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var database: FirebaseDatabase

    companion object {
        fun newInstance() = Register()
        private const val TAG = "EmailPassword"
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )


        // Get the viewModel
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Conection to DataBase
        // cambiar el link del Storage por el correcto, este no es valido --------------------------------------------------------------------------------------------------------------------
        database = FirebaseDatabase.getInstance("https://freedating-9dbd7-default-rtdb.europe-west1.firebasedatabase.app/")

        binding.btnNewRegister.setOnClickListener {

            if (binding.editTextName.text == null ) {
                Toast.makeText(activity, "Falta nombre!", Toast.LENGTH_SHORT).show()
            } else if (binding.editTextEdat.text == null) {
                Toast.makeText(activity, "Falta la edat!", Toast.LENGTH_SHORT).show()
            } else if (binding.radioGroupRegistre.checkedRadioButtonId == null ){
                Toast.makeText(activity, "Falta sexo!", Toast.LENGTH_SHORT).show()
            } else if (binding.editTextCiutat.text == null ){
                Toast.makeText(activity, "Falta la ciudad!", Toast.LENGTH_SHORT).show()
            } else if (binding.editTextEmailRegister.text == null){
                Toast.makeText(activity, "Falta la cuidad!", Toast.LENGTH_SHORT).show()
            } else if (binding.editTextPassword.text == null ){
                Toast.makeText(activity, "Falta introducir la contraseña", Toast.LENGTH_SHORT).show()
            } else if (binding.editTextPassword2.text == null ){
                Toast.makeText(activity, "Falta repetir la contraseña", Toast.LENGTH_SHORT).show()
            } else {
                saveDatesUserViewModel()
                viewModel.setEstadoRegistro(1)
                createAccount(viewModel.email.value.toString(), viewModel.password.value.toString(), viewModel.password2.value.toString())
            }
        }

        binding.btnClear.setOnClickListener {
            clearDates()
        }

        if (viewModel.estadoRegistro.value == 1 ) restaurarDatos()

        return  binding.root
//        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        (activity as MainActivity).disableMenus()

        supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        (activity as MainActivity).enableMenus()
        saveDatesUserViewModel()
        supportActionBar?.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createAccount(email: String, password: String, password2: String) {

        if (password == password2){
            // [START create_user_with_email]
            (activity as MainActivity).getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
//                        val user = (activity as MainActivity).getAuth().currentUser
                        Toast.makeText(activity, "Register success!", Toast.LENGTH_SHORT).show()
                        // guardar los datos en la DDBB
                        saveDatesUserDataBase()
                        // go to login fragment
                        val action = RegisterDirections.actionRegisterToLogin()
                        NavHostFragment.findNavController(this).navigate(action)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(activity, "Register failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            // [END create_user_with_email]
        } else {
            Toast.makeText(activity, "Passwords not equals .", Toast.LENGTH_SHORT).show()
        }
    }
    private fun restaurarDatos(){
        if (viewModel.nom.value != "") binding.editTextName.setText(viewModel.nom.value)
        if (viewModel.edat.value != "") binding.editTextEdat.setText(viewModel.edat.value)
        if (viewModel.sexe.value != null) binding.radioGroupRegistre.check(viewModel.sexe.value!!)
        if (viewModel.ciutat.value != "") binding.editTextCiutat.setText(viewModel.ciutat.value)
        if(viewModel.email.value != "") binding.editTextEmailRegister.setText(viewModel.email.value)
        if (viewModel.password.value != "" ) binding.editTextPassword.setText(viewModel.password.value)
        if (viewModel.password2.value != "") binding.editTextPassword2.setText(viewModel.password2.value)
    }

    private fun clearDates(){
        binding.editTextName.text.clear()
        binding.editTextEdat.text.clear()
        binding.radioGroupRegistre.clearCheck()
        binding.editTextCiutat.text.clear()
        binding.editTextEmailRegister.text.clear()
        binding.editTextPassword.text.clear()
        binding.editTextPassword2.text.clear()
    }

    data class DatosUsuari(
        var nom: String,
        var edat: String,
        var sexe: String,
        var ciutat: String,
        var email: String,
    )
    private fun saveDatesUserViewModel(){
        if (binding.editTextName.text != null){
            viewModel.setNom(binding.editTextName.text.toString().trim())
        }
        if (binding.editTextEdat.text != null){
            viewModel.setEdatUser(binding.editTextEdat.text.toString().trim())
        }
        if (binding.radioGroupRegistre.checkedRadioButtonId != null){
            viewModel.setSexeUser(binding.radioGroupRegistre.checkedRadioButtonId)
        }
        if (binding.editTextCiutat.text != null){
            viewModel.setCiutatUser(binding.editTextCiutat.text.toString().trim())
        }
        if (binding.editTextEmailRegister.text != null){
            viewModel.setEmail(binding.editTextEmailRegister.text.toString().trim())
        }
        if (binding.editTextPassword.text != null){
            viewModel.setPassword(binding.editTextPassword.text.toString().trim())
        }
        if (binding.editTextPassword2.text != null){
            viewModel.setPassword2(binding.editTextPassword2.text.toString().trim())
        }
    }
    private fun saveDatesUserDataBase(){
        val auth = Firebase.auth
        var sexe : String
        // se setea el sexe
        if (viewModel.sexe.value == binding.radioBtn1.id) sexe = "Dona" else sexe = "Home"
        // se genera una clase de USER con todos los datos del usuario
        val user = DatosUsuari(viewModel.nom.value.toString(), viewModel.edat.value.toString(),  sexe, viewModel.ciutat.value.toString(), auth.currentUser?.email.toString())
        // Se genera el acceso a la DDBB
        val myRef = database.getReference("AllUsers/${auth.currentUser?.uid}/userDates")
        // Se settean y suben los datos del nuevo usuario
        myRef.setValue(user)
    }

}