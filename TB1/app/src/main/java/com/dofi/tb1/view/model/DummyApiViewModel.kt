package com.dofi.tb1.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.comment.Comment
import com.dofi.tb1.data.model.comment.CommentCreate
import com.dofi.tb1.data.model.post.Post
import com.dofi.tb1.data.model.post.PostCreate
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.data.repository.DummyApiRepository
import com.dofi.tb1.data.repository.ImageApiRepository
import com.dofi.tb1.data.response.BaseResponse
import com.dofi.tb1.data.response.image.BaseResponseImageUpload
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DummyApiViewModel(
    private val dummyApiRepository: DummyApiRepository,
    private val imageApiRepository: ImageApiRepository
) : ViewModel() {

    private val _listOfUsers = MutableLiveData<List<Int>>()
    fun getListOfUsers(params: List<Int>) {
        _listOfUsers.postValue(params)
    }

    val listOfUsers : LiveData<NetworkResultState<BaseResponse<User>?>> = _listOfUsers.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            dummyApiRepository.getListOfUsers(it[0], it[1]).collect {
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
            dummyApiRepository.getUserById(it).collect {
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
            dummyApiRepository.createUser(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _updateUser = MutableLiveData<User>()
    fun updateUser(user: User) {
        _updateUser.postValue(user)
    }

    val updateUserResponse: LiveData<NetworkResultState<User?>> = _updateUser.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            dummyApiRepository.updateUser(it).collect {
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
            dummyApiRepository.getListOfPosts(it[0], it[1]).collect {
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
            dummyApiRepository.getPostById(it).collect {
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
            dummyApiRepository.getPostByUser(it.first, it.second, it.third).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _createPost = MutableLiveData<PostCreate>()
    fun createPost(post: PostCreate) {
        _createPost.postValue(post)
    }

    val createPostResponse: LiveData<NetworkResultState<Post?>> = _createPost.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            dummyApiRepository.createPost(it).collect {
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
            dummyApiRepository.getListOfComments(it[0], it[1]).collect {
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
            dummyApiRepository.getCommentByPost(it.first, it.second[0], it.second[1]).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _createComment = MutableLiveData<CommentCreate>()
    fun createComment(comment: CommentCreate) {
        _createComment.postValue(comment)
    }

    val createCommentResponse: LiveData<NetworkResultState<Comment?>> = _createComment.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            dummyApiRepository.createComment(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _uploadImage = MutableLiveData<MultipartBody.Part>()
    fun uploadImage(image: MultipartBody.Part) {
        _uploadImage.postValue(image)
    }

    val uploadImageResponse: LiveData<NetworkResultState<BaseResponseImageUpload?>> = _uploadImage.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            imageApiRepository.uploadImage(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }

    private val _uploadImageUser = MutableLiveData<MultipartBody.Part>()
    fun uploadImageUser(image: MultipartBody.Part) {
        _uploadImageUser.postValue(image)
    }

    val uploadImageUserResponse: LiveData<NetworkResultState<BaseResponseImageUpload?>> = _uploadImageUser.switchMap {
        liveData {
            emit(NetworkResultState.Loading)
            imageApiRepository.uploadImage(it).collect {
                if (it.isSuccessful) {
                    emit(NetworkResultState.Success(it.body()))
                } else {
                    println("it: $it")
                }
            }
        }
    }


}
