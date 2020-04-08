package com.kodorebi.kotlinstarter.core.searchable

interface Searchable<I, Q> {
    companion object{
        fun <I, Q> empty(): Searchable<I, Q> = object : Searchable<I, Q>{
            override fun searchResults(query: Q): List<SearchResult<I>> {
                return ArrayList()
            }
        }
    }

    fun searchResults(query: Q) : List<SearchResult<I>>
}