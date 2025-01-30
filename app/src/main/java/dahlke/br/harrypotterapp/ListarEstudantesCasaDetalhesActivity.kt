package dahlke.br.harrypotterapp

import StudentsHouseImageTextArrayAdapter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListarEstudantesCasaDetalhesActivity : AppCompatActivity() {

    private lateinit var harryPotterAPI: HarryPotterAPI
    private lateinit var recyclerViewStudents : RecyclerView
    private lateinit var tvTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_estudantes_casa_detalhes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerViewStudents = findViewById(R.id.recyclerViewStudents)
        tvTitle = findViewById(R.id.tvTitleStudentsByHouse)

        val house = intent.extras?.getString("HOUSE") ?: ""

        if (house.isEmpty()){
            Toast.makeText(this, getString(R.string.errorMessageGetStudentsByHouse),Toast.LENGTH_SHORT).show()
        }

        tvTitle.text = buildString {
            append(getString(R.string.tvTitleStudentsDetailByHouse))
            append(" ")
            append(house.uppercase())
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        harryPotterAPI = retrofit.create(HarryPotterAPI::class.java)

        getStudentsByHouse(house)
    }

    private fun getStudentsByHouse(house : String){
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    harryPotterAPI.getStudentsByHouse(house)
                }

                if (response.isEmpty()) {
                    Toast.makeText(
                        this@ListarEstudantesCasaDetalhesActivity,
                        R.string.errorMessageGetStudentsByHouse,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    recyclerViewStudents.adapter = StudentsHouseImageTextArrayAdapter(
                        response,
                        this@ListarEstudantesCasaDetalhesActivity
                    )
                    recyclerViewStudents.layoutManager =
                        LinearLayoutManager(this@ListarEstudantesCasaDetalhesActivity)
                    recyclerViewStudents.setHasFixedSize(true)
                    recyclerViewStudents.addItemDecoration(
                        DividerItemDecoration(
                            this@ListarEstudantesCasaDetalhesActivity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            } catch (ex: Exception) {
                Log.e(this@ListarEstudantesCasaDetalhesActivity.toString(), "Error", ex)
            }
        }
    }
}