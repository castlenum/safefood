package port.safefood.domain.food;

public enum FoodType {
    PROCESSED("가공식품"), FRESH("신선식품"), ETC("기타");

    private final String description;

    FoodType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
