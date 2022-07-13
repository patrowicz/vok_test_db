package com.example.vok_test_db.views

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasElement
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout



class AppLayout:VerticalLayout(),RouterLayout {
    private val container:Div
    private lateinit var links:HorizontalLayout
    init {
        horizontalLayout {
            setWidthFull()
            routerLink(VaadinIcon.PLAY,null,LayoutView1::class)
            links = horizontalLayout(isPadding=false,isSpacing = true)
            div { isExpand = true }
        }
        container = div {
            isExpand = true
        }
    }

    override fun showRouterLayoutContent(content: HasElement) {
        container.element.removeAllChildren()
        links.removeAll()
        if(content is HasStringParam) {
            val linki = (content
                .stringParam
                .takeIf { it.isNotBlank() }
                ?.split("/")
                ?.filterNot { it.isBlank() }
                ?.scan("") { prev, id -> "$prev/$id".removePrefix("/") }
                ?.map {
                    routerLink(null,it.substringAfterLast('/'),LayoutView2::class,it)
                }
                ?.toTypedArray()
                ?: emptyArray())
            links.add( * linki )
        }
        container.element.appendChild(content.element)
    }

    override fun removeRouterLayoutContent(oldContent: HasElement?) {
        container.element.removeAllChildren()
        links.removeAll()
    }
}