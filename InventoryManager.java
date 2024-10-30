import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryManager {
    public void addInventoryItem(int supplierId, int itemId, int quantity) {
        String query = "INSERT INTO SupplierInventory (supplier_id, item_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, supplierId);
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkStock(int itemId) {
        String query = "SELECT quantity FROM SupplierInventory WHERE item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt("quantity") > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Other inventory management methods as needed
}
