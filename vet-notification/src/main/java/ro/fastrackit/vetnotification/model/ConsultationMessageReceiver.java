package ro.fastrackit.vetnotification.model;

public class ConsultationMessageReceiver {
    private String vetName;
    private String petName;
    private String ownerName;

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "ConsultationMessageReceiver{" +
                "vetName='" + vetName + '\'' +
                ", petName='" + petName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
