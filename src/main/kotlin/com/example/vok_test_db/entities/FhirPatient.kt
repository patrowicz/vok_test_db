package com.example.vok_test_db.entities

import com.github.vokorm.KEntity
import com.gitlab.mvysny.jdbiorm.Dao
import com.gitlab.mvysny.jdbiorm.Table
import org.jdbi.v3.core.mapper.reflect.ColumnName
import org.jdbi.v3.json.EncodedJson
import java.time.LocalDate

@Table("fhir_patient")
data class FhirPatient(
    override var id: String? = null,
    var tenant:String = "",
    @EncodedJson
    var fhir:String? = null,
    @ColumnName("dt_birthdate")
    var dt_birthdate:LocalDate? = null,
):KEntity<String> {
    companion object: Dao<FhirPatient,String>(FhirPatient::class.java) {

    }
}