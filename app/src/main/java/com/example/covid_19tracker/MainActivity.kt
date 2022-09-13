package com.example.covid_19tracker

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
   // lateinit var worldCases : TextView
    // lateinit var worldRecovered: TextView
    //lateinit var worldDeaths : TextView

    lateinit var countryCases : TextView
    lateinit var countryRecovered: TextView
    lateinit var countryDeaths : TextView

    lateinit var stateRV : RecyclerView
    lateinit var stateRvAdapter: state_rv_adapter
    private lateinit var stateList: List<StateModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  worldCases = findViewById(R.id.world_cases)
       // worldRecovered = findViewById(R.id.world_recovery)
      //  worldDeaths = findViewById(R.id.world_death)
        countryCases = findViewById(R.id.India_cases)
        countryRecovered = findViewById(R.id.India_recovery)
        countryDeaths = findViewById(R.id.India_death)
        stateRV = findViewById(R.id.recycler_view)
        stateList = ArrayList<StateModel>()
getstateInfo()
       // getWorldInfo()
    }
    private fun getstateInfo()
    {
      val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request =
            JsonObjectRequest(
                Request.Method.GET,url,null,
                { response ->
                    try{
                       val dataObj = response.getJSONObject("data")
                       val summaryObj = dataObj.getJSONObject("summary")
                       val cases : Int = summaryObj.getInt("total")
                        val recovered : Int = summaryObj.getInt("discharged")
                        val deaths : Int = summaryObj.getInt("deaths")

                        countryCases.text = cases.toString()
                        countryRecovered.text = recovered.toString()
                        countryDeaths.text= deaths.toString()

                        val regionalArray = dataObj.getJSONArray("regional")
                        for(i in 0 until regionalArray.length())
                        {
                            val regionalObj = regionalArray.getJSONObject(i)
                            val stateName : String = regionalObj.getString("loc")
                            val cases1 : Int = regionalObj.getInt("totalConfirmed")
                            val recovered1 : Int =regionalObj.getInt("discharged")
                            val deaths1 : Int = regionalObj.getInt("deaths")

                            val stateModel = StateModel(stateName,recovered1,deaths1,cases1)
                            stateList = stateList + stateModel
                        }
                        stateRvAdapter= state_rv_adapter(stateList)
                        stateRV.layoutManager = LinearLayoutManager(this)
                        stateRV.adapter = stateRvAdapter

                    } catch (e:JSONException)
                    {
                        e.printStackTrace()
                    }
                },{
                    error->
                    Toast.makeText(this,"Fail to get data",Toast.LENGTH_SHORT).show()
                })
        queue.add(request)
    }
    /*private fun getWorldInfo()
    {
        val url = " https://coronavirus-tracker-api.herokuapp.com/v2/locations"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request =
            JsonObjectRequest(
                Request.Method.GET,url,null,
                { response ->
                    try {
                        val dataObj = response.getJSONObject("latest")
                        val cases: Int = dataObj.getInt("confirmed")
                        val recovered: Int = dataObj.getInt("recovered")
                        val death: Int = dataObj.getInt("deaths")
                        worldCases.text = cases.toString()
                        worldRecovered.text = recovered.toString()
                        worldDeaths.text = death.toString()
                    }catch (e:JSONException)
                    {
                        e.printStackTrace()
                    }
                }, {
                    error->
                    Toast.makeText(this,"Fail to get data",Toast.LENGTH_SHORT).show()
                })
        queue.add(request)
    }*/
}