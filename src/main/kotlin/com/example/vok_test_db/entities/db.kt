package com.example.vok_test_db.entities

import com.github.vokorm.db
import com.gitlab.mvysny.jdbiorm.JdbiOrm
import com.gitlab.mvysny.jdbiorm.JdbiOrm.jdbi
import org.jdbi.v3.core.statement.Slf4JSqlLogger
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.stereotype.Component
import javax.sql.DataSource


@Configuration
class DsConfig {
    @Bean
    @ConfigurationProperties("spring.datasource")
    fun dataSource(): DataSource {
        return DriverManagerDataSource()
    }

    @Bean
    fun dataSourceTransactionManager(dataSource: DataSource): DataSourceTransactionManager {
        val dataSourceTransactionManager = DataSourceTransactionManager()
        dataSourceTransactionManager.dataSource = dataSource
        return dataSourceTransactionManager
    }


}

@Component
class DsInit(
    internal val dataSource: DataSource
): CommandLineRunner {
    override fun run(vararg args: String?) {
        JdbiOrm.setDataSource(dataSource)
        JdbiOrm.jdbi().setSqlLogger(Slf4JSqlLogger())
        db {
//            handle.createUpdate("""create table if not exists aaa (
//                id bigserial primary key,
//                nazwa text
//                )
//                """).execute()
        }
    }
}