package ebookfrenzy.com.database.provider;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import com.ebookfrenzy.database.MyDBHandler;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import ebookfrenzy.com.database.MyDBHandler;

public class MyContentProvider extends ContentProvider {

    private MyDBHandler myDB;

    private static final String AUTHORITY = "ebookfrenzy.com.database.provider.MyContentProvider";
    private static final String PRODUCT_TABLE = "products";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PRODUCT_TABLE);

    public static final int PRODUCTS = 1;
    public static final int PRODUCTS_ID = 2;

    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, PRODUCT_TABLE, PRODUCTS);
        sURIMatcher.addURI(AUTHORITY, PRODUCT_TABLE + "/#", PRODUCTS_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqLiteDB = myDB.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsDeleted = sqLiteDB.delete(MyDBHandler.TABLE_PRODUCT,
                        selection, selectionArgs);
                break;
            case PRODUCTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqLiteDB.delete(MyDBHandler.TABLE_PRODUCT,
                            MyDBHandler.COLUMN_ID + "=" + id, null);
                } else  {
                    rowsDeleted = sqLiteDB.delete(MyDBHandler.TABLE_PRODUCT,
                            MyDBHandler.COLUMN_ID + "=" + id + " and "
                                    + selection, selectionArgs)
                }
                break;
                default:
                    throw new UnsupportedOperationException("Unknow URI: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case PRODUCTS:
                id = sqlDB.insert(MyDBHandler.TABLE_PRODUCT, null, values);
                break;

                default:
                    throw new IllegalArgumentException("Unknow Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PRODUCT_TABLE + "/" + id);

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        myDB = new MyDBHandler(getContext(), null, null, 1)
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MyDBHandler.TABLE_PRODUCT);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case PRODUCTS_ID:
                queryBuilder.appendWhere(MyDBHandler.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case PRODUCTS:
                break;
                default:
                    throw new   IllegalArgumentException("Unknow URI")
        }

        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(), projection,
                selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(this.getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqLiteDB = myDB.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsUpdated = sqLiteDB.update(MyDBHandler.TABLE_PRODUCT,
                        values, selection, selectionArgs);
                break;
            case PRODUCTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqLiteDB.update(MyDBHandler.TABLE_PRODUCT, values
                    , MyDBHandler.COLUMN_ID + "=" + id
                                    + " and " + selection, selectionArgs);
                }
                break;
                default:
                    throw new UnsupportedOperationException("Not yet implemented");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
