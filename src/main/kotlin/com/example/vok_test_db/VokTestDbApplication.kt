package com.example.vok_test_db

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
@Theme(themeClass = Lumo::class, variant = Lumo.DARK)
class VokTestDbApplication: SpringBootServletInitializer(), AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<VokTestDbApplication>(*args)
}
