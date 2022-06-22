package com.example.vok_test_db.views

import com.example.vok_test_db.entities.FhirPatient
import com.github.mvysny.karibudsl.v10.*
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin.asFilterBar
import eu.vaadinonkotlin.vaadin.inRange
import eu.vaadinonkotlin.vaadin.istartsWith
import eu.vaadinonkotlin.vaadin.vokdb.asDataProvider

@Route("")
class MainView:KComposite() {
    val root = ui {
        verticalLayout {
            setSizeFull()
            grid( FhirPatient.dataLoader.asDataProvider()) {
                val filterBar = appendHeaderRow().asFilterBar(this)
                columnFor(FhirPatient::tenant) {
                    filterBar.forField(TextField(),this).eq(trim = true)
                }
                columnFor(FhirPatient::id) {
                    filterBar.forField(TextField(),this).istartsWith()
                }
                columnFor(FhirPatient::dt_birthdate) {
                    filterBar.forField(DateRangePopup(),this).inRange(FhirPatient::dt_birthdate)
                }
            }
        }
    }
}