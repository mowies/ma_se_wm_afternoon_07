package com.geoschnitzel.treasurehunt.backend;

import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hint;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;
import com.geoschnitzel.treasurehunt.backend.service.TestDataService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.transaction.Transactional;

import static com.geoschnitzel.treasurehunt.util.UtilsKt.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserPositionTest {

    @Autowired
    private TestDataService testDataService;

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void generateTestData() {
        testDataService.generateTestData();
    }

    @Test
    public void testReachedTarget() {

    }
}
