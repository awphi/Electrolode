package ph.adamw.electrolode.recipe;

public class RecipeUtils {
    public static boolean canComponentArraysStack(RecipeComponent[] attempted, RecipeComponent[] onto) {
        if(attempted.length != onto.length) {
            return false;
        }

        for(int i = 0; i < attempted.length; i ++) {
            if(attempted[i].isEmpty() || onto[i].isEmpty()) {
                continue;
            }

            if(!attempted[i].isSameType(onto[i]) || !attempted[i].canStack(onto[i])) {
                return false;
            }
        }

        return true;
    }
}
