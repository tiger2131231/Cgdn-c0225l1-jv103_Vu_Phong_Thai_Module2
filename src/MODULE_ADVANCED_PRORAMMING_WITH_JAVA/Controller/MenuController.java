package MODULE_ADVANCED_PRORAMMING_WITH_JAVA.Controller;

import MODULE_ADVANCED_PRORAMMING_WITH_JAVA.Mode.Service.ContactsService;

import java.util.InputMismatchException; // Thêm để bắt lỗi nhập liệu
import java.util.Scanner;

public class MenuController {
    // Không cần tạo đối tượng ContactsService vì tất cả phương thức đều là static
    // private final ContactsService service = new ContactsService();

    private final Scanner scanner = new Scanner(System.in);

    public void MainMenu() {
        // Tải dữ liệu từ file khi chương trình bắt đầu
        // Đảm bảo dữ liệu được nạp vào bộ nhớ trước khi người dùng tương tác
        ContactsService.loadContactsFromFile();

        while (true) {
            System.out.println("==== CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ ====");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xoá");
            System.out.println("5. Tìm Kiếm");
            System.out.println("6. Tải dữ liệu từ file"); // Đổi tên cho rõ ràng hơn
            System.out.println("7. Lưu dữ liệu vào file"); // Đổi tên cho rõ ràng hơn
            System.out.println("8. Thoát ");
            System.out.print("Chọn chức năng: ");

            int chon;
            try {
                chon = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập một số.");
                continue; // Quay lại đầu vòng lặp để hiển thị menu
            }

            switch (chon) {
                case 1 -> hienthi();
                case 2 -> themmoi();
                case 3 -> capnhat();
                case 4 -> xoa(); // Phương thức xoa() cần được triển khai
                case 5 -> timkiem(); // Phương thức timkiem() cần được triển khai
                case 6 -> doctufile();
                case 7 -> ghivaofile();
                case 8 -> {
                    // Hỏi người dùng có muốn lưu trước khi thoát không (tùy chọn)
                    System.out.print("Bạn có muốn lưu thay đổi trước khi thoát không? (y/n): ");
                    String confirmSave = scanner.nextLine().trim().toLowerCase();
                    if (confirmSave.equals("y")) {
                        ContactsService.saveContactsToFile();
                    }
                    scanner.close(); // Đóng scanner khi thoát chương trình
                    System.out.println("Đã thoát chương trình.");
                    System.exit(0);
                }
                default -> System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    public void hienthi() {
        ContactsService.hienthidanhba();
    }

    public void themmoi() {
        ContactsService.themdanhba(scanner);
    }

    public void capnhat() {
        ContactsService.capnhat(scanner);
    }

    public void xoa() {
        ContactsService.xoadanhba(scanner);
    }

    public void timkiem() {
        ContactsService.timkiem(scanner);
    }

    public void doctufile() {
        System.out.println("Đang tải lại dữ liệu từ file...");
        ContactsService.loadContactsFromFile();
    }

    public void ghivaofile() {
        System.out.println("Đang lưu dữ liệu vào file...");
        ContactsService.saveContactsToFile();
    }
}