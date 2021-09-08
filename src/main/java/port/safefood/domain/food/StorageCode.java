package port.safefood.domain.food;

public enum StorageCode {
    REFR("냉장보관"), FREEZE("냉동보관"), ROOM("상온보관");

    private final String description;

    StorageCode(String description) { this.description = description; }

    public String getDescription() { return description; }
}
