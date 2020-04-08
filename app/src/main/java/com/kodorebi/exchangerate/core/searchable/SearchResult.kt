package com.kodorebi.exchangerate.core.searchable

interface SearchResult<T> {
    val relevance: Int
    val item : T
}