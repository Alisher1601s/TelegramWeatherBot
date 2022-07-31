package model;

public class Model {
    private String name;
    private Double temp;
    private Double humidity;
    private String icon;

    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public Model() {

    }

    public Model(String name, double temp, double humidity, String icon) {
        this.name = name;
        this.temp = temp;
        this.humidity = humidity;
        this.icon = icon;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
