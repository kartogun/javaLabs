public class Category {
    private String name;
    private String description;

    public Category(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва категорії не може бути порожньою");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Опис не може бути порожнім");
        }
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}
