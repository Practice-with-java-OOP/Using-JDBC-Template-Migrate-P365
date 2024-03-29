package com.jidian.cosalon.migration.pos365.thread;

import com.jidian.cosalon.migration.pos365.Utils;
import com.jidian.cosalon.migration.pos365.repository.TaskRepository;
import com.jidian.cosalon.migration.pos365.retrofitservice.Pos365RetrofitService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

//@Component
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class MyThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyThread.class);

    @Autowired
    protected Retrofit retrofit;

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    @Qualifier("jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("upmsJdbcTemplate")
    protected JdbcTemplate upmsJdbcTemplate;

    @Autowired
    @Qualifier("bhairJdbcTemplate")
    protected JdbcTemplate bhairJdbcTemplate;

    @Autowired
    @Qualifier("amsJdbcTemplate")
    protected JdbcTemplate amsJdbcTemplate;

    @Autowired
    @Qualifier("omsJdbcTemplate")
    protected JdbcTemplate omsJdbcTemplate;

    @Getter
    protected MyThreadStatus status = MyThreadStatus.IDLE;

    protected Pos365RetrofitService pos365RetrofitService;

    @PostConstruct
    public void init() {
        status = MyThreadStatus.IDLE;
        pos365RetrofitService = retrofit.create(Pos365RetrofitService.class);
        taskRepository.updateThread(getName(), status);
    }

    @PreDestroy
    public void destroy() {
        status = MyThreadStatus.DESTROYED;
    }

    public abstract String getName();

    public Map<String, String> getMapHeaders2() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.put("Cookie", "ss-id=" + Utils.SESSION_ID + ";" + Utils.PID);
        return headers;
    }

    @Override
    public void run() {
        Long currMillis = System.currentTimeMillis();
        try {
            status = MyThreadStatus.RUNNING;
            taskRepository.updateThread(getName(), status);
            doRun();
            status = MyThreadStatus.IDLE;
            taskRepository.updateThread(getName(), status);
        } catch (Exception e) {
            e.printStackTrace();
            status = MyThreadStatus.IDLE;
            taskRepository.updateThread(getName(), status);
        } finally {
            LOGGER.info("{} run time elapsed: {}s", getName(),
                (System.currentTimeMillis() - currMillis) / 1000);
        }
    }

    public abstract void doRun();
}
