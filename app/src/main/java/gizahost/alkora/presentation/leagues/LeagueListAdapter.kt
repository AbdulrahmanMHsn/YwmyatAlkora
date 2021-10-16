package gizahost.alkora.presentation.leagues

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gizahost.alkora.R
import gizahost.alkora.databinding.LayoutItemLeagueBinding
import gizahost.alkora.pojo.league.League


class LeagueListAdapter(private val clickListener:(League)->Unit) : RecyclerView.Adapter<LeagueListAdapter.LeagueHolder>() {

    var leagueList: List<League> = ArrayList()
    lateinit var bindingAdapter:  LayoutItemLeagueBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueHolder {

        bindingAdapter = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_item_league, parent, false)

        return LeagueHolder(
            bindingAdapter
        )
    }

    override fun getItemCount(): Int {
        return leagueList.size
    }

    override fun onBindViewHolder(holder: LeagueHolder, position: Int) {

        if (leagueList.isEmpty()) {
            return
        }

        holder.bind(leagueList[position],clickListener)

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<League>){
        leagueList = list
        notifyDataSetChanged()
    }

    class LeagueHolder(val binding:LayoutItemLeagueBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(league: League, clickListener:(League)->Unit){
            binding.itemTxtLeague.text = league.name
            binding.itemImgLeague.let {
                Glide.with(it).load(league.logo).into(it)
            }
            binding.layoutListHome.setOnClickListener{
                clickListener(league)
            }
        }
    }


}