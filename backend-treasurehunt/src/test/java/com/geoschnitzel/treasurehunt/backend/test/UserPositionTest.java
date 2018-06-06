package com.geoschnitzel.treasurehunt.backend.test;

import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hint;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.ItemFactory;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;
import com.geoschnitzel.treasurehunt.backend.service.GameService;
import com.geoschnitzel.treasurehunt.backend.service.TestDataService;
import com.geoschnitzel.treasurehunt.backend.service.UserService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;

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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserPositionTest {

    @Autowired
    private TestDataService testDataService;

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Before
    public void generateTestData() {
        testDataService.generateTestData();
    }

    @Test
    public void testReachedTarget() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        Target target = hunt.getTargets().get(0);

        userService.pushUserLocation(user.getId(),gameItem.getId(),ItemFactory.CreateCoordinateItem(target.getArea().getCoordinate()));
        assertTrue(gameService.CheckTargetReached(user.getId(), gameItem.getId()));

        gameItem = gameService.getGame(user.getId(),gameItem.getId());


        assertTrue(gameItem.getCurrenttarget() != null);
        assertTrue(gameItem.getCurrenttarget().getHints().size() > 0);
        assertTrue(gameItem.getCurrenttarget().getHints().get(0).getId() == hunt.getTargets().get(1).getHints().get(0).getId());

        target = hunt.getTargets().get(1);
        userService.pushUserLocation(user.getId(),gameItem.getId(),ItemFactory.CreateCoordinateItem(target.getArea().getCoordinate()));
        assertTrue(gameService.CheckTargetReached(user.getId(), gameItem.getId()));

        gameItem = gameService.getGame(user.getId(),gameItem.getId());

        assertTrue(gameItem.getEnded() != null);
    }
}
