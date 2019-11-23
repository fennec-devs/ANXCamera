package android.support.v4.media;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.IMediaController2;
import java.util.ArrayList;
import java.util.List;

public interface IMediaSession2 extends IInterface {

    public static abstract class Stub extends Binder implements IMediaSession2 {
        private static final String DESCRIPTOR = "android.support.v4.media.IMediaSession2";
        static final int TRANSACTION_addPlaylistItem = 23;
        static final int TRANSACTION_adjustVolume = 4;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_fastForward = 9;
        static final int TRANSACTION_getChildren = 36;
        static final int TRANSACTION_getItem = 35;
        static final int TRANSACTION_getLibraryRoot = 34;
        static final int TRANSACTION_getSearchResult = 38;
        static final int TRANSACTION_pause = 6;
        static final int TRANSACTION_play = 5;
        static final int TRANSACTION_playFromMediaId = 18;
        static final int TRANSACTION_playFromSearch = 17;
        static final int TRANSACTION_playFromUri = 16;
        static final int TRANSACTION_prepare = 8;
        static final int TRANSACTION_prepareFromMediaId = 15;
        static final int TRANSACTION_prepareFromSearch = 14;
        static final int TRANSACTION_prepareFromUri = 13;
        static final int TRANSACTION_release = 2;
        static final int TRANSACTION_removePlaylistItem = 24;
        static final int TRANSACTION_replacePlaylistItem = 25;
        static final int TRANSACTION_reset = 7;
        static final int TRANSACTION_rewind = 10;
        static final int TRANSACTION_search = 37;
        static final int TRANSACTION_seekTo = 11;
        static final int TRANSACTION_selectRoute = 33;
        static final int TRANSACTION_sendCustomCommand = 12;
        static final int TRANSACTION_setPlaybackSpeed = 20;
        static final int TRANSACTION_setPlaylist = 21;
        static final int TRANSACTION_setRating = 19;
        static final int TRANSACTION_setRepeatMode = 29;
        static final int TRANSACTION_setShuffleMode = 30;
        static final int TRANSACTION_setVolumeTo = 3;
        static final int TRANSACTION_skipToNextItem = 28;
        static final int TRANSACTION_skipToPlaylistItem = 26;
        static final int TRANSACTION_skipToPreviousItem = 27;
        static final int TRANSACTION_subscribe = 39;
        static final int TRANSACTION_subscribeRoutesInfo = 31;
        static final int TRANSACTION_unsubscribe = 40;
        static final int TRANSACTION_unsubscribeRoutesInfo = 32;
        static final int TRANSACTION_updatePlaylistMetadata = 22;

        private static class Proxy implements IMediaSession2 {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public void addPlaylistItem(IMediaController2 iMediaController2, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(23, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void adjustVolume(IMediaController2 iMediaController2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void connect(IMediaController2 iMediaController2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void fastForward(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(9, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getChildren(IMediaController2 iMediaController2, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(36, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void getItem(IMediaController2 iMediaController2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getLibraryRoot(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(34, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getSearchResult(IMediaController2 iMediaController2, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(38, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void pause(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(6, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void play(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(5, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void playFromMediaId(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(18, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void playFromSearch(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(17, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void playFromUri(IMediaController2 iMediaController2, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(16, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void prepare(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(8, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void prepareFromMediaId(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(15, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void prepareFromSearch(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(14, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void prepareFromUri(IMediaController2 iMediaController2, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(13, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void release(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(2, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void removePlaylistItem(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(24, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void replacePlaylistItem(IMediaController2 iMediaController2, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(25, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void reset(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(7, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void rewind(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(10, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void search(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(37, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void seekTo(IMediaController2 iMediaController2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void selectRoute(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(33, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void sendCustomCommand(IMediaController2 iMediaController2, Bundle bundle, Bundle bundle2, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (resultReceiver != null) {
                        obtain.writeInt(1);
                        resultReceiver.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(12, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setPlaybackSpeed(IMediaController2 iMediaController2, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeFloat(f);
                    this.mRemote.transact(20, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setPlaylist(IMediaController2 iMediaController2, List<Bundle> list, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeTypedList(list);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(21, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setRating(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(19, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setRepeatMode(IMediaController2 iMediaController2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setShuffleMode(IMediaController2 iMediaController2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setVolumeTo(IMediaController2 iMediaController2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void skipToNextItem(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(28, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void skipToPlaylistItem(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(26, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void skipToPreviousItem(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(27, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void subscribe(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(39, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void subscribeRoutesInfo(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(31, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void unsubscribe(IMediaController2 iMediaController2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void unsubscribeRoutesInfo(IMediaController2 iMediaController2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    this.mRemote.transact(32, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void updatePlaylistMetadata(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaController2 != null ? iMediaController2.asBinder() : null);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(22, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaSession2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMediaSession2)) ? new Proxy(iBinder) : (IMediaSession2) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v39, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v42, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v48, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v54, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v60, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [android.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v51 */
        /* JADX WARNING: type inference failed for: r0v57 */
        /* JADX WARNING: type inference failed for: r0v63 */
        /* JADX WARNING: type inference failed for: r0v64 */
        /* JADX WARNING: type inference failed for: r0v65 */
        /* JADX WARNING: type inference failed for: r0v66 */
        /* JADX WARNING: type inference failed for: r0v67 */
        /* JADX WARNING: type inference failed for: r0v68 */
        /* JADX WARNING: type inference failed for: r0v69 */
        /* JADX WARNING: type inference failed for: r0v70 */
        /* JADX WARNING: type inference failed for: r0v71 */
        /* JADX WARNING: type inference failed for: r0v72 */
        /* JADX WARNING: type inference failed for: r0v73 */
        /* JADX WARNING: type inference failed for: r0v74 */
        /* JADX WARNING: type inference failed for: r0v75 */
        /* JADX WARNING: type inference failed for: r0v76 */
        /* JADX WARNING: type inference failed for: r0v77 */
        /* JADX WARNING: type inference failed for: r0v78 */
        /* JADX WARNING: type inference failed for: r0v79 */
        /* JADX WARNING: type inference failed for: r0v80 */
        /* JADX WARNING: type inference failed for: r0v81 */
        /* JADX WARNING: type inference failed for: r0v82 */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                ? r0 = 0;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        connect(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        release(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        setVolumeTo(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt());
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        adjustVolume(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt());
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        play(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        pause(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        reset(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        prepare(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        fastForward(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        rewind(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        seekTo(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readLong());
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                        Bundle bundle2 = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            r0 = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
                        }
                        sendCustomCommand(asInterface, bundle, bundle2, r0);
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface2 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        Uri uri = parcel.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        prepareFromUri(asInterface2, uri, r0);
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface3 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString = parcel.readString();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        prepareFromSearch(asInterface3, readString, r0);
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface4 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString2 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        prepareFromMediaId(asInterface4, readString2, r0);
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface5 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        Uri uri2 = parcel.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        playFromUri(asInterface5, uri2, r0);
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface6 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString3 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        playFromSearch(asInterface6, readString3, r0);
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface7 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString4 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        playFromMediaId(asInterface7, readString4, r0);
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface8 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString5 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        setRating(asInterface8, readString5, r0);
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        setPlaybackSpeed(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readFloat());
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface9 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        ArrayList createTypedArrayList = parcel.createTypedArrayList(Bundle.CREATOR);
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        setPlaylist(asInterface9, createTypedArrayList, r0);
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface10 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        updatePlaylistMetadata(asInterface10, r0);
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface11 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        int readInt = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        addPlaylistItem(asInterface11, readInt, r0);
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface12 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        removePlaylistItem(asInterface12, r0);
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface13 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        int readInt2 = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        replacePlaylistItem(asInterface13, readInt2, r0);
                        return true;
                    case 26:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface14 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        skipToPlaylistItem(asInterface14, r0);
                        return true;
                    case 27:
                        parcel.enforceInterface(DESCRIPTOR);
                        skipToPreviousItem(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 28:
                        parcel.enforceInterface(DESCRIPTOR);
                        skipToNextItem(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 29:
                        parcel.enforceInterface(DESCRIPTOR);
                        setRepeatMode(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                        return true;
                    case 30:
                        parcel.enforceInterface(DESCRIPTOR);
                        setShuffleMode(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                        return true;
                    case 31:
                        parcel.enforceInterface(DESCRIPTOR);
                        subscribeRoutesInfo(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 32:
                        parcel.enforceInterface(DESCRIPTOR);
                        unsubscribeRoutesInfo(IMediaController2.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 33:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface15 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        selectRoute(asInterface15, r0);
                        return true;
                    case 34:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface16 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        getLibraryRoot(asInterface16, r0);
                        return true;
                    case 35:
                        parcel.enforceInterface(DESCRIPTOR);
                        getItem(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        return true;
                    case 36:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface17 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString6 = parcel.readString();
                        int readInt3 = parcel.readInt();
                        int readInt4 = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        getChildren(asInterface17, readString6, readInt3, readInt4, r0);
                        return true;
                    case 37:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface18 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString7 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        search(asInterface18, readString7, r0);
                        return true;
                    case 38:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface19 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString8 = parcel.readString();
                        int readInt5 = parcel.readInt();
                        int readInt6 = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        getSearchResult(asInterface19, readString8, readInt5, readInt6, r0);
                        return true;
                    case 39:
                        parcel.enforceInterface(DESCRIPTOR);
                        IMediaController2 asInterface20 = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
                        String readString9 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            r0 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        subscribe(asInterface20, readString9, r0);
                        return true;
                    case 40:
                        parcel.enforceInterface(DESCRIPTOR);
                        unsubscribe(IMediaController2.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }
    }

    void addPlaylistItem(IMediaController2 iMediaController2, int i, Bundle bundle) throws RemoteException;

    void adjustVolume(IMediaController2 iMediaController2, int i, int i2) throws RemoteException;

    void connect(IMediaController2 iMediaController2, String str) throws RemoteException;

    void fastForward(IMediaController2 iMediaController2) throws RemoteException;

    void getChildren(IMediaController2 iMediaController2, String str, int i, int i2, Bundle bundle) throws RemoteException;

    void getItem(IMediaController2 iMediaController2, String str) throws RemoteException;

    void getLibraryRoot(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException;

    void getSearchResult(IMediaController2 iMediaController2, String str, int i, int i2, Bundle bundle) throws RemoteException;

    void pause(IMediaController2 iMediaController2) throws RemoteException;

    void play(IMediaController2 iMediaController2) throws RemoteException;

    void playFromMediaId(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException;

    void playFromSearch(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException;

    void playFromUri(IMediaController2 iMediaController2, Uri uri, Bundle bundle) throws RemoteException;

    void prepare(IMediaController2 iMediaController2) throws RemoteException;

    void prepareFromMediaId(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException;

    void prepareFromSearch(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException;

    void prepareFromUri(IMediaController2 iMediaController2, Uri uri, Bundle bundle) throws RemoteException;

    void release(IMediaController2 iMediaController2) throws RemoteException;

    void removePlaylistItem(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException;

    void replacePlaylistItem(IMediaController2 iMediaController2, int i, Bundle bundle) throws RemoteException;

    void reset(IMediaController2 iMediaController2) throws RemoteException;

    void rewind(IMediaController2 iMediaController2) throws RemoteException;

    void search(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException;

    void seekTo(IMediaController2 iMediaController2, long j) throws RemoteException;

    void selectRoute(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException;

    void sendCustomCommand(IMediaController2 iMediaController2, Bundle bundle, Bundle bundle2, ResultReceiver resultReceiver) throws RemoteException;

    void setPlaybackSpeed(IMediaController2 iMediaController2, float f) throws RemoteException;

    void setPlaylist(IMediaController2 iMediaController2, List<Bundle> list, Bundle bundle) throws RemoteException;

    void setRating(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException;

    void setRepeatMode(IMediaController2 iMediaController2, int i) throws RemoteException;

    void setShuffleMode(IMediaController2 iMediaController2, int i) throws RemoteException;

    void setVolumeTo(IMediaController2 iMediaController2, int i, int i2) throws RemoteException;

    void skipToNextItem(IMediaController2 iMediaController2) throws RemoteException;

    void skipToPlaylistItem(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException;

    void skipToPreviousItem(IMediaController2 iMediaController2) throws RemoteException;

    void subscribe(IMediaController2 iMediaController2, String str, Bundle bundle) throws RemoteException;

    void subscribeRoutesInfo(IMediaController2 iMediaController2) throws RemoteException;

    void unsubscribe(IMediaController2 iMediaController2, String str) throws RemoteException;

    void unsubscribeRoutesInfo(IMediaController2 iMediaController2) throws RemoteException;

    void updatePlaylistMetadata(IMediaController2 iMediaController2, Bundle bundle) throws RemoteException;
}
