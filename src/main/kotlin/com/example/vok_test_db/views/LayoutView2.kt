package com.example.vok_test_db.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.text
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.WildcardParameter

interface HasStringParam:HasUrlParameter<String> {
    var stringParam:String

}
@Route("view2", layout = AppLayout::class)
class LayoutView2:VerticalLayout(),HasStringParam {

    override lateinit var stringParam: String
    override fun setParameter(p0: BeforeEvent?, @WildcardParameter p1: String) {
        stringParam = p1
        removeAll()
        add(
            div {
                text("Hello $stringParam")
            }
        )
    }
}