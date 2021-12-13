package cat.smartcoding.mendez.freedating.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.R
import cat.smartcoding.mendez.freedating.databinding.FragmentGalleryBinding
import cat.smartcoding.mendez.freedating.ui.Login.LoginDirections


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if ( (activity as MainActivity).getAuth().currentUser == null ){
            val action = GalleryFragmentDirections.actionNavGalleryToLogin()
            NavHostFragment.findNavController(this).navigate(action)
        }

        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

       val email: String = GalleryFragmentArgs.fromBundle(requireArguments()).email

        galleryViewModel.setEmail(email)

        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val safeArgs: LoginArgs by navArgs()
//        val nom = safeArgs.nom
//        Toast.makeText(requireContext(), "Hola  $nom", Toast.LENGTH_SHORT).show()
//        binding.textGallery.text = nom
//
////        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}