package ro.fasttrackit.vetclinic.model;

import java.util.Date;

public class VetsDto extends Human {
    private Date yearOfGraduation;
    private String specialization;

    public Date getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(Date yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "VetsDto{" +
                "yearOfGraduation=" + yearOfGraduation +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
