package gizahost.alkora.presentation.social

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gizahost.alkora.R
import gizahost.alkora.databinding.LayoutItemHomeBinding
import gizahost.alkora.databinding.LayoutItemSocialBinding
import gizahost.alkora.pojo.HomeModel
import gizahost.alkora.pojo.SocialModel


class SocialListAdapter(private val clickListener:(SocialModel)->Unit) : RecyclerView.Adapter<SocialListAdapter.SocialHolder>() {

    var socialList: List<SocialModel> = ArrayList()
    lateinit var bindingAdapter:  LayoutItemSocialBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialHolder {

        bindingAdapter = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_item_social, parent, false)

        return SocialHolder(
            bindingAdapter
        )
    }

    override fun getItemCount(): Int {
        return socialList.size
    }

    override fun onBindViewHolder(holder: SocialHolder, position: Int) {

        if (socialList.isEmpty()) {
            return
        }

        holder.bind(socialList[position],clickListener)

    }


    fun setList(list: List<SocialModel>){
        socialList = list
        notifyDataSetChanged()
    }

    class SocialHolder(val binding:LayoutItemSocialBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(social: SocialModel,clickListener:(SocialModel)->Unit){
            binding.itemTxtSocial.text = social.body
            binding.layoutListHome.setOnClickListener{
                clickListener(social)
            }
        }
    }


}