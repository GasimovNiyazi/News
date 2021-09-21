package com.niyazi.news.ui.news_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.niyazi.news.databinding.ItemNewsBinding
import com.niyazi.news.model.Article
import com.niyazi.news.utils.MONTH_DAY_YEAR_PATTERN
import com.niyazi.news.utils.YEAR_MONTH_DAY_TIME_PATTERN
import com.niyazi.news.utils.format

class NewsViewHolder(
    private val binding: ItemNewsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article?) {
        article?.let {
            binding.apply {
                imageViewNews.load(article.urlToImage)
                textViewTitle.text = article.title
                textViewAuthorName.text = article.author
                textViewPublishedDate.text =
                    article.publishedAt?.format(YEAR_MONTH_DAY_TIME_PATTERN, MONTH_DAY_YEAR_PATTERN)
            }
        } ?: kotlin.run {
             binding.cardViewParent.isVisible = false
        }
    }

    companion object {
        fun from(parent: ViewGroup): NewsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemNewsBinding.inflate(inflater, parent, false)
            return NewsViewHolder(binding)
        }
    }

}