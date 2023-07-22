package com.example.assignment.presentation.feeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.domain.model.Feed
import com.example.assignment.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {

    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed
    private val _isErrorOccur = MutableLiveData<Boolean>()
    val isErrorOccur: LiveData<Boolean> = _isErrorOccur

    fun getFeeds(page: Int, fetchFromRemote: Boolean) {
        _isErrorOccur.value = false
        viewModelScope.launch {
            val feedResult = async { repository.getFeeds(page, fetchFromRemote) }
            when (val result = feedResult.await()) {
                null -> _isErrorOccur.value = true

                else -> {
                    result.data.let {
                        _feed.value = it
                    }
                }
            }
        }
    }

    fun updateFeed(feed: Feed) {
        viewModelScope.launch {
            repository.updateFeed(feed)
        }
    }
}