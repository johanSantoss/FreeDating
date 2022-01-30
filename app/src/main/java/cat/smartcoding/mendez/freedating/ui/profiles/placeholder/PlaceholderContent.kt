package cat.smartcoding.mendez.freedating.ui.profiles.placeholder

import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.ui.profiles.ProfilesRecyclerViewAdapter
import cat.smartcoding.mendez.freedating.ui.user.UserFragment
import cat.smartcoding.mendez.freedating.ui.user.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    private var COUNT = -1

    var database = FirebaseDatabase.getInstance("https://freedating-9dbd7-default-rtdb.europe-west1.firebasedatabase.app/")

    var listUsers = arrayListOf<DatosProfile>()
    var error = ""
    var edatMin = 0
    var edatMax = 0
    lateinit var rangoEdad : IntRange
    var sexe = ""
    var ciutat = ""
    var distancia = 0




    init {
        // Add some sample items.
        cargarDatosUsuario()
    }
    class DatosProfile {
        val nom: String = ""
        val edat: String = ""
        val sexe: String = ""
        val ciutat: String = ""
        val email: String = ""
        val imgProfile = ""
    }

    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.edat, item)
    }

    private fun cargarDatosUsuario(){
        val auth: FirebaseAuth = Firebase.auth
        val myRef = database.getReference("AllUsers")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                snapshot.children.forEach { item ->
                    if(item.key != auth.currentUser?.uid){
                        item.children.forEach { valors ->
                            valors.getValue<DatosProfile>()?.let {
                                rangoEdad = edatMin .. edatMax
                                if (sexe == it.sexe || sexe == "Tots dos"){
                                    if(ciutat == it.ciutat){
                                        if(rangoEdad.contains(Integer.parseInt(it.edat))){
                                            listUsers.add(it)
                                            ++COUNT
                                            if(COUNT >= 0){
                                                addItem(createPlaceholderItem(COUNT))
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }
                }

//                Log.d("VALUE2:" , listUsers[2].nom)

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun createPlaceholderItem(position: Int): PlaceholderItem {
       Log.d("Datos Usuario2: ", "listUsers[position].nom")
//       Log.d("Datos Usuario: ", edad)
        return PlaceholderItem(listUsers[position].edat, listUsers[position].nom, listUsers[position].imgProfile)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val edat: String, val content: String, val img: String) {
        override fun toString(): String = content
    }
}