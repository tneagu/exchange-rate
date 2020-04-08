package com.kodorebi.kotlinstarter.core.searchable

fun <I, Q> Searchable<I, Q>.search(query: Q) : List<I> {
    val searchResults = this.searchResults(query)

    return searchResults.sortedByDescending { r -> r.relevance }
        .map { r -> r.item }
}