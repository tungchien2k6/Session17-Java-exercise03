import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "S1mpL0rd";

    // Thêm sách (kiểm tra trùng)
    public boolean addBook(Book book) {
        String checkSql = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?";
        String insertSql = "INSERT INTO books(title, author, published_year, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Kiểm tra trùng
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, book.getTitle());
                checkStmt.setString(2, book.getAuthor());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Sách này đã tồn tại trong thư viện!");
                    return false;
                }
            }

            // Thêm mới
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, book.getTitle());
                insertStmt.setString(2, book.getAuthor());
                insertStmt.setInt(3, book.getPublishedYear());
                insertStmt.setDouble(4, book.getPrice());
                insertStmt.executeUpdate();
                System.out.println("Thêm sách thành công!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sách: " + e.getMessage());
            return false;
        }
    }

    // Liệt kê tất cả sách
    public void listAllBooks() {
        String sql = "SELECT * FROM books ORDER BY id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== DANH SÁCH SÁCH TRONG THƯ VIỆN ===");
            boolean hasBook = false;
            while (rs.next()) {
                hasBook = true;
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("published_year"),
                        rs.getDouble("price")
                );
                System.out.println(book);
            }
            if (!hasBook) System.out.println("Thư viện hiện đang trống.");
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    // Cập nhật sách
    public void updateBook(int id, Book newBook) {
        String sql = "UPDATE books SET title=?, author=?, published_year=?, price=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newBook.getTitle());
            stmt.setString(2, newBook.getAuthor());
            stmt.setInt(3, newBook.getPublishedYear());
            stmt.setDouble(4, newBook.getPrice());
            stmt.setInt(5, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Lập nhật sách thành công!");
            } else {
                System.out.println("Không tìm thấy sách có ID = " + id);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    // Xóa sách
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Xóa sách thành công!");
            } else {
                System.out.println("Không tìm thấy sách có ID = " + id);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa: " + e.getMessage());
        }
    }

    // Tìm kiếm theo tác giả
    public void findBooksByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author ILIKE ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n=== KẾT QUẢ TÌM KIẾM TÁC GIẢ: " + author + " ===");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    Book book = new Book(rs.getInt("id"), rs.getString("title"),
                            rs.getString("author"), rs.getInt("published_year"), rs.getDouble("price"));
                    System.out.println(book);
                }
                if (!found) System.out.println("Không tìm thấy sách của tác giả này.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm: " + e.getMessage());
        }
    }
}