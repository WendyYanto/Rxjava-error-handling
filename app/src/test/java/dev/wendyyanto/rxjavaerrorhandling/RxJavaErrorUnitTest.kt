package dev.wendyyanto.rxjavaerrorhandling

import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import java.lang.Exception

class RxJavaErrorUnitTest {

  companion object {
    private const val USER = "USER"
    private const val DEFAULT_USER = "DEFAULT_USER"
  }

  @Test
  fun getUserTest() {
    getUser()
      .onErrorReturnItem(DEFAULT_USER)
      .subscribe(::println)
  }

  @Test
  fun undeliverableException() {
    Observable.create<String> { sink ->
      sink.onNext(USER)
      sink.onComplete()
      sink.onError(Exception())
    }.subscribe(::println)
  }

  private fun getUser(): Observable<String> {
    return Observable.fromCallable {
      USER
    }
  }
}