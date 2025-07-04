package MODULE_ADVANCED_PRORAMMING_WITH_JAVA.Mode.Service;

import MODULE_ADVANCED_PRORAMMING_WITH_JAVA.Mode.Entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactsService {
    private static List<User> contactList = new ArrayList<>();
    private static final String FILE_PATH = "src/MODULE_ADVANCED_PRORAMMING_WITH_JAVA/Mode/Data/contacts.csv";

    public static void loadContactsFromFile() {
        contactList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Bỏ qua các dòng trống
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    try {
                        User user = new User(
                                parts[2].trim(),
                                parts[1].trim(),
                                parts[0].trim(),
                                parts[3].trim(),
                                parts[4].trim(),
                                parts[5].trim(),
                                parts[6].trim()
                        );
                        contactList.add(user);
                    } catch (Exception e) {
                        System.err.println("Lỗi định dạng dữ liệu trong file CSV cho dòng: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Dòng không hợp lệ trong file CSV (số cột không khớp): " + line);
                }
            }
            System.out.println("Đã tải " + contactList.size() + " liên hệ từ file.");
        } catch (FileNotFoundException e) {
            System.out.println("File contacts.csv không tồn tại. Tạo file mới.");
            try {
                new File(FILE_PATH).createNewFile();
            } catch (IOException ioException) {
                System.err.println("Lỗi khi tạo file mới: " + ioException.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file contacts.csv: " + e.getMessage());
        }
    }

    public static void saveContactsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : contactList) {
                String contactLine = String.join(",",
                        user.getFullName(),
                        user.getGroup(),
                        user.getNumberPhone(),
                        user.getSex(),
                        user.getAddress(),
                        user.getBirthday(),
                        user.getEmail());
                bw.write(contactLine);
                bw.newLine();
            }
            System.out.println("Đã lưu " + contactList.size() + " liên hệ vào file.");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file contacts.csv: " + e.getMessage());
        }
    }

    public static void hienthidanhba() {
        System.out.println("--- Danh bạ ---");
        if (contactList.isEmpty()) {
            System.out.println("Danh bạ trống.");
            return;
        }
        for (int i = 0; i < contactList.size(); i++) {
            User user = contactList.get(i);
            System.out.println((i + 1) + ". " + user.toString());
        }
    }

    public static void themdanhba(Scanner scanner) {
        System.out.println("--- Thêm danh bạ mới ---");
        String fullName;
        String group;
        String numberPhone;
        String sex;
        String address;
        String birthday;
        String email;

        System.out.print("Nhập Tên đầy đủ: ");
        fullName = scanner.nextLine();
        System.out.print("Nhập Nhóm (ví dụ: Gia đình, Bạn bè, Công việc): ");
        group = scanner.nextLine();
        while (true) {
            System.out.print("Nhập Số điện thoại: ");
            numberPhone = scanner.nextLine();
            if (isPhoneNumberExists(numberPhone)) {
                System.out.println("Số điện thoại này đã tồn tại trong danh bạ. Vui lòng nhập số khác.");
            } else if (!numberPhone.matches("\\d+")) {
                System.out.println("Số điện thoại không hợp lệ. Vui lòng chỉ nhập các chữ số.");
            } else {
                break;
            }
        }
        System.out.print("Nhập Giới tính (Nam/Nữ/Khác): ");
        sex = scanner.nextLine();
        System.out.print("Nhập Địa chỉ: ");
        address = scanner.nextLine();
        System.out.print("Nhập Ngày sinh (DD/MM/YYYY): ");
        birthday = scanner.nextLine();
        System.out.print("Nhập Email: ");
        email = scanner.nextLine();

        User newUser = new User(numberPhone, group, fullName, sex, address, birthday, email);
        contactList.add(newUser);
        System.out.println("Thêm danh bạ thành công vào bộ nhớ!");
        saveContactsToFile();
    }

    private static boolean isPhoneNumberExists(String phoneNumber) {
        for (User user : contactList) {
            if (user.getNumberPhone().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public static void capnhat(Scanner scanner) {
        System.out.println("--- Cập Nhật danh bạ ---");

        hienthidanhba();
        if (contactList.isEmpty()) {
            return;
        }
        System.out.print("\nNhập SỐ ĐIỆN THOẠI của người bạn muốn cập nhật: ");
        String searchPhoneNumber = scanner.nextLine();

        User userToUpdate = null;
        for (User user : contactList) {
            if (user.getNumberPhone().equals(searchPhoneNumber)) {
                userToUpdate = user;
                break;
            }
        }
        if (userToUpdate == null) {
            System.out.println("Không tìm thấy liên hệ với số điện thoại: " + searchPhoneNumber);
            return;
        }
        System.out.println("Đã tìm thấy liên hệ: " + userToUpdate.toString());
        System.out.println("Vui lòng nhập thông tin mới (Để trống nếu không muốn thay đổi):");
        System.out.print("Tên đầy đủ mới (" + userToUpdate.getFullName() + "): ");
        String newFullName = scanner.nextLine();
        if (!newFullName.trim().isEmpty()) {
            userToUpdate.setFullName(newFullName.trim());
        }
        System.out.print("Nhóm mới (" + userToUpdate.getGroup() + "): ");
        String newGroup = scanner.nextLine();
        if (!newGroup.trim().isEmpty()) {
            userToUpdate.setGroup(newGroup.trim());
        }
        System.out.print("Số điện thoại mới (" + userToUpdate.getNumberPhone() + "): ");
        String newNumberPhone = scanner.nextLine();
        if (!newNumberPhone.trim().isEmpty()) {
            if (isPhoneNumberExistsExceptSelf(newNumberPhone, userToUpdate)) {
                System.out.println("Lỗi: Số điện thoại mới này đã tồn tại cho một liên hệ khác.");
            } else if (!newNumberPhone.matches("\\d+")) {
                System.out.println("Lỗi: Số điện thoại không hợp lệ. Vui lòng chỉ nhập các chữ số.");
            } else {
                userToUpdate.setNumberPhone(newNumberPhone.trim());
            }
        }
        System.out.print("Giới tính mới (" + userToUpdate.getSex() + "): ");
        String newSex = scanner.nextLine();
        if (!newSex.trim().isEmpty()) {
            userToUpdate.setSex(newSex.trim());
        }
        System.out.print("Địa chỉ mới (" + userToUpdate.getAddress() + "): ");
        String newAddress = scanner.nextLine();
        if (!newAddress.trim().isEmpty()) {
            userToUpdate.setAddress(newAddress.trim());
        }
        System.out.print("Ngày sinh mới (DD/MM/YYYY) (" + userToUpdate.getBirthday() + "): ");
        String newBirthday = scanner.nextLine();
        if (!newBirthday.trim().isEmpty()) {
            userToUpdate.setBirthday(newBirthday.trim());
        }
        System.out.print("Email mới (" + userToUpdate.getEmail() + "): ");
        String newEmail = scanner.nextLine();
        if (!newEmail.trim().isEmpty()) {
            userToUpdate.setEmail(newEmail.trim());
        }
        System.out.println("Cập nhật danh bạ thành công vào bộ nhớ!");
        System.out.println("Thông tin mới: " + userToUpdate.toString());
        saveContactsToFile();
    }
    private static boolean isPhoneNumberExistsExceptSelf(String phoneNumber, User currentUser) {
        for (User user : contactList) {
            if (user != currentUser && user.getNumberPhone().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
    public static void xoadanhba(Scanner scanner) {
        System.out.println("--- Xóa danh bạ ---");
        hienthidanhba();
        if (contactList.isEmpty()) {
            System.out.println("Không có danh bạ để xóa.");
            return;
        }
        System.out.print("\nNhập SỐ ĐIỆN THOẠI của liên hệ bạn muốn xóa: ");
        String phoneNumberToDelete = scanner.nextLine();
        User userToRemove = null;
        for (User user : contactList) {
            if (user.getNumberPhone().equals(phoneNumberToDelete)) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove == null) {
            System.out.println("Không tìm thấy liên hệ với số điện thoại: " + phoneNumberToDelete);
            return;
        }
        System.out.println("Đã tìm thấy liên hệ: " + userToRemove.toString());
        System.out.print("Bạn có chắc chắn muốn xóa liên hệ này? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (confirmation.equals("y")) {
            contactList.remove(userToRemove);
            saveContactsToFile();
            System.out.println("Liên hệ đã được xóa thành công!");
        } else {
            System.out.println("Hủy bỏ thao tác xóa.");
        }
    }
    public static void timkiem(Scanner scanner) {
        System.out.println("--- Tìm kiếm danh bạ ---");
        if (contactList.isEmpty()) {
            System.out.println("Danh bạ trống, không có gì để tìm kiếm.");
            return;
        }
        System.out.print("Nhập từ khóa tìm kiếm (Tên, SĐT, Nhóm, Email, Địa chỉ, Giới tính, Ngày sinh): ");
        String keyword = scanner.nextLine().trim().toLowerCase(); // Chuyển về chữ thường để tìm kiếm không phân biệt hoa thường

        if (keyword.isEmpty()) {
            System.out.println("Vui lòng nhập từ khóa tìm kiếm.");
            return;
        }
        List<User> searchResults = new ArrayList<>();
        for (User user : contactList) {
            if (user.getFullName().toLowerCase().contains(keyword) ||
                    user.getNumberPhone().toLowerCase().contains(keyword) ||
                    user.getGroup().toLowerCase().contains(keyword) ||
                    user.getEmail().toLowerCase().contains(keyword) ||
                    user.getAddress().toLowerCase().contains(keyword) ||
                    user.getSex().toLowerCase().contains(keyword) ||
                    user.getBirthday().toLowerCase().contains(keyword)) {
                searchResults.add(user);
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("Không tìm thấy liên hệ nào phù hợp với từ khóa '" + keyword + "'.");
        } else {
            System.out.println("--- Kết quả tìm kiếm cho '" + keyword + "' ---");
            for (int i = 0; i < searchResults.size(); i++) {
                User foundUser = searchResults.get(i);
                System.out.println((i + 1) + ". " + foundUser.toString());
            }
        }
    }
}