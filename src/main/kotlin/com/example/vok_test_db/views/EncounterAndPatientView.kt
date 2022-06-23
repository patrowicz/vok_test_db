package com.example.vok_test_db.views

import com.example.vok_test_db.entities.FhirPatient
import com.example.vok_test_db.entities.QueryEncounterAndPatient
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.vokdataloader.buildFilter
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin.*
import eu.vaadinonkotlin.vaadin.vokdb.asDataProvider

@Route("encounters")
class EncounterAndPatientView:KComposite() {
    val root = ui {
        verticalLayout {
            setSizeFull()
            grid( QueryEncounterAndPatient.provider().asDataProvider { it.encounterId+"|"+it.patientId }) {
                val filterBar = appendHeaderRow().asFilterBar(this)
                columnFor(QueryEncounterAndPatient::encounterId) {
//                    filterBar.forField(TextField(),this).istartsWith()
                }
                columnFor(QueryEncounterAndPatient::patientId) {
                    val filterComponent = TextField()
                    filterBar.forField(filterComponent,this).withConverter {
                        buildFilter<QueryEncounterAndPatient> {
                            "fp.id like :fp_id"("fp_id" to "${filterComponent.value}%")
                        }
                    }.bind()
                }
                columnFor(QueryEncounterAndPatient::encounterStarted) {
//                    filterBar.forField(TextField(),this).istartsWith()
                }
                columnFor(QueryEncounterAndPatient::encounterEnded) {
//                    filterBar.forField(TextField(),this).istartsWith()
                }
            }
        }
    }
}
