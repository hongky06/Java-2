package Session11.baitap05;

public class Doctor {
    private int doctorId;
    private String fullName;
    private String specialty;

    public Doctor(int doctorId, String fullName, String specialty) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
    }

    // Getters và Setters
    public int getDoctorId() { return doctorId; }
    public String getFullName() { return fullName; }
    public String getSpecialty() { return specialty; }
}