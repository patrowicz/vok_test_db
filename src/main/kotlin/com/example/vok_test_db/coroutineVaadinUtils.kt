/**
 * na podstawie https://github.com/mvysny/vaadin-coroutines-demo/tree/master/src/main/kotlin/org/test
 */
package com.example.vok_test_db

import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.setPrimary
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.server.ErrorEvent
import com.vaadin.flow.server.ErrorHandler
import com.vaadin.flow.server.VaadinSession
import com.vaadin.flow.shared.Registration
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


fun checkUIThread() {
    require(UI.getCurrent() != null) { "Not running in Vaadin UI thread" }
}

/**
 * Implements [CoroutineDispatcher] on top of Vaadin [UI] and makes sure that all coroutine code runs in the UI thread.
 * Actions done in the UI thread are then automatically pushed by Vaadin Push to the browser.
 */
private data class VaadinDispatcher(val ui: UI) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        ui.access { block.run() }
    }
}

/**
 * If the coroutine fails, redirect the exception to the Vaadin Error Handler
 * (the [VaadinSession.errorHandler] if set; if not,
 * the handler will simply rethrow the exception).
 */
private data class VaadinExceptionHandler(val ui: UI) : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        // send the exception to Vaadin
        ui.access {
            val errorHandler: ErrorHandler? = VaadinSession.getCurrent().errorHandler
            if (errorHandler != null) {
                errorHandler.error(ErrorEvent(exception))
            } else {
                throw exception
            }
        }
    }
}

/**
 * Provides the Vaadin Coroutine context for given [ui] (or the current one if none specified).
 */
fun vaadin(ui: UI = UI.getCurrent()): CoroutineContext = VaadinDispatcher(ui) + VaadinExceptionHandler(ui)

inline fun <T> withProgressDialog(message: String, block: ()->T): T {
    checkUIThread()
    val dlg = ProgressDialog(message)
    dlg.open()
    try {
        return block()
    } finally {
        dlg.close()
    }
}
class ProgressDialog(val message: String) : Dialog() {
    init {
        // the dialog is not modal on purpose, so that you can try the "Cancel" button.
        isResizable = false; isModal = false;
        verticalLayout {
            isMargin = true
            span(message)
            progressBar(indeterminate = true)
        }
    }
}

class ConfirmDialog(val message: String, private val response: (confirmed: Boolean) -> Unit) : Dialog() {
    private val responseRegistration: Registration
    init {
        isResizable = true; isModal = false;
        responseRegistration = addDialogCloseActionListener { response(false) }
        verticalLayout {
            span(message)
            horizontalLayout {
                content { align(right, middle) }
                button("Yes") {
                    setPrimary()
                    onLeftClick { cancel(); response(true) }
                }
                button("No") {
                    onLeftClick { cancel(); response(false) }
                }
            }
        }
    }

    fun cancel() {
        responseRegistration.remove()
        close()
    }
}

/**
 * callbackowe wywołanie vaadin opakowane w funkcję suspendowalną
 */
suspend fun confirmDialog(message: String = "Are you sure?"): Boolean {
    return suspendCancellableCoroutine { cont: CancellableContinuation<Boolean> ->
        checkUIThread()
        val dlg = ConfirmDialog(message) { response -> cont.resume(response) {} }
        dlg.open()
        cont.invokeOnCancellation { checkUIThread(); dlg.cancel() }
    }
}

