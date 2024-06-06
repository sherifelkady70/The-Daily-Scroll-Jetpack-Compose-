package com.route.newsapplication.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.route.newsapplication.domain.models.Article

class NewsPagingSource (
    private val newsApi : NewsWebService,
    private val source : String
) : PagingSource<Int , Article>(){
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }
    private var totalNewsCount = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.getNews(page = page , sources = source)
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles ,
                nextKey = if(totalNewsCount == newsResponse.totalResults) null else page+1 ,
                prevKey = null
            )
        }catch (e : Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}