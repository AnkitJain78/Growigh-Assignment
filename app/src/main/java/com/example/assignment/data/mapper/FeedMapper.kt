package com.example.assignment.data.mapper

import com.example.assignment.data.remote.dto.FeedDto
import com.example.assignment.domain.model.Feed

fun FeedDto.toFeed(): Feed{
    return Feed(
        title,
        url,
        urlToImage,
        source
    )
}