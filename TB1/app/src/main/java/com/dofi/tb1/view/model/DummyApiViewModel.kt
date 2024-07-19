package com.dofi.tb1.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.comment.Comment
import com.dofi.tb1.data.model.post.Post
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.data.repository.DummyApiRepository
import com.dofi.tb1.data.response.BaseResponse
import com.google.gson.Gson

class DummyApiViewModel(
    private val repository: DummyApiRepository
) : ViewModel() {

    private val _listOfUsers = MutableLiveData<List<Int>>()
    fun getListOfUsers(params: List<Int>) {
        _listOfUsers.postValue(params)
    }

    val listOfUsers : LiveData<NetworkResultState<BaseResponse<User>?>> = _listOfUsers.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.getListOfUsers(it[0], it[1]).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _getUserById = MutableLiveData<String>()
    fun getUserById(id: String) {
        _getUserById.postValue(id)
    }

    val getUserByIdResponse: LiveData<NetworkResultState<User?>> = _getUserById.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.getUserById(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _createUser = MutableLiveData<User>()
    fun createUser(user: User) {
        _createUser.postValue(user)
    }

    val createUserResponse: LiveData<NetworkResultState<User?>> = _createUser.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.createUser(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _getListOfPosts = MutableLiveData<List<Int>>()
    fun getListOfPosts(params: List<Int>) {
        _getListOfPosts.postValue(params)
    }

    val listOfPosts: LiveData<NetworkResultState<BaseResponse<Post>?>> = _getListOfPosts.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.getListOfPosts(it[0], it[1]).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _getPostById = MutableLiveData<String>()
    fun getPostById(id: String) {
        _getPostById.postValue(id)
    }

    val getPostByIdResponse: LiveData<NetworkResultState<Post?>> = _getPostById.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.getPostById(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _getPostByUser = MutableLiveData<Triple<String, Int, Int>>()
    fun getPostByUser(id: String, limit: Int, page: Int) {
        _getPostByUser.postValue(Triple(id, limit, page))
    }

    val getPostByUserResponse: LiveData<NetworkResultState<BaseResponse<Post>?>> = _getPostByUser.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.getPostByUser(it.first, it.second, it.third).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _createPost = MutableLiveData<Post>()
    fun createPost(post: Post) {
        _createPost.postValue(post)
    }

    val createPostResponse: LiveData<NetworkResultState<Post?>> = _createPost.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.createPost(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _getListOfComments = MutableLiveData<List<Int>>()
    fun getListOfComments(params: List<Int>) {
        _getListOfComments.postValue(params)
    }

    val getListOfCommentsResponse: LiveData<NetworkResultState<BaseResponse<Comment>?>> = _getListOfComments.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.getListOfComments(it[0], it[1]).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _getCommentByPost = MutableLiveData<Pair<String, List<Int>>>()
    fun getCommentByPost(postId: String, params: List<Int>) {
        _getCommentByPost.postValue(Pair(postId, params))
    }

    val getCommentByPostResponse: LiveData<NetworkResultState<BaseResponse<Comment>?>> = _getCommentByPost.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.getCommentByPost(it.first, it.second[0], it.second[1]).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _createComment = MutableLiveData<Comment>()
    fun createComment(comment: Comment) {
        _createComment.postValue(comment)
    }

    val createCommentResponse: LiveData<NetworkResultState<Comment?>> = _createComment.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            repository.createComment(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }


}
