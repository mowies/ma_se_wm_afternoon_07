package com.geoschnitzel.treasurehunt.backend.test;

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
public class SchemaTest {

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
    public void testDataExists() {
        List<User> expectedUsers = testDataService.generateUsers();
        List<Hunt> expectedHunts = testDataService.generateSchnitzelHunts(expectedUsers.get(1));

        List<User> actualUsers = asList(userRepository.findAll());
        List<Hunt> actualHunts = asList(huntRepository.findAll());

        assertThat(actualUsers, is(not(empty())));
        assertThat(actualHunts, is(not(empty())));

        assertThat(actualUsers, hasSize(expectedUsers.size()));
        assertThat(actualHunts, hasSize(expectedHunts.size()));

        for (int i = 0; i < expectedUsers.size(); i++) {
            assertThatUsersMatch(actualUsers.get(i), expectedUsers.get(i));
        }
        for (int i = 0; i < expectedHunts.size(); i++) {
            assertThatHuntsMatch(actualHunts.get(i), expectedHunts.get(i));
        }
    }


    private void assertThatHuntsMatch(Hunt actualHunt, Hunt expectedHunt) {
        assertThatUsersMatch(actualHunt.getCreator(), expectedHunt.getCreator());

        assertThat(actualHunt.getDescription(), is(equalTo(expectedHunt.getDescription())));
        assertThat(actualHunt.getMaxSpeed(), is(equalTo(expectedHunt.getMaxSpeed())));
        assertThat(actualHunt.getName(), is(equalTo(expectedHunt.getName())));
        assertThat(actualHunt.getStartArea(), is(equalTo(expectedHunt.getStartArea())));

        assertThat(actualHunt.getTargets(), hasSize(expectedHunt.getTargets().size()));
        for (int i = 0; i < expectedHunt.getTargets().size(); i++) {
            assertThatTargetsMatch(actualHunt.getTargets().get(i), expectedHunt.getTargets().get(i));
        }

        assertThat(actualHunt.getId(), is(notNullValue()));
        assertThat(expectedHunt.getId(), is(nullValue()));
    }
    private void assertThatTargetsMatch(Target actualTarget, Target expectedTarget) {
        assertThat(actualTarget.getArea(), is(equalTo(expectedTarget.getArea())));

        assertThat(actualTarget.getHints(), hasSize(expectedTarget.getHints().size()));
        for (int i = 0; i < expectedTarget.getHints().size(); i++) {
            assertThatHintsMatch(actualTarget.getHints().get(i), expectedTarget.getHints().get(i));
        }
    }

    private void assertThatHintsMatch(Hint actualHint, Hint expectedHint) {
        assertThat(actualHint, is(instanceOf(expectedHint.getClass())));
    }

    private void assertThatUsersMatch(User actualUser, User expectedUser) {
        assertThat(actualUser.getDisplayName(), is(equalTo(expectedUser.getDisplayName())));
        assertThat(actualUser.getEmail(), is(equalTo(expectedUser.getEmail())));
        assertThat(actualUser.getId(), is(notNullValue()));
        assertThat(expectedUser.getId(), is(nullValue()));
    }


}
