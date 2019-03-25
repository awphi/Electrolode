package ph.adamw.electrolode.recipe;

public class RecipeUtils {
    public static boolean canComponentArraysStack(RecipeComponent[] a, RecipeComponent[] b) {
        if(a.length != b.length) {
            return false;
        }

        for(int i = 0; i < a.length; i ++) {
            if(a[i].isEmpty() || b[i].isEmpty()) {
                continue;
            }

            if(!a[i].isSameType(b[i]) || !a[i].canStack(b[i])) {
                return false;
            }
        }

        return true;
    }
}
