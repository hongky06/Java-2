class User {

    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) throws InvalidAgeException {

        if (age < 0) {
            throw new InvalidAgeException("Tuổi không thể âm!");
        }

        this.age = age;
    }

    public void printUser() {

        if (name != null) {
            System.out.println("Tên người dùng: " + name);
        } else {
            System.out.println("Tên chưa được thiết lập.");
        }

        System.out.println("Tuổi: " + age);
    }
}