package com.myatech.caregiver.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.myatech.caregiver.databinding.FirstAidItemLayoutBinding
import com.myatech.caregiver.model.FirstAid
import java.util.*
import kotlin.collections.ArrayList

class FirstAidAdapter : RecyclerView.Adapter<FirstAidAdapterViewHolder>(), Filterable {
    var firstAids = mutableListOf<FirstAid>()
    var firstAidFilterList = ArrayList<FirstAid>()

   /* init {
        firstAidFilterList = firstAids as ArrayList<FirstAid>
    }
*/
    @SuppressLint("NotifyDataSetChanged")
    fun setFirstAidsList(firstAids: MutableList<FirstAid>) {
        this.firstAids = firstAids
        firstAidFilterList = firstAids as ArrayList<FirstAid>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstAidAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FirstAidItemLayoutBinding.inflate(inflater, parent, false)
        return FirstAidAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FirstAidAdapterViewHolder, position: Int) {
        val firstAid = firstAidFilterList[position]
        holder.binding.textViewPhotoPath.text = firstAid.photo
        holder.binding.textViewInstruction.text = firstAid.instruction
        holder.binding.textViewCaution.text = firstAid.caution
    }

    override fun getItemCount(): Int {
        return firstAidFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                firstAidFilterList = if (charSearch.isEmpty()) {
                    firstAids as ArrayList<FirstAid>
                } else {
                    val resultList = ArrayList<FirstAid>()
                    for (row in firstAids) {
                        if (row.instruction.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = firstAidFilterList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                firstAidFilterList = p1?.values as ArrayList<FirstAid>
                notifyDataSetChanged()
            }
        }
    }

}

class FirstAidAdapterViewHolder(val binding: FirstAidItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

}