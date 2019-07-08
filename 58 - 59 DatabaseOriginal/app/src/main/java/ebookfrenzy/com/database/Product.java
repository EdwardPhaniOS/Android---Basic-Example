package ebookfrenzy.com.database;

public class Product
{
    private int _id;
    private String _productName;
    private int _quantity;

    public Product() {

    }

    public Product(int id, String productName, int quantity) {
        this._id = id;
        this._productName = productName;
        this._quantity = quantity;
    }

    public Product(String productName, int quantity) {
        this._productName = productName;
        this._quantity = quantity;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setProductName(String name) {
        this._productName = name;
    }

    public String getProductName() {
        return this._productName;
    }

    public void setQuantity(int quantity) {
        this._quantity = quantity;
    }

    public int getQuantity() {
        return this._quantity;
    }

}
