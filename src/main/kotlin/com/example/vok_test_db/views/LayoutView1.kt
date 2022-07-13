package com.example.vok_test_db.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.text
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.router.Route

@Route("view1", layout = AppLayout::class)
class LayoutView1:KComposite() {
    val root = ui {
        verticalLayout {
            div {
                text("Hello!")
            }
        }
    }
}