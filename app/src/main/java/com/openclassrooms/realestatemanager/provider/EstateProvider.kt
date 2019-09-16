/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 16/09/19 14:32
 *
 */

package com.openclassrooms.realestatemanager.provider

/**
 * Created by Lionel JOFFRAY - on 16/09/2019.
 */

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Estate
import java.util.*
import java.util.concurrent.Callable


/**
 * A [ContentProvider] based on a Room database.
 *
 * Note that you don't need to implement a ContentProvider unless you want to expose the data
 * outside your process or your application already uses a ContentProvider.
 */
class EstateProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val code = MATCHER.match(uri)
        if (code == CODE_ESTATE_DIR || code == CODE_ESTATE_ITEM) {
            val context = context ?: return null
            val estate = RealEstateDatabase.getInstance(context).estateDao()
            val cursor: Cursor
            if (code == CODE_ESTATE_DIR) {
                cursor = estate.selectAll()
            } else {
                cursor = estate.selectEstateByEid(ContentUris.parseId(uri))
            }
            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        when (MATCHER.match(uri)) {
            CODE_ESTATE_DIR -> return "vnd.android.cursor.dir/" + AUTHORITY + "." + Estate.TABLE_NAME
            CODE_ESTATE_ITEM -> return "vnd.android.cursor.item/" + AUTHORITY + "." + Estate.TABLE_NAME
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (MATCHER.match(uri)) {
            CODE_ESTATE_DIR -> {
                val context = context ?: return null
                val id = RealEstateDatabase.getInstance(context).estateDao()
                        .createEstate(Estate.fromContentValues(values!!))
                context.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            CODE_ESTATE_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?,
                        selectionArgs: Array<String>?): Int {
        when (MATCHER.match(uri)) {
            CODE_ESTATE_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_ESTATE_ITEM -> {
                val context = context ?: return 0
                val count = RealEstateDatabase.getInstance(context).estateDao()
                        .deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        when (MATCHER.match(uri)) {
            CODE_ESTATE_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_ESTATE_ITEM -> {
                val context = context ?: return 0
                val estate = Estate.fromContentValues(values!!)
                estate.estateId = ContentUris.parseId(uri)
                val count = RealEstateDatabase.getInstance(context).estateDao()
                        .updateEstate(estate)
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @Throws(OperationApplicationException::class)
    override/* This gets propagated up from the Callable */ fun applyBatch(
            operations: ArrayList<ContentProviderOperation>): Array<ContentProviderResult?> {
        val context = context ?: return arrayOfNulls(0)
        val database = RealEstateDatabase.getInstance(context)
        return database.runInTransaction(Callable { super@EstateProvider.applyBatch(operations) })
    }

    companion object {

        /** The authority of this content provider.  */
        val AUTHORITY = "com.openclassrooms.realestatemanager.provider"

        /** The URI for the Cheese table.  */
        val URI_ESTATE = Uri.parse(
                "content://" + AUTHORITY + "/" + Estate.TABLE_NAME)

        /** The match code for some items in the Cheese table.  */
        private val CODE_ESTATE_DIR = 1

        /** The match code for an item in the Cheese table.  */
        private val CODE_ESTATE_ITEM = 2

        /** The URI matcher.  */
        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, Estate.TABLE_NAME, CODE_ESTATE_DIR)
            MATCHER.addURI(AUTHORITY, Estate.TABLE_NAME + "/*", CODE_ESTATE_ITEM)
        }
    }

}
