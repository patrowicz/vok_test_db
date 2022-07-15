package com.example.vok_test_db.views

import com.example.vok_test_db.entities.FhirPatient
import com.example.vok_test_db.entities.QueryEncounterAndPatient
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.vokdataloader.buildFilter
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.virtuallist.VirtualList
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin.*
import eu.vaadinonkotlin.vaadin.vokdb.asDataProvider
import java.time.format.DateTimeFormatter

@Route("encounters2")
class EncounterAndPatientAsListView:KComposite() {
    val root = ui {
        verticalLayout {
            setSizeFull()
            val vl = VirtualList<QueryEncounterAndPatient>().apply {
                dataProvider = QueryEncounterAndPatient.provider().asDataProvider { it.encounterId+"|"+it.patientId }
                setRenderer(
                    ComponentRenderer { d: QueryEncounterAndPatient ->
                        HorizontalLayout().apply {
                            formLayout {
                                flexShrink = 0.0
                                text(d.encounterStarted?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "-")
                                text(d.encounterEnded?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "-")
                            }
//                            div {
//                                div {
//                                    text(d.encounterStarted?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "-")
//                                }
//                                div {
//                                    text(d.encounterEnded?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "-")
//                                }
//                            }
                            verticalLayout {
                                flexGrow = 1.0
                                text(d.patientId ?: "")
                                text(d.encounterId ?: "")
                            }
                        }
                    }
                )
            }
            add(vl.apply { isExpand = true })
        }
    }
}
