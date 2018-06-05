package com.geoschnitzel.treasurehunt.backend.test;

import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.service.GameService;
import com.geoschnitzel.treasurehunt.backend.service.TestDataService;
import com.geoschnitzel.treasurehunt.backend.service.UserService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static com.geoschnitzel.treasurehunt.util.UtilsKt.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GameServiceTest {

    @Autowired
    private TestDataService testDataService;

    @Autowired
    private GameService gameService;

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private UserService userService;

    @Before
    public void generateTestData() {
        testDataService.generateTestData();
    }


    @Test
    public void startGame() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getHints().get(0).getId(), is(hunt.getTargets().get(0).getHints().get(0).getId()));

        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(),is(1));
        assertThat(gameItem.getCurrenttarget().getLockedHints().size(),is(gameItem.getCurrenttarget().getHints().size() - 1));
        assertThat(gameItem.getCurrenttarget().getHints().size(),is(hunt.getTargets().get(0).getHints().size()));
        assertThat(gameItem.getHuntid(),is(hunt.getId()));
        assertThat(gameItem.getUserid(),is(user.getId()));
    }
    @Test
    public void startGameWrongUser() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        assertTrue(gameService.startGame(0, hunt.getId()) == null);
    }
    @Test
    public void startGameWrongHunt() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        assertTrue(gameService.startGame(user.getId(), 0) == null);
    }
    @Test
    public void getGameWrongUser() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(), hunt.getId());
        assertTrue(gameService.getGame(0,gameItem.getId())== null);
    }
    @Test
    public void getGameWrongGame() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        assertTrue(gameService.getGame(user.getId(),0)== null);
    }
    @Test
    public void getGameFromOtherUser() {
        UserItem user = userService.login();
        UserItem user2 = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(), hunt.getId());
        assertTrue(gameService.getGame(user2.getId(),gameItem.getId())== null);
    }

    @Test
    public void buyHint() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        HintItem buyHint = gameItem.getCurrenttarget().getLockedHints().get(gameItem.getCurrenttarget().getLockedHints().size() -1);
        gameService.buyHint(user.getId(), gameItem.getId(), buyHint.getId());
        gameItem = gameService.getGame(user.getId(), gameItem.getId());
        System.out.println(buyHint);

        HintItem addedHint = null;
        for (HintItem hintItem : gameItem.getCurrenttarget().getUnlockedHints()) {
            if(hintItem.getId() == buyHint.getId())
                addedHint = hintItem;
        }
        assertTrue(addedHint != null);
        System.out.println(addedHint);
    }
    @Test
    public void buyAllHints() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        for(int i = gameItem.getCurrenttarget().getUnlockedHints().size();i <= (hunt.getTargets().get(0).getHints().size());i++) {
            gameItem = gameService.getGame(user.getId(), gameItem.getId());
            HintItem buyHint = gameItem.getCurrenttarget().getLockedHints().get(0);
            assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(i));
            assertThat(gameItem.getCurrenttarget().getHints().size(),is(hunt.getTargets().get(0).getHints().size()));
            if(!gameService.buyHint(user.getId(), gameItem.getId(), buyHint.getId()))
            {
                user = userService.getUser(user.getId());
                assertTrue(buyHint.getShvalue() > user.getBalance());
                break;
            }
        }
    }

    @Test
    public void buySameHint() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
        HintItem buyHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        assertTrue(gameService.buyHint(user.getId(),gameItem.getId(), buyHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(2));
        assertFalse(gameService.buyHint(user.getId(),gameItem.getId(), buyHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(2));
    }
    @Test
    public void buyHintWrongUser() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
        HintItem buyHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        assertFalse(gameService.buyHint(0,gameItem.getId(), buyHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
    }
    @Test
    public void buyHintWrongHint() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);

        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
        HintItem buyHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        assertFalse(gameService.buyHint(user.getId(),gameItem.getId(), 0));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
    }
    @Test
    public void buyHintWrongHint2() {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        Hunt hunt2 = asList(huntRepository.findAll()).get(1);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
        HintItem buyHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        assertFalse(gameService.buyHint(user.getId(),gameItem.getId(), hunt2.getTargets().get(0).getHints().get(0).getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
    }
    @Test
    public void buyHintNoGame() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        assertFalse(gameService.buyHint(user.getId(),0,hunt.getTargets().get(0).getHints().get(0).getId()));
    }
    @Test
    public void buyHintGameFromOtherUser() throws InterruptedException {
        UserItem user = userService.login();
        UserItem user2 = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertFalse(gameService.buyHint(user2.getId(),gameItem.getId(),gameItem.getCurrenttarget().getLockedHints().get(0).getId()));
    }
    @Test
    public void unlockHintAfterTimeout() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
        HintItem unlockHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        Thread.sleep( unlockHint.getTimetounlockhint() * 1000);
        assertTrue(gameService.unlockHint(user.getId(),gameItem.getId(),unlockHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(2));
    }
    @Test
    public void unlockHintBuyHint() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));

        HintItem unlockHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        Thread.sleep( unlockHint.getTimetounlockhint() * 1000);
        assertTrue(gameService.unlockHint(user.getId(),gameItem.getId(),unlockHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(2));

        HintItem addedHint = null;
        for (HintItem hintItem : gameItem.getCurrenttarget().getUnlockedHints()) {
            if(hintItem.getId() == unlockHint.getId())
                addedHint = hintItem;
        }
        assertTrue(addedHint != null);

        HintItem buyHint = gameItem.getCurrenttarget().getLockedHints().get(gameItem.getCurrenttarget().getLockedHints().size() -1);
        assertTrue(gameService.buyHint(user.getId(), gameItem.getId(),buyHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(3));

        addedHint = null;
        for (HintItem hintItem : gameItem.getCurrenttarget().getUnlockedHints()) {
            if(hintItem.getId() == buyHint.getId())
                addedHint = hintItem;
        }
        assertTrue(addedHint != null);

        System.out.println(addedHint);
        System.out.println(buyHint);
    }
    @Test
    public void unlockHintBeforeTimeout() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
        HintItem unlockHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        assertFalse(gameService.unlockHint(user.getId(),gameItem.getId(),unlockHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
    }

    @Test
    public void unlockUnlockedHint() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
        HintItem unlockHint = gameItem.getCurrenttarget().getUnlockedHints().get(0);
        assertFalse(gameService.unlockHint(user.getId(),gameItem.getId(),unlockHint.getId()));
        gameItem = gameService.getGame(user.getId(),gameItem.getId());
        assertThat(gameItem.getCurrenttarget().getUnlockedHints().size(), is(1));
    }
    @Test
    public void unlockHintWrongUser() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        HintItem unlockHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        assertFalse(gameService.unlockHint(0,gameItem.getId(),unlockHint.getId()));
    }
    @Test
    public void unlockHintWrongHint() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertFalse(gameService.unlockHint(user.getId(),gameItem.getId(),0));
    }
    @Test
    public void unlockHintWrongHint2() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        Hunt hunt2 = asList(huntRepository.findAll()).get(1);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        assertFalse(gameService.unlockHint(user.getId(),gameItem.getId(),hunt2.getTargets().get(0).getHints().get(0).getId()));
    }
    @Test
    public void unlockHintNoGame() throws InterruptedException {
        UserItem user = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        assertFalse(gameService.unlockHint(user.getId(),0,hunt.getTargets().get(0).getHints().get(0).getId()));
    }
    @Test
    public void unlockHintGameFromOtherUser() throws InterruptedException {
        UserItem user = userService.login();
        UserItem user2 = userService.login();
        Hunt hunt = asList(huntRepository.findAll()).get(0);
        GameItem gameItem = gameService.startGame(user.getId(),hunt.getId());
        HintItem unlockHint = gameItem.getCurrenttarget().getLockedHints().get(0);
        Thread.sleep( unlockHint.getTimetounlockhint() * 1000);
        assertFalse(gameService.unlockHint(user2.getId(),gameItem.getId(),unlockHint.getId()));
    }



}
