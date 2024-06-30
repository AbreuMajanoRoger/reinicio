package com.example.practicaa
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaa.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class MainActivity : AppCompatActivity(),OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : DogsAdapter
    private val dogImg = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchDog.setOnQueryTextListener(this)
        initRecyclerView()


    }

    private fun initRecyclerView() {
        adapter = DogsAdapter(dogImg)
            binding.rvDogs.layoutManager = LinearLayoutManager(this)

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    private fun searchByName(query: String){
        CoroutineScope(Dispatchers.IO).launch {



            val call = getRetrofit().create(ApiService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    Log.i("CALL", "ENTRA EN IF")
                    val  images:List<String> = puppies?.images ?: emptyList()
                    dogImg.clear()
                    dogImg.addAll(images)
                    adapter.notifyDataSetChanged()

                } else {
                    Log.i("CALL", "ENTRA EN ERROR")


                }


            }


        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}