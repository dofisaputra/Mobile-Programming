package com.dofi.tb1.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.data.model.Post
import com.dofi.tb1.data.model.User
import com.dofi.tb1.data.repository.DummyApiRepository
import com.dofi.tb1.data.response.BaseResponse
import kotlinx.coroutines.delay

class DummyApiViewModel(
    private val repository: DummyApiRepository
) : ViewModel() {

    private val _users = MutableLiveData<Boolean>()
    private val _posts = MutableLiveData<Boolean>()
    private val _loginUser = MutableLiveData<Boolean>()
    private val _postByUser = MutableLiveData<Boolean>()
    private val _comments = MutableLiveData<Boolean>()
    private val _commentByPost = MutableLiveData<String>()

    private val _loadingState = MutableLiveData<MutableMap<FetchType, Boolean>>()
    val loadingState: LiveData<MutableMap<FetchType, Boolean>> = _loadingState

    val users: LiveData<BaseResponse<User>> = _users.switchMap {
        liveData {
            setLoadingState(FetchType.USERS, true)
            repository.getUser(10).collect {
                it.body()?.let { it1 ->
                    emit(it1)
                    setLoadingState(FetchType.USERS, false)
                }
            }
        }
    }

    val posts: LiveData<BaseResponse<Post>> = _posts.switchMap {
        liveData {
            setLoadingState(FetchType.POSTS, true)
            repository.getPost(5).collect {
                it.body()?.let { it1 ->
                    emit(it1)
                    setLoadingState(FetchType.POSTS, false)
                }
            }
        }
    }

    val loginUser: LiveData<User> = _loginUser.switchMap {
        liveData {
            setLoadingState(FetchType.LOGIN_USER, true)
            repository.getDetailUser("60d0fe4f5311236168a109d0").collect {
                it.body()?.let { it1 ->
                    emit(it1)
                    setLoadingState(FetchType.LOGIN_USER, false)
                }
            }
        }
    }

    val postByUser: LiveData<BaseResponse<Post>> = _postByUser.switchMap {
        liveData {
            setLoadingState(FetchType.POST_BY_USER, true)
            repository.getPostByUser("60d0fe4f5311236168a109d0", 5).collect {
                it.body()?.let { it1 ->
                    emit(it1)
                    setLoadingState(FetchType.POST_BY_USER, false)
                }
            }
        }
    }

    val comments: LiveData<BaseResponse<Comment>> = _comments.switchMap {
        liveData {
            setLoadingState(FetchType.COMMENTS, true)
            repository.getComment(10).collect {
                it.body()?.let { it1 ->
                    emit(it1)
                    setLoadingState(FetchType.COMMENTS, false)
                }
            }
        }
    }

    val commentByPost: LiveData<BaseResponse<Comment>> = _commentByPost.switchMap {
        liveData {
            setLoadingState(FetchType.COMMENT_BY_POST, true)
            repository.getCommentByPost(it, 10).collect {
                it.body()?.let { it1 ->
                    emit(it1)
                    setLoadingState(FetchType.COMMENT_BY_POST, false)
                }
            }
        }
    }

    fun fetchUsers() {
        _users.value = true
    }

    fun fetchPosts() {
        _posts.value = true
    }

    fun fetchLoginUser() {
        _loginUser.value = true
    }

    fun fetchPostByUser() {
        _postByUser.value = true
    }

    fun fetchComments() {
        _comments.value = true
    }

    fun fetchCommentByPost(id: String) {
        _commentByPost.value = id
    }

    private fun setLoadingState(fetchType: FetchType, isLoading: Boolean) {
        val map = _loadingState.value ?: mutableMapOf()
        map[fetchType] = isLoading
        _loadingState.value = map
    }

}

enum class FetchType {
    USERS,
    POSTS,
    LOGIN_USER,
    POST_BY_USER,
    COMMENTS,
    COMMENT_BY_POST,
    GET_USER,
    CREATE_USER
}