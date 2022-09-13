package com.example.covid_19tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class state_rv_adapter(private val stateList : List<StateModel>) :
    RecyclerView.Adapter<StateRvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRvViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateRvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StateRvViewHolder, position: Int) {
        val stateData = stateList[position]
        holder.stateCases.text = stateData.cases.toString()
        holder.stateName.text=stateData.state
        holder.stateDeath.text = stateData.deaths.toString()
        holder.stateRecovered.text= stateData.recovered.toString()
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}
class StateRvViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val stateName : TextView =itemView.findViewById(R.id.state_name)
    val stateCases : TextView = itemView.findViewById(R.id.state_cases)
    val  stateDeath : TextView = itemView.findViewById(R.id.state_death)
    val stateRecovered : TextView = itemView.findViewById(R.id.state_recovery)

}


