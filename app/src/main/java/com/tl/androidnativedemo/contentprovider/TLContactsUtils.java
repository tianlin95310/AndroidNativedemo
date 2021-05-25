package com.tl.androidnativedemo.contentprovider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Toast;

import com.tl.androidnativedemo.vo.ContactVo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tianlin on 2017/3/15.
 * Tel : 15071485692
 * QQ : 953108373
 * Function : 读取联系人
 */

public class TLContactsUtils
{
    public static List<ContactVo> getAllContact(Context context)
    {
        List<ContactVo> list = null;
        Cursor rs1 = null;
        Cursor rs2 = null;
        Cursor rs3 = null;
        try
        {
            list = new ArrayList<>();
            rs1 = context.getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            while (rs1.moveToNext())
            {
                ContactVo contactVo = new ContactVo();

                // 先得到id，id为int类型，联系人表里只保存了联系人的姓名和id以及一些图标，根据id在其他的表里继续再查询得到电话号码
                int id = rs1.getInt(rs1.getColumnIndex(ContactsContract.Contacts._ID));
                // 联系人表里的联系人姓名
                contactVo.setName(rs1.getString(rs1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                // 查询电话信息表
                rs2 = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{String.valueOf(id)}, null);

                while (rs2.moveToNext())
                {
                    // 得到电话
                    contactVo.setNumber(rs2.getString(rs2
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }

                // 查询电子邮件表
                rs3 = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                        new String[]{String.valueOf(id)}, null);

                while (rs3.moveToNext())
                {
                    // 得到邮箱地址
                    contactVo.setEmail(rs3.getString(rs3
                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
                }

                rs2.close();
                rs3.close();

                if(!TextUtils.isEmpty(contactVo.getNumber()))
                    list.add(contactVo);

            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, "没有读取联系人权限！" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            if(rs1 != null)
                rs1.close();

            if(rs2 != null)
                rs2.close();

            if(rs3 != null)
                rs3.close();
        }

        return list;
    }

}
