package com.jidian.cosalon.migration.pos365.thread.impl;

import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Categories;
import com.jidian.cosalon.migration.pos365.dto.BaseResponse;
import com.jidian.cosalon.migration.pos365.thread.MyThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component("categoriesThread")
public class CategoriesThread extends MyThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchThread.class);

    @Override
    public String getName() {
        return "categoriesThread";
    }

    @Override
    public void doRun() {
        try {
            jdbcTemplate.execute("TRUNCATE TABLE p365_categories");

            BaseResponse<Pos365Categories> response = pos365RetrofitService.listCategories(getMapHeaders2()).execute().body();
            LOGGER.info("Response: {}", response);

//                    branchJpaRepository.saveAll(response.getResults());
            List<Pos365Categories> pos365Categories = response.getResults();
            jdbcTemplate.batchUpdate("INSERT  " +
                    "INTO " +
                    "    p365_categories " +
                    "    (id, name, retailer_id, created_date, created_by)  " +
                    "  VALUES " +
                    "    (?,?,?,?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Pos365Categories categories = pos365Categories.get(i);
                    ps.setLong(1, categories.getId());
                    ps.setString(2, categories.getName());
                    ps.setLong(3, categories.getRetailerId() != null ? categories.getRetailerId() : 0);
                    ps.setTimestamp(4, categories.getCreatedDate());
                    ps.setLong(5, categories.getCreatedBy() != null ? categories.getCreatedBy() : 0);
                }

                @Override
                public int getBatchSize() {
                    return pos365Categories.size();
                }
            });
//            response.getResults().forEach(item -> {
//                jdbcTemplate.update("INSERT  " +
//                                "INTO " +
//                                "    p365_categories " +
//                                "    (id, name, retailer_id, created_date, created_by)  " +
//                                "  VALUES " +
//                                "    (?,?,?,?,?)",
//                        item.getId(), item.getName(), item.getRetailerId(), item.getCreatedDate(), item.getCreatedBy());
//            });
//            jdbcTemplate.execute("COMMIT");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
