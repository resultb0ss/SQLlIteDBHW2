package com.example.sqllitedbhw

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper (context, DATABASE_NAME, factory, DATABASE_VERSION)
{
        companion object {
            private val DATABASE_NAME = "PRODUCT_DATABASE"
            private val DATABASE_VERSION = 1
            val TABLE_NAME = "product_table"
            val KEY_ID = "id"
            val KEY_NAME = "name"
            val KEY_WEIGHT = "weight"
            val KEY_PRICE = "price"
        }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    override fun onCreate(p0: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME
                + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_WEIGHT + " DOUBLE, " +
                KEY_PRICE + " INTEGER" +")")

        p0.execSQL(query)
    }

    fun addPerson(name: String, weight: Double, price: Int){
        val values = ContentValues()
        values.put(KEY_NAME,name)
        values.put(KEY_WEIGHT, weight)
        values.put(KEY_PRICE, price)
        val db = this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()

    }


    fun getInfo(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun removeAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null,null)
    }



    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)

    }

    override fun getReadableDatabase(): SQLiteDatabase {
        return super.getReadableDatabase()
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return super.getWritableDatabase()
    }

    override fun setIdleConnectionTimeout(idleConnectionTimeoutMs: Long) {
        super.setIdleConnectionTimeout(idleConnectionTimeoutMs)
    }

    override fun setOpenParams(openParams: SQLiteDatabase.OpenParams) {
        super.setOpenParams(openParams)
    }

    override fun setLookasideConfig(slotSize: Int, slotCount: Int) {
        super.setLookasideConfig(slotSize, slotCount)
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        super.setWriteAheadLoggingEnabled(enabled)
    }

    override fun getDatabaseName(): String {
        return super.getDatabaseName()
    }

    override fun close() {
        super.close()
    }
}