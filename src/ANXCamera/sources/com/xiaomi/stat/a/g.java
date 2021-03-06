package com.xiaomi.stat.a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.stat.H;
import com.xiaomi.stat.a.l;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.r;
import java.util.Calendar;

class g implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ c f338a;

    g(c cVar) {
        this.f338a = cVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    public void run() {
        Cursor cursor;
        Cursor cursor2;
        try {
            SQLiteDatabase writableDatabase = this.f338a.l.getWritableDatabase();
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(r.b());
            instance.set(6, instance.get(6) - 7);
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            String[] strArr = {Long.toString(instance.getTimeInMillis()), l.a.m};
            int i = 1;
            String[] strArr2 = strArr;
            int i2 = 2;
            cursor = writableDatabase.query(j.f343b, new String[]{"ts"}, "ts < ? and e != ?", strArr, (String) null, (String) null, "ts ASC");
            try {
                int count = cursor.getCount();
                if (count != 0) {
                    H h = new H();
                    h.putInt(l.a.x, count);
                    k.c("EventManager", "delete obsolete events total number " + count);
                    int columnIndex = cursor.getColumnIndex("ts");
                    String str = null;
                    int i3 = 0;
                    while (cursor.moveToNext()) {
                        instance.setTimeInMillis(cursor.getLong(columnIndex));
                        String format = String.format("%4d%02d%02d", new Object[]{Integer.valueOf(instance.get(i)), Integer.valueOf(instance.get(i2) + i), Integer.valueOf(instance.get(5))});
                        if (!TextUtils.equals(str, format)) {
                            if (str != null) {
                                h.putInt(l.a.y + str, i3);
                            }
                            str = format;
                            i3 = 1;
                        } else {
                            i3++;
                        }
                        i = 1;
                        i2 = 2;
                    }
                    if (str != null) {
                        h.putInt(l.a.y + str, i3);
                    }
                    this.f338a.b(l.a(h));
                    writableDatabase.delete(j.f343b, "ts < ? and e != ?", strArr2);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e2) {
                e = e2;
                cursor2 = cursor;
                try {
                    k.c("EventManager", "remove obsolete events failed with " + e);
                    if (cursor2 == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursor2;
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursor != null) {
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            cursor2 = null;
            k.c("EventManager", "remove obsolete events failed with " + e);
            if (cursor2 == null) {
                cursor2.close();
            }
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
