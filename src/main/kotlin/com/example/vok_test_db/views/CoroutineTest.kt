package com.example.vok_test_db.views

import com.example.vok_test_db.confirmDialog
import com.example.vok_test_db.vaadin
import com.example.vok_test_db.withProgressDialog
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.DetachEvent
import com.vaadin.flow.component.HasText
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.VaadinSession
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import kotlin.coroutines.CoroutineContext
@Route("coroutine")
class CoroutineTest:KComposite(),CoroutineScope {
    companion object {
        val log = LoggerFactory.getLogger(CoroutineTest::class.java)
    }

    //scope i jego niszczenie
    private val uiCoroutineScope = SupervisorJob()
    private val uiCoroutineContext = vaadin()
    override val coroutineContext: CoroutineContext
        get() = uiCoroutineContext + uiCoroutineScope
    override fun onDetach(detachEvent: DetachEvent) {
        uiCoroutineScope.cancel()
        log.info("Canceled all coroutines started from the UI")
        super.onDetach(detachEvent)
    }


    @Transient
    private var job1: Job? = null

    val root = ui {
        verticalLayout {
            val t = textField("Krok")
            button("Start") {
                onLeftClick {
                    job1 = startJob1(t)
                }
            }
            button("Stop all") {
                onLeftClick { VaadinSession.getCurrent().close(); UI.getCurrent().page.reload() }
            }
        }
    }

    private fun startJob1(hasText: TextField): Job {
        check(coroutineContext.isActive)
        return launch {
//            withProgressDialog("Job1") {
                if(confirmDialog("Czy chcesz rozpocząć?")) {
                    log.info("Started")
                    repeat(5) {
                        hasText.value = "$it"
                        log.info("Tick $it")
                        delay(1000)
                    }
                } else throw Exception("EEE")
//            }
        }
    }
}