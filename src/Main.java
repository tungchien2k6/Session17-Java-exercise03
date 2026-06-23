import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookManager manager = new BookManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = getValidChoice(scanner);

            try {
                switch (choice) {
                    case 1:
                        addBookMenu(manager, scanner);
                        break;
                    case 2:
                        manager.listAllBooks();
                        break;
                    case 3:
                        updateBookMenu(manager, scanner);
                        break;
                    case 4:
                        deleteBookMenu(manager, scanner);
                        break;
                    case 5:
                        findByAuthorMenu(manager, scanner);
                        break;
                    case 0:
                        System.out.println("Cảm ơn bạn đã sử dụng chương trình quản lý thư viện!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("              QUẢN LÝ THƯ VIỆN SÁCH");
        System.out.println("=".repeat(55));
        System.out.println("1. Thêm sách mới");
        System.out.println("2. Hiển thị tất cả sách");
        System.out.println("3. Cập nhật thông tin sách");
        System.out.println("4. Xóa sách");
        System.out.println("5. Tìm sách theo tác giả");
        System.out.println("0. Thoát chương trình");
        System.out.println("=".repeat(55));
        System.out.print("Nhập lựa chọn: ");
    }

    private static int getValidChoice(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Vui lòng nhập số: ");
            }
        }
    }

    // ====================== THÊM SÁCH ======================
    private static void addBookMenu(BookManager manager, Scanner scanner) {
        System.out.print("Nhập tiêu đề sách: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Tiêu đề không được để trống!");
            return;
        }

        System.out.print("Nhập tên tác giả: ");
        String author = scanner.nextLine().trim();

        if (author.isEmpty()) {
            System.out.println("Tên tác giả không được để trống!");
            return;
        }

        System.out.print("Nhập năm xuất bản: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Nhập giá sách: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        Book book = new Book(title, author, year, price);
        manager.addBook(book);
    }

    // ====================== CẬP NHẬT SÁCH ======================
    private static void updateBookMenu(BookManager manager, Scanner scanner) {
        System.out.print("Nhập ID sách cần sửa: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Nhập tiêu đề mới: ");
        String title = scanner.nextLine().trim();

        System.out.print("Nhập tác giả mới: ");
        String author = scanner.nextLine().trim();

        System.out.print("Nhập năm xuất bản mới: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Nhập giá mới: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        Book book = new Book(title, author, year, price);
        manager.updateBook(id, book);
    }

    // ====================== XÓA SÁCH ======================
    private static void deleteBookMenu(BookManager manager, Scanner scanner) {
        System.out.print("Nhập ID sách cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        manager.deleteBook(id);
    }

    // ====================== TÌM THEO TÁC GIẢ ======================
    private static void findByAuthorMenu(BookManager manager, Scanner scanner) {
        System.out.print("Nhập tên tác giả cần tìm: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Tên tác giả không được để trống!");
            return;
        }
        manager.findBooksByAuthor(author);
    }
}