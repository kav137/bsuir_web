package by.bsuir.bsuirweb.extensions

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

fun <T> Observable<T>.applyOnStartAndFinish(onStart: () -> Unit, onFinish: () -> Unit): Observable<T> {
    return compose {
        it.doOnSubscribe { onStart.invoke() }
        it.doOnTerminate { onFinish.invoke() }
    }
}


