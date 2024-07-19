package com.dofi.tb1.data.repository

import com.dofi.tb1.data.model.comment.Comment
import com.dofi.tb1.data.model.post.Post
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.data.response.BaseResponse
import com.dofi.tb1.data.service.DummyApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class DummyApiRepository(
    private val dummyApiService: DummyApiService
) {

    suspend fun getListOfUsers(limit: Int, page: Int): Flow<Response<BaseResponse<User>>> {
        return flow {
            val response = dummyApiService.getListOfUser(limit, page)
            emit(response)
        }
    }

    suspend fun getUserById(id: String): Flow<Response<User>> {
        return flow {
            val response = dummyApiService.getUserById(id)
            emit(response)
        }
    }

    suspend fun createUser(body: User): Flow<Response<User>> {
        return flow {
            val response = dummyApiService.createUser(body)
            emit(response)
        }
    }

    suspend fun updateUser(id: String, body: User): Flow<Response<User>> {
        return flow {
            val response = dummyApiService.updateUser(id, body)
            emit(response)
        }
    }

    suspend fun deleteUser(id: String): Flow<Response<String>> {
        return flow {
            val response = dummyApiService.deleteUser(id)
            emit(response)
        }
    }

    suspend fun getListOfPosts(limit: Int, page: Int): Flow<Response<BaseResponse<Post>>> {
        return flow {
            val response = dummyApiService.getListOfPost(limit, page)
            emit(response)
        }
    }

    suspend fun getPostById(id: String): Flow<Response<Post>> {
        return flow {
            val response = dummyApiService.getPostById(id)
            emit(response)
        }
    }

    suspend fun getPostByUser(
        id: String,
        limit: Int,
        page: Int
    ): Flow<Response<BaseResponse<Post>>> {
        return flow {
            val response = dummyApiService.getPostByUser(id, limit, page)
            emit(response)
        }
    }

    suspend fun getPostByTag(
        tag: String,
        limit: Int,
        page: Int
    ): Flow<Response<BaseResponse<Post>>> {
        return flow {
            val response = dummyApiService.getPostByTag(tag, limit, page)
            emit(response)
        }
    }

    suspend fun createPost(body: Post): Flow<Response<Post>> {
        return flow {
            val response = dummyApiService.createPost(body)
            emit(response)
        }
    }

    suspend fun updatePost(id: String, body: Post): Flow<Response<Post>> {
        return flow {
            val response = dummyApiService.updatePost(id, body)
            emit(response)
        }
    }

    suspend fun deletePost(id: String): Flow<Response<String>> {
        return flow {
            val response = dummyApiService.deletePost(id)
            emit(response)
        }
    }

    suspend fun getListOfComments(
        limit: Int,
        page: Int
    ): Flow<Response<BaseResponse<Comment>>> {
        return flow {
            val response = dummyApiService.getListOfComments(limit, page)
            emit(response)
        }
    }

    suspend fun getCommentByPost(
        postId: String,
        limit: Int,
        page: Int
    ): Flow<Response<BaseResponse<Comment>>> {
        return flow {
            val response = dummyApiService.getCommentByPost(postId, limit, page)
            emit(response)
        }
    }

    suspend fun getCommentByUser(
        userId: String,
        limit: Int,
        page: Int
    ): Flow<Response<BaseResponse<Comment>>> {
        return flow {
            val response = dummyApiService.getCommentByUser(userId, limit, page)
            emit(response)
        }
    }

    suspend fun createComment(body: Comment): Flow<Response<Comment>> {
        return flow {
            val response = dummyApiService.createComment(body)
            emit(response)
        }
    }

    suspend fun deleteComment(id: String): Flow<Response<String>> {
        return flow {
            val response = dummyApiService.deleteComment(id)
            emit(response)
        }
    }

    suspend fun getListOfTags(): Flow<Response<BaseResponse<String>>> {
        return flow {
            val response = dummyApiService.getListOfTags()
            emit(response)
        }
    }


}