package com.jidian.cosalon.migration.pos365.controller;

import com.jidian.cosalon.migration.pos365.thread.MyThreadStatus;
import com.jidian.cosalon.migration.pos365.domain.Warehouse;
import com.jidian.cosalon.migration.pos365.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = {"Task API"})
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TaskService taskService;

//    @ApiOperation("Hello World")
//    @GetMapping
    public List<Warehouse> hello() {
//        return "Hello World!";
        return jdbcTemplate.query("select * from ims_warehouse", (rs, rowNum) ->
                new Warehouse(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getTimestamp(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getLong(7),
                        rs.getInt(8)
                ));
    }

    @ApiOperation("Danh sách các task")
    @GetMapping
    public Map<String, MyThreadStatus> getAll() {
        return taskService.findAll();
    }

    @ApiOperation("Tạo mới task fetch dữ liệu từ POS365")
    @PostMapping("/fetch")
    public ResponseEntity createFetch() {
        try {
            return ResponseEntity.ok(taskService.createFetchingTask());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Tạo mới task migrate dữ liệu từ bảng dữ liệu thô POS365 sang bảng dữ liệu của CoSalon IMS")
    @PostMapping("/migrate")
    public ResponseEntity createMigrate() {
        try {
            return ResponseEntity.ok(taskService.createMigrationTask());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
