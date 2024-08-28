package api.model;

public enum PetStatus {

    available("available"),
    pending("pending"),
    sold("sold");

    private String value;

    PetStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
