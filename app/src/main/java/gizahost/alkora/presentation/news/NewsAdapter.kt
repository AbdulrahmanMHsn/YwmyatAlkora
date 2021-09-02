package gizahost.alkora.presentation.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gizahost.alkora.R
import gizahost.alkora.databinding.LayoutItemNewsBinding
import gizahost.alkora.pojo.Articles
import gizahost.alkora.utils.DateUtils
import kotlin.collections.ArrayList


class NewsAdapter(private val clickListener: (Articles) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    var newsList: List<Articles> = ArrayList()
    lateinit var bindingAdapter: LayoutItemNewsBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {

        bindingAdapter = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_news, parent, false
        )

        return NewsHolder(
            bindingAdapter
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        if (newsList.isEmpty()) {
            return
        }

        holder.bind(newsList[position], clickListener)

    }


    fun setList(list: List<Articles>) {
        newsList = list
        notifyDataSetChanged()
    }

    class NewsHolder(val binding: LayoutItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articles: Articles, clickListener: (Articles) -> Unit) {
            binding.apply {

                imageView2.let {
                    Glide.with(it)
                        .load(articles.urlToImage)
                        .into(it)
                }

                newsTxtPublishedAt.text = DateUtils.DateFormat(articles.publishedAt)

//                newsTxtAuthor.text = articles.author

                newsTxtTitle.text = articles.title

                newsTxtDesc.text = articles.description

                newsTxtSource.text = articles.source.name

//                newsTxtTime.text = "\u2022 ${NewsUtils.DateToTimeFormat(articles.publishedAt)}"

                layoutListNews.setOnClickListener {
                    clickListener(articles)
                }
            }
        }
    }


}