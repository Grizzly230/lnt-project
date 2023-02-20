import java.util.*;

public class Main {
    private List<Employee> employees;
    private Map<String, Integer> employeeCount;

    public Main() {
        employees = new ArrayList<>();
        employeeCount = new HashMap<>();
    }

    public void addEmployee() {
        Scanner scanner = new Scanner(System.in);

        String code, name, gender, position;
        double salary;

        do {
            code = generateCode();
        } while (employeeCount.containsKey(code));

        System.out.print("Input nama karyawan [>=3]: ");
        name = scanner.nextLine();

        System.out.print("Input jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
        gender = scanner.nextLine();

        System.out.print("Input Jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
        position = scanner.nextLine();

        switch (position.toLowerCase()) {
            case "manager":
                salary = 8000000.0;
                break;
            case "supervisor":
                salary = 6000000.0;
                break;
            case "admin":
                salary = 4000000.0;
                break;
            default:
                System.out.println("Jabatan tidak valid!");
                return;
        }

        Employee employee = new Employee(code, name, gender, position, salary);
        employees.add(employee);

        employeeCount.merge(position.toLowerCase(), 1, Integer::sum);
        int positionCount = employeeCount.get(position.toLowerCase());

        if (positionCount % 3 == 0) {
            int bonusCount = positionCount / 3;
            double bonus;

            switch (position.toLowerCase()) {
                case "manager":
                    bonus = 0.1 * salary;
                    break;
                case "supervisor":
                    bonus = 0.075 * salary;
                    break;
                default:
                    bonus = 0.05 * salary;
                    break;
            }

            for (int i = 0; i < bonusCount; i++) {
                Employee bonusEmployee = employees.get(i);
                if (bonusEmployee.getPosition().equalsIgnoreCase(position)) {
                    bonusEmployee.setSalary(bonusEmployee.getSalary() + bonus);
                    System.out.printf("Bonus sebesar %.2f%% telah diberikan kepada karyawan dengan id %s\n", bonus * 100 / salary, bonusEmployee.getCode());
                }
            }
        }

        System.out.printf("Berhasil menambahkan karyawan dengan id %s\n", code);
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("Belum ada data karyawan!");
            return;
        }

        employees.sort(Comparator.comparing(Employee::getName));

        System.out.printf("| %-10s | %-20s | %-10s | %-15s | %-10s |\n", "Kode", "Nama", "Gender", "Jabatan", "Gaji");
        System.out.println("-".repeat(78));

        for (Employee employee : employees) {
            System.out.printf("| %-10s | %-20s | %-10s | %-15s | Rp. %8.2f |\n|", employee.getCode(), employee.getName(), employee.getGender(), employee.getPosition(), employee.getSalary());
        }
        System.out.println("-".repeat(78));
    }

    public void updateEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan kode karyawan yang akan diperbarui: ");
        String code = scanner.nextLine();

        Employee employeeToUpdate = null;

        for (Employee employee : employees) {
            if (employee.getCode().equals(code)) {
                employeeToUpdate = employee;
                break;
            }
        }

        if (employeeToUpdate != null) {
            System.out.println("Data Karyawan yang akan diperbarui:");
            System.out.printf("Kode         : %s\n", employeeToUpdate.getCode());
            System.out.printf("Nama         : %s\n", employeeToUpdate.getName());
            System.out.printf("Jenis Kelamin: %s\n", employeeToUpdate.getGender());
            System.out.printf("Jabatan      : %s\n", employeeToUpdate.getPosition());
            System.out.printf("Gaji         : Rp. %.2f\n", employeeToUpdate.getSalary());

            System.out.println("Masukkan data baru:");

            System.out.print("Nama karyawan [>=3]: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                employeeToUpdate.setName(name);
            }

            System.out.print("Jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
            String gender = scanner.nextLine();
            if (!gender.isEmpty()) {
                employeeToUpdate.setGender(gender);
            }

            System.out.print("Jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
            String position = scanner.nextLine();
            if (!position.isEmpty()) {
                switch (position.toLowerCase()) {
                    case "manager":
                        employeeToUpdate.setSalary(8000000.0);
                        break;
                    case "supervisor":
                        employeeToUpdate.setSalary(6000000.0);
                        break;
                    case "admin":
                        employeeToUpdate.setSalary(4000000.0);
                        break;
                    default:
                        System.out.println("Jabatan tidak valid!");
                        return;
                }
                employeeToUpdate.setPosition(position);
            }

            System.out.printf("Data karyawan dengan kode %s berhasil diperbarui.\n", code);
        } else {
            System.out.printf("Karyawan dengan kode %s tidak ditemukan.\n", code);
        }
    }

    public void deleteEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan kode karyawan yang akan dihapus: ");
        String code = scanner.nextLine();

        Employee employeeToDelete = null;

        for (Employee employee : employees) {
            if (employee.getCode().equals(code)) {
                employeeToDelete = employee;
                break;
            }
        }

        if (employeeToDelete != null) {
            employees.remove(employeeToDelete);
            employeeCount.merge(employeeToDelete.getPosition().toLowerCase(), -1, Integer::sum);
            System.out.printf("Karyawan dengan kode %s berhasil dihapus.\n", code);
        } else {
            System.out.printf("Karyawan dengan kode %s tidak ditemukan.\n", code);
        }
    }


    private String generateCode() {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}

class Employee {
    private String code;
    private String name;
    private String gender;
    private String position;
    private double salary;
    public Employee(String code, String name, String gender, String position, double salary) {
        this.code = code;
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.salary = salary;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.name = name;
    }

    public void setPosition(String position1) {
        this.name = name;
    }
}
class MainProgram {
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("========== Aplikasi Data Karyawan ==========");
            System.out.println("1. Tambah Karyawan");
            System.out.println("2. Lihat Data Karyawan");
            System.out.println("3. Update Data Karyawan");
            System.out.println("4. Hapus Data Karyawan");
            System.out.println("5. Keluar");
            System.out.println("============================================");
            System.out.print("Pilihan anda [1-5]: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    main.addEmployee();
                    break;
                case 2:
                    main.viewEmployees();
                    break;
                case 3:
                    scanner.nextLine();
                    main.updateEmployee();
                    break;
                case 4:
                    scanner.nextLine();
                    main.deleteEmployee();
                    break;
                case 5:
                    System.out.println("Terima kasih telah menggunakan aplikasi ini!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}