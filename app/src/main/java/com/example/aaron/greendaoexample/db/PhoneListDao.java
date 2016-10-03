package com.example.aaron.greendaoexample.db;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.example.aaron.greendaoexample.db.PhoneList;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PHONE_LIST".
*/
public class PhoneListDao extends AbstractDao<PhoneList, Long> {

    public static final String TABLENAME = "PHONE_LIST";

    /**
     * Properties of entity PhoneList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Phone = new Property(1, String.class, "phone", false, "PHONE");
        public final static Property UserId = new Property(2, long.class, "userId", false, "USER_ID");
    };

    private Query<PhoneList> user_UserPhoneQuery;

    public PhoneListDao(DaoConfig config) {
        super(config);
    }
    
    public PhoneListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PHONE_LIST\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PHONE\" TEXT NOT NULL ," + // 1: phone
                "\"USER_ID\" INTEGER NOT NULL );"); // 2: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PHONE_LIST\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PhoneList entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPhone());
        stmt.bindLong(3, entity.getUserId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PhoneList readEntity(Cursor cursor, int offset) {
        PhoneList entity = new PhoneList( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // phone
            cursor.getLong(offset + 2) // userId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PhoneList entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhone(cursor.getString(offset + 1));
        entity.setUserId(cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PhoneList entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PhoneList entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "userPhone" to-many relationship of User. */
    public List<PhoneList> _queryUser_UserPhone(long userId) {
        synchronized (this) {
            if (user_UserPhoneQuery == null) {
                QueryBuilder<PhoneList> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                user_UserPhoneQuery = queryBuilder.build();
            }
        }
        Query<PhoneList> query = user_UserPhoneQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }

}
