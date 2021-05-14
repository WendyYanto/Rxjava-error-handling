package dev.wendyyanto.rxjavaerrorhandling

import io.reactivex.rxjava3.core.Observable
import org.junit.Test

class RxJavaUnitTest {

    companion object {
        private const val USER_ID = "USER_ID"
        private const val NAME = "John Doe"
        private const val AGE = 20
        private const val EMPTY = ""
    }

    @Test
    fun getUser() {
        getUserId()
          .flatMap(this::getUser)
          .flatMap(this::saveUser)
          .subscribe(::println)
    }

    @Test
    fun getUserWithBadErrorHandling() {
        getUserId()
            .flatMap(this::getUser)
            .flatMap(this::saveUser)
            .subscribe { user ->
                if (user.hasError) {
                    println("user ID is invalid")
                } else {
                    println(user)
                }
            }
    }

    @Test
    fun getUserWithGoodErrorHandling() {
        getUserId()
            .doOnNext{ println("get user") }
            .flatMap(this::getUser)
            .doOnNext{ println("done get user") }
            .doOnNext{ println("saving user") }
            .flatMap(this::saveUser)
            .doOnNext{ println("done saving user") }
            .subscribe{ user ->
              println(user)
            }
    }

    private fun getUserId(): Observable<String> {
        return Observable.fromCallable {
            USER_ID
        }
    }

    private fun getUser(id: String): Observable<User> {
        if (id == EMPTY) {
            throw Exception("UserID is invalid")
        }
        val user = User(
            id = id,
            name = NAME,
            age =  AGE
        )
        return Observable.fromCallable { user }
    }

    private fun saveUser(user: User): Observable<User> {
        // Perform save to user
        return Observable.fromCallable { user }
    }
}

data class User(
    val id: String,
    val name: String,
    val age: Int,
    var hasError: Boolean = false
)