/**
 * Клас для представлення куртки (похідний від Clothes).
 */
public class Jacket extends Clothes {
    private String season; // сезон: зимова, осіння, літня
    private boolean hasHood; // наявність капюшона

    public Jacket(String name, String size, double price, int quantity, String material,
                  String season, boolean hasHood) {
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

    public void setHasHood(boolean hasHood) {
        this.hasHood = hasHood;
    }

    @Override
    public String toString() {
        return "Куртка: " + super.toString() + ", Сезон: " + season + ", Капюшон: " + (hasHood ? "так" : "ні");
    }
}