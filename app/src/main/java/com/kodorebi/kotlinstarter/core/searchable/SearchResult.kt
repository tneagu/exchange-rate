package com.kodorebi.kotlinstarter.core.searchable

interface SearchResult<T> {
    val relevance: Int
    val item : T
}