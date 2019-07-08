package ebookfrenzy.com.database.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import ebookfrenzy.com.database.MyDBHandler;

public class MyContentProvider extends ContentProvider {

    private MyDBHandler myDBHandler;

    private static final String AUTHORITY = "com.ebookfrenzy.database.provider.MyContentProvider";
    private static final String PRODUCTS_TABLE = "products";
    public static final Uri CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + PRODUCTS_TABLE);

    public static final int PRODUCTS = 1;
    public static final int PRODUCT_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, PRODUCTS_TABLE, PRODUCTS);
        sUriMatcher.addURI(AUTHORITY, PRODUCTS_TABLE + "/#", PRODUCT_ID);
    }


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = myDBHandler.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsDeleted = sqLiteDatabase.delete(MyDBHandler.TABLE_PRODUCT,
                        selection, selectionArgs);
                break;
            case PRODUCT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqLiteDatabase.delete(MyDBHandler.TABLE_PRODUCT,
                            MyDBHandler.COLUMN_ID + " = " + id, null);

                } else {
                    rowsDeleted = sqLiteDatabase.delete(MyDBHandler.TABLE_PRODUCT,
                            MyDBHandler.COLUMN_ID + " = " + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
                default:
                    throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = myDBHandler.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case PRODUCTS:
                id = sqLiteDatabase.insert(MyDBHandler.TABLE_PRODUCT,
                        null, values);
                break;
                default:
                    throw new UnsupportedOperationException("Unknow URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PRODUCTS_TABLE + "/" + id);
    }

    @Override
    public boolean onCreate() {
        myDBHandler = new MyDBHandler(getContext(), null, null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MyDBHandler.TABLE_PRODUCT);

        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case PRODUCT_ID:
                queryBuilder.appendWhere(MyDBHandler.COLUMN_ID
                        + " = " + uri.getLastPathSegment());
                break;
            case PRODUCTS:
                break;
                default:
                    throw new UnsupportedOperationException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(myDBHandler.getReadableDatabase(), projection,
                selection, selectionArgs,
        null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = myDBHandler.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsUpdated = sqLiteDatabase.update(MyDBHandler.TABLE_PRODUCT,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PRODUCT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqLiteDatabase.update(MyDBHandler.TABLE_PRODUCT,
                            values,
                            MyDBHandler.COLUMN_ID + " = " + id, null);
                } else  {
                    rowsUpdated = sqLiteDatabase.update(MyDBHandler.TABLE_PRODUCT,
                            values,
                            MyDBHandler.COLUMN_ID + " = " + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                default:
                    throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
