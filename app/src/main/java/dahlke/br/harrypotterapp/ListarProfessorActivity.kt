package dahlke.br.harrypotterapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager

class ListarProfessorActivity : AppCompatActivity() {

    private var recyclerViewStaff : RecyclerView? = null
    private lateinit var harryPotterAPI : HarryPotterAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_professor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerViewStaff = findViewById(R.id.recyclerViewStaff)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        harryPotterAPI = retrofit.create(HarryPotterAPI::class.java)

        getStaffs()
    }

    private fun getStaffs(){
        lifecycleScope.launch {
            try{
                val response = withContext(Dispatchers.IO){
                    harryPotterAPI.getStaff()
                }

                if (response.isEmpty()){
                    Toast.makeText(this@ListarProfessorActivity, R.string.errorMessageGetCharacterById, Toast.LENGTH_SHORT).show()
                } else{
                    recyclerViewStaff?.adapter = ImageTextArrayAdapter(response, this@ListarProfessorActivity)
                    recyclerViewStaff?.layoutManager = LinearLayoutManager(this@ListarProfessorActivity)
                    recyclerViewStaff?.setHasFixedSize(true)
                    recyclerViewStaff?.addItemDecoration(
                        DividerItemDecoration(this@ListarProfessorActivity, DividerItemDecoration.VERTICAL)
                    )
                }
            } catch (ex: Exception){
                Log.e(this@ListarProfessorActivity.toString(), "Error", ex)
            }
        }
    }
}