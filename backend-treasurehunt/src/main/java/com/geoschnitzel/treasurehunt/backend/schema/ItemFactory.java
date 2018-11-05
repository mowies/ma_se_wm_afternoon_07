package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.AreaItem;
import com.geoschnitzel.treasurehunt.rest.CoordinateItem;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.GameTargetItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.HuntItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.TargetItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemFactory {
    public static List<TargetItem> CreateTargetItem(List<Target> targets) {
        List<TargetItem> result = new ArrayList<>();
        for(Target target : targets)
            result.add(CreateTargetItem(target));
        return result;
    }
    public static TargetItem CreateTargetItem(Target target) {
        return new TargetItem(target.getId(),CreateHintItem(target.getHints()),CreateAreaItem(target.getArea()) );
    }

    private static AreaItem CreateAreaItem(Area area) {
        return new AreaItem(
                CreateCoordinateItem(area.getCoordinate()),
                area.getRadius()
        );
    }

    public static List<HintItem> CreateHintItem(List<Hint> hints) {
        List<HintItem> results = new ArrayList<>();
        for (Hint hint : hints)
            results.add(CreateHintItem(hint,true));

        results.sort(Comparator.comparingInt(HintItem::getShvalue));
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

    public static HuntItem CreateHuntItem(Hunt hunt) {

        return new HuntItem(
                hunt.getId(),
                CreateTargetItem(hunt.getTargets()),
                hunt.getName(),
                hunt.getDescription(),
                hunt.getMaxSpeed(),
                hunt.getCreator().getDisplayName(),
                CreateAreaItem(hunt.getStartArea())
        );
    }
}
