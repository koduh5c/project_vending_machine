package project.model.user.container;

public class Anonymous extends UserImpl {
    private static Anonymous INSTANCE;

    private Anonymous() {}

    public static Anonymous getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Anonymous();
        }
        return INSTANCE;
    }
}
