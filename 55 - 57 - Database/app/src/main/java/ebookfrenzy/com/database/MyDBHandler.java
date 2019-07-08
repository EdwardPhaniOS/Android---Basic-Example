package ebookfrenzy.com.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "productDB.db";
    public static final String TABLE_PRODUCT = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";


    public MyDBHandler(Context context,
                       String name,
                       SQLiteDatabase.CursorFactory factory,
                       int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME
                + " TEXT," + COLUMN_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    public void addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public Product findProduct(String productName) {
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE "
                + COLUMN_PRODUCTNAME + " = \"" + productName + "\"";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(cursor.getInt(2));
        } else  {
            product = null;
        }

        db.close();
        return product;
    }

    public Boolean deleteProduct(String productName) {

        boolean result = false;

        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_PRODUCTNAME
                + " = \"" + productName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCT,
                    COLUMN_ID + " = ?",
                    new String[] { String.valueOf(product.getID())});
            cursor.close();
            result = true;
        }

        db.close();
        return result;
    }
}
