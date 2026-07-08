public class UserProfile {
    // 1. Private fields (Encapsulation)
    private String name;
    private int age;

    // 2. Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // 3. Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // 4. Main Method
    public static void main(String[] args) {
        // Create an instance of the class
        UserProfile user = new UserProfile();
        
        // Use setters to assign data
        user.setName("Alex");
        user.setAge(28);

        // Pass the object as a parameter to another method
        printDetails(user);
    }

    // 5. Method receiving the parameter
    public static void printDetails(UserProfile profile) {
        // Use getters to retrieve data
        System.out.println("User Name: " + profile.getName());
        System.out.println("User Age: " + profile.getAge());
    }
}
