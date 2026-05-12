public class Jacket extends Clothes {
    private String season;
    private boolean hasHood;

    public Jacket(String name, String size, double price, int quantity, String material, String season, boolean hasHood) {
        super(name, size, price, quantity, material);
        setSeason(season);
        this.hasHood = hasHood;
    }

    public String getSeason() { return season; }
    public boolean isHasHood() { return hasHood; }

    public void setSeason(String season) {
        if (season == null || (!season.equals("зимова") && !season.equals("осіння") && !season.equals("літня"))) {
            throw new IllegalArgumentException("Сезон має бути: зимова, осіння, літня");
        }
        this.season = season;
    }

    @Override
    public String toString() {
        return "Куртка: " + super.toString() + ", Сезон: " + season + ", Капюшон: " + (hasHood ? "так" : "ні");
    }
}