package gizahost.alkora.presentation.leagues

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gizahost.alkora.R
import gizahost.alkora.databinding.LayoutItemDetailsLeagueBinding
import gizahost.alkora.databinding.LayoutItemLeagueBinding
import gizahost.alkora.pojo.details_league.Details
import gizahost.alkora.pojo.league.League


class TableLeagueListAdapter() : RecyclerView.Adapter<TableLeagueListAdapter.TableLeagueHolder>() {

    var detailsList: List<Details> = ArrayList()
    lateinit var bindingAdapter:  LayoutItemDetailsLeagueBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableLeagueHolder {

        bindingAdapter = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_item_details_league, parent, false)

        return TableLeagueHolder(
            bindingAdapter
        )
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    override fun onBindViewHolder(holder: TableLeagueHolder, position: Int) {

        if (detailsList.isEmpty()) {
            return
        }

        holder.bind(detailsList[position])

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Details>){
        detailsList = list
        notifyDataSetChanged()
    }

    class TableLeagueHolder(val binding:LayoutItemDetailsLeagueBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(table: Details){
            binding.txtPoints.text = table.intPoints
            binding.txtGoals.text = "${table.intGoalsFor}/${table.intGoalsAgainst}"
            binding.txtLose.text = table.intLoss
            binding.txtDraw.text = table.intDraw
            binding.txtWin.text = table.intWin
            binding.txtPlay.text = table.intPlayed
            binding.teamName.text = table.strTeam
            binding.teamNum.text = table.intRank
            binding.teamLogo.let {
                Glide.with(it).load(table.strTeamBadge).into(it)
            }

        }
    }




}