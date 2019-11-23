.class public Lcom/android/camera/fragment/vv/ResourceManager;
.super Ljava/lang/Object;
.source "ResourceManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/fragment/vv/ResourceManager$ResourceListener;
    }
.end annotation


# static fields
.field private static final PREFIX_CLOUD_RESOURCE:Ljava/lang/String; = "https://"

.field private static final PREFIX_EXTERNAL_RESOURCE:Ljava/lang/String; = "sdcard/"

.field private static final PREFIX_NATIVE_RESOURCE:Ljava/lang/String; = "assets://"

.field private static final TEST_PATH:Ljava/lang/String;

.field private static sInstance:Lcom/android/camera/fragment/vv/ResourceManager;


# instance fields
.field private mStoragedResourceList:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Lcom/android/camera/fragment/vv/BaseResourceList;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v1}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "/MIUI/Camera/Test/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/fragment/vv/ResourceManager;->TEST_PATH:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/vv/ResourceManager;->mStoragedResourceList:Landroid/util/SparseArray;

    return-void
.end method

.method private decompressNativeResource(Landroid/content/Context;Lcom/android/camera/fragment/vv/BaseResourceItem;Ljava/lang/String;Z)V
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    iget-object v0, p2, Lcom/android/camera/fragment/vv/BaseResourceItem;->uri:Ljava/lang/String;

    const-string v1, "assets://"

    invoke-virtual {v0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x1

    aget-object v0, v0, v1

    nop

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p3, p2, Lcom/android/camera/fragment/vv/BaseResourceItem;->id:Ljava/lang/String;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object p3, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    if-eqz p4, :cond_0

    invoke-virtual {p2, p3}, Lcom/android/camera/fragment/vv/BaseResourceItem;->simpleVerification(Ljava/lang/String;)Z

    move-result p4

    if-eqz p4, :cond_0

    invoke-virtual {p2, p3}, Lcom/android/camera/fragment/vv/BaseResourceItem;->onDecompressFinished(Ljava/lang/String;)V

    return-void

    :cond_0
    new-instance p4, Ljava/lang/StringBuilder;

    invoke-direct {p4}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/android/camera/fragment/vv/ResourceManager;->TEST_PATH:Ljava/lang/String;

    invoke-virtual {p4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p4

    new-instance v1, Ljava/io/File;

    invoke-direct {v1, p4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v1

    const v2, 0x8000

    if-eqz v1, :cond_1

    invoke-static {p1, p4, p3, v2}, Lcom/android/camera/Util;->verifySdcardZip(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;I)V

    goto :goto_0

    :cond_1
    invoke-static {p1, v0, p3, v2}, Lcom/android/camera/Util;->verifyAssetZip(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;I)V

    :goto_0
    invoke-virtual {p2, p3}, Lcom/android/camera/fragment/vv/BaseResourceItem;->onDecompressFinished(Ljava/lang/String;)V

    return-void
.end method

.method private decompressSdcardResource(Landroid/content/Context;Ljava/lang/String;Lcom/android/camera/fragment/vv/BaseResourceItem;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p2, p3, Lcom/android/camera/fragment/vv/BaseResourceItem;->id:Ljava/lang/String;

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object p2, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p3, p1}, Lcom/android/camera/fragment/vv/BaseResourceItem;->onDecompressFinished(Ljava/lang/String;)V

    return-void
.end method

.method private decompressZip(Ljava/lang/String;Ljava/lang/String;)V
    .locals 6

    nop

    const/4 v0, 0x0

    :try_start_0
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, p2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v2

    if-nez v2, :cond_0

    invoke-virtual {v1}, Ljava/io/File;->mkdirs()Z

    :cond_0
    new-instance v1, Ljava/util/zip/ZipFile;

    invoke-direct {v1, p1}, Ljava/util/zip/ZipFile;-><init>(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_2
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    :try_start_1
    invoke-virtual {v1}, Ljava/util/zip/ZipFile;->entries()Ljava/util/Enumeration;

    move-result-object p1

    :goto_0
    invoke-interface {p1}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-interface {p1}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/zip/ZipEntry;

    invoke-virtual {v0}, Ljava/util/zip/ZipEntry;->getName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0}, Ljava/util/zip/ZipEntry;->isDirectory()Z

    move-result v3

    if-eqz v3, :cond_1

    new-instance v0, Ljava/io/File;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v0, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    goto :goto_2

    :cond_1
    new-instance v3, Ljava/io/File;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v3, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    new-instance v2, Ljava/io/FileOutputStream;

    invoke-direct {v2, v3}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    const/16 v3, 0x400

    new-array v3, v3, [B

    invoke-virtual {v1, v0}, Ljava/util/zip/ZipFile;->getInputStream(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;

    move-result-object v0

    :goto_1
    invoke-virtual {v0, v3}, Ljava/io/InputStream;->read([B)I

    move-result v4

    const/4 v5, -0x1

    if-eq v4, v5, :cond_2

    const/4 v5, 0x0

    invoke-virtual {v2, v3, v5, v4}, Ljava/io/FileOutputStream;->write([BII)V

    invoke-virtual {v2}, Ljava/io/FileOutputStream;->flush()V

    goto :goto_1

    :cond_2
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->close()V

    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    :goto_2
    goto :goto_0

    :cond_3
    nop

    :try_start_2
    invoke-virtual {v1}, Ljava/util/zip/ZipFile;->close()V
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_0

    goto :goto_4

    :catch_0
    move-exception p1

    goto :goto_5

    :catchall_0
    move-exception p1

    goto :goto_6

    :catch_1
    move-exception p1

    move-object v0, v1

    goto :goto_3

    :catchall_1
    move-exception p1

    move-object v1, v0

    goto :goto_6

    :catch_2
    move-exception p1

    :goto_3
    :try_start_3
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    if-eqz v0, :cond_4

    :try_start_4
    invoke-virtual {v0}, Ljava/util/zip/ZipFile;->close()V
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_0

    :goto_4
    nop

    nop

    :cond_4
    :goto_5
    return-void

    :goto_6
    if-eqz v1, :cond_5

    :try_start_5
    invoke-virtual {v1}, Ljava/util/zip/ZipFile;->close()V
    :try_end_5
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_3

    nop

    goto :goto_7

    :catch_3
    move-exception p2

    :cond_5
    :goto_7
    throw p1
.end method

.method public static final destroy()V
    .locals 1

    sget-object v0, Lcom/android/camera/fragment/vv/ResourceManager;->sInstance:Lcom/android/camera/fragment/vv/ResourceManager;

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/fragment/vv/ResourceManager;->sInstance:Lcom/android/camera/fragment/vv/ResourceManager;

    iget-object v0, v0, Lcom/android/camera/fragment/vv/ResourceManager;->mStoragedResourceList:Landroid/util/SparseArray;

    invoke-virtual {v0}, Landroid/util/SparseArray;->clear()V

    const/4 v0, 0x0

    sput-object v0, Lcom/android/camera/fragment/vv/ResourceManager;->sInstance:Lcom/android/camera/fragment/vv/ResourceManager;

    :cond_0
    return-void
.end method

.method public static getInstance()Lcom/android/camera/fragment/vv/ResourceManager;
    .locals 2

    sget-object v0, Lcom/android/camera/fragment/vv/ResourceManager;->sInstance:Lcom/android/camera/fragment/vv/ResourceManager;

    if-nez v0, :cond_1

    const-class v0, Lcom/android/camera/fragment/vv/ResourceManager;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/android/camera/fragment/vv/ResourceManager;->sInstance:Lcom/android/camera/fragment/vv/ResourceManager;

    if-nez v1, :cond_0

    new-instance v1, Lcom/android/camera/fragment/vv/ResourceManager;

    invoke-direct {v1}, Lcom/android/camera/fragment/vv/ResourceManager;-><init>()V

    sput-object v1, Lcom/android/camera/fragment/vv/ResourceManager;->sInstance:Lcom/android/camera/fragment/vv/ResourceManager;

    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    :cond_1
    :goto_0
    sget-object v0, Lcom/android/camera/fragment/vv/ResourceManager;->sInstance:Lcom/android/camera/fragment/vv/ResourceManager;

    return-object v0
.end method


# virtual methods
.method public convertStreamToString(Ljava/io/InputStream;)Ljava/lang/String;
    .locals 1

    new-instance v0, Ljava/util/Scanner;

    invoke-direct {v0, p1}, Ljava/util/Scanner;-><init>(Ljava/io/InputStream;)V

    const-string p1, "\\A"

    invoke-virtual {v0, p1}, Ljava/util/Scanner;->useDelimiter(Ljava/lang/String;)Ljava/util/Scanner;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/Scanner;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p1}, Ljava/util/Scanner;->next()Ljava/lang/String;

    move-result-object p1

    goto :goto_0

    :cond_0
    const-string p1, ""

    :goto_0
    return-object p1
.end method

.method public decompressResource(Landroid/content/Context;Lcom/android/camera/fragment/vv/BaseResourceList;Ljava/lang/String;Z)V
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<P:",
            "Lcom/android/camera/fragment/vv/BaseResourceList;",
            ">(",
            "Landroid/content/Context;",
            "TP;",
            "Ljava/lang/String;",
            "Z)V"
        }
    .end annotation

    invoke-virtual {p2}, Lcom/android/camera/fragment/vv/BaseResourceList;->getResourceList()Ljava/util/List;

    move-result-object v0

    :try_start_0
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/fragment/vv/BaseResourceItem;

    iget-object v2, v1, Lcom/android/camera/fragment/vv/BaseResourceItem;->uri:Ljava/lang/String;

    const-string v3, "assets://"

    invoke-virtual {v2, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-direct {p0, p1, v1, p3, p4}, Lcom/android/camera/fragment/vv/ResourceManager;->decompressNativeResource(Landroid/content/Context;Lcom/android/camera/fragment/vv/BaseResourceItem;Ljava/lang/String;Z)V

    goto :goto_1

    :cond_0
    const-string v3, "sdcard/"

    invoke-virtual {v2, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_1

    iget-object v2, v1, Lcom/android/camera/fragment/vv/BaseResourceItem;->uri:Ljava/lang/String;

    invoke-direct {p0, p1, v2, v1, p3}, Lcom/android/camera/fragment/vv/ResourceManager;->decompressSdcardResource(Landroid/content/Context;Ljava/lang/String;Lcom/android/camera/fragment/vv/BaseResourceItem;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    :cond_1
    :goto_1
    goto :goto_0

    :cond_2
    goto :goto_2

    :catch_0
    move-exception p1

    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V

    :goto_2
    iget-object p1, p0, Lcom/android/camera/fragment/vv/ResourceManager;->mStoragedResourceList:Landroid/util/SparseArray;

    invoke-virtual {p2}, Lcom/android/camera/fragment/vv/BaseResourceList;->getResourceType()I

    move-result p3

    invoke-virtual {p1, p3, p2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    return-void
.end method

.method public downloadCLoudResource(Landroid/content/Context;Lcom/android/camera/fragment/vv/BaseResourceItem;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    return-void
.end method

.method public getAssetCache(Ljava/lang/String;Landroid/content/Context;)Ljava/lang/String;
    .locals 2

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    :cond_0
    invoke-virtual {p2}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p2

    invoke-virtual {p2}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object p2

    nop

    :try_start_0
    invoke-virtual {p2, p1}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object p1
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    nop

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/vv/ResourceManager;->convertStreamToString(Ljava/io/InputStream;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :catch_0
    move-exception p1

    invoke-virtual {p1}, Ljava/io/IOException;->printStackTrace()V

    return-object v1
.end method

.method public getResourceList(I)Lcom/android/camera/fragment/vv/BaseResourceList;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<P:",
            "Lcom/android/camera/fragment/vv/BaseResourceList;",
            ">(I)TP;"
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/fragment/vv/ResourceManager;->mStoragedResourceList:Landroid/util/SparseArray;

    invoke-virtual {v0, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/fragment/vv/BaseResourceList;

    return-object p1
.end method
