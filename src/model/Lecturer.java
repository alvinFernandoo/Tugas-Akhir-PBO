package model;

public class Lecturer extends Person {

    private String nidn;
    private String expertise;

    public Lecturer(
            String idCard,
            String name,
            String nidn,
            String expertise
    ) {

        super(idCard, name);

        this.nidn = nidn;
        this.expertise = expertise;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
    

    @Override
    public String toString() {
        return name;
    }

}