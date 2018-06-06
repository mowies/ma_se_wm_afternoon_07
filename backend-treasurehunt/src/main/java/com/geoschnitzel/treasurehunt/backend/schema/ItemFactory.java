package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.CoordinateItem;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.GameTargetItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemFactory {
    public static GameItem CreateGameItem(Game game) {
        return new GameItem(
                game.getId(),
                game.getUser().getId(),
                game.getHunt().getId(),
                CreateGameTargetItem(game.getTargets()),
                game.getStarted(),
                game.getPaused(),
                game.getEnded()
                );
    }
    public static List<GameTargetItem> CreateGameTargetItem(List<GameTarget> gameTargets) {
        List<GameTargetItem> result = new ArrayList<>();
        for(GameTarget gameTarget : gameTargets)
            result.add(CreateGameTargetItem(gameTarget));
        return result;
    }
    public static GameTargetItem CreateGameTargetItem(GameTarget gameTarget) {
        List<HintItem> hints = new ArrayList<>();
        hints.addAll(CreateHintItem(gameTarget.getUnlockedHints(), true));
        for(Hint hint : gameTarget.getTarget().getHints()) {
            if(!gameTarget.getUnlockedHints().contains(hint))
                hints.add(CreateHintItem(hint, false));
        }
        hints.sort(Comparator.comparingInt(HintItem::getShvalue));

        return new GameTargetItem(gameTarget.getId(), gameTarget.getStartTime(),hints );
    }

    public static List<HintItem> CreateHintItem(List<Hint> hints, boolean unlocked) {
        List<HintItem> results = new ArrayList<>();
        for (Hint hint : hints)
            results.add(CreateHintItem(hint, unlocked));
        return results;
    }

    public static HintItem CreateHintItem(Hint hint, boolean unlocked) {
        if (!unlocked)
            return new HintItem(hint.getId(), hint.getHintType(), hint.getShValue(), hint.getTimeToUnlockHint(), false, null, null, null, null);

        switch (hint.getHintType()) {
            case IMAGE:
                HintImage hintImage = (HintImage) hint;
                return new HintItem(hint.getId(), hint.getHintType(), hint.getShValue(), hint.getTimeToUnlockHint(), true, null, hintImage.getImageFileName(), null, null);
            case TEXT:
                HintText hintText = (HintText) hint;
                return new HintItem(hint.getId(), hint.getHintType(), hint.getShValue(), hint.getTimeToUnlockHint(), true, hintText.getDescription(), null, null, null);
            case COORDINATE:
                return new HintItem(hint.getId(), hint.getHintType(), hint.getShValue(), hint.getTimeToUnlockHint(), true, null, null, null, null);
            case DIRECTION:
                return new HintItem(hint.getId(), hint.getHintType(), hint.getShValue(), hint.getTimeToUnlockHint(), true, null, null, null, null);
            default:
                return null;
        }
    }

    public static UserItem CreateUserItem(User user) {
        return new UserItem(user.getId(),user.getBalance());
    }

    public static SHListItem CreateSHListItem(Hunt hunt) {
        return new SHListItem(
                hunt.getId(),
                hunt.getName(),
                hunt.getCreator().getDisplayName(),
                0,
                (float) 4.5,
                hunt.getDescription(),
                false,
                hunt.getTargets().size()
        ); //TODO calculate values that are hardcoded now
    }

    public static CoordinateItem CreateCoordinateItem(Coordinate coordinate) {
        return new CoordinateItem(coordinate.getLongitude(),coordinate.getLatitude());
    }
}
