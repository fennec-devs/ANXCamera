.class Landroid/support/v4/provider/DocumentsContractApi21;
.super Ljava/lang/Object;
.source "DocumentsContractApi21.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "DocumentFile"


# direct methods
.method constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static closeQuietly(Ljava/lang/AutoCloseable;)V
    .registers 2

    if-eqz p0, :cond_a

    :try_start_2
    invoke-interface {p0}, Ljava/lang/AutoCloseable;->close()V
    :try_end_5
    .catch Ljava/lang/RuntimeException; {:try_start_2 .. :try_end_5} :catch_8
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_5} :catch_6

    goto :goto_a

    :catch_6
    move-exception v0

    goto :goto_a

    :catch_8
    move-exception v0

    throw v0

    :cond_a
    :goto_a
    return-void
.end method

.method public static createDirectory(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    .registers 4

    const-string v0, "vnd.android.document/directory"

    invoke-static {p0, p1, v0, p2}, Landroid/support/v4/provider/DocumentsContractApi21;->createFile(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    return-object v0
.end method

.method public static createFile(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri;
    .registers 5

    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    invoke-static {v0, p1, p2, p3}, Landroid/provider/DocumentsContract;->createDocument(Landroid/content/ContentResolver;Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    return-object v0
.end method

.method public static listFiles(Landroid/content/Context;Landroid/net/Uri;)[Landroid/net/Uri;
    .registers 12

    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v6

    invoke-static {p1}, Landroid/provider/DocumentsContract;->getDocumentId(Landroid/net/Uri;)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/provider/DocumentsContract;->buildChildDocumentsUriUsingTree(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v7

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    move-object v8, v0

    const/4 v0, 0x0

    move-object v9, v0

    :try_start_14
    const-string v0, "document_id"

    filled-new-array {v0}, [Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v0, v6

    move-object v1, v7

    invoke-virtual/range {v0 .. v5}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v0

    move-object v9, v0

    :goto_24
    invoke-interface {v9}, Landroid/database/Cursor;->moveToNext()Z

    move-result v0

    if-eqz v0, :cond_37

    const/4 v0, 0x0

    invoke-interface {v9, v0}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/provider/DocumentsContract;->buildDocumentUriUsingTree(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    invoke-virtual {v8, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_36
    .catch Ljava/lang/Exception; {:try_start_14 .. :try_end_36} :catch_3d
    .catchall {:try_start_14 .. :try_end_36} :catchall_3b

    goto :goto_24

    :cond_37
    :goto_37
    invoke-static {v9}, Landroid/support/v4/provider/DocumentsContractApi21;->closeQuietly(Ljava/lang/AutoCloseable;)V

    goto :goto_55

    :catchall_3b
    move-exception v0

    goto :goto_62

    :catch_3d
    move-exception v0

    :try_start_3e
    const-string v1, "DocumentFile"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Failed query: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_54
    .catchall {:try_start_3e .. :try_end_54} :catchall_3b

    goto :goto_37

    :goto_55
    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v0

    new-array v0, v0, [Landroid/net/Uri;

    invoke-virtual {v8, v0}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Landroid/net/Uri;

    return-object v0

    :goto_62
    invoke-static {v9}, Landroid/support/v4/provider/DocumentsContractApi21;->closeQuietly(Ljava/lang/AutoCloseable;)V

    throw v0
.end method

.method public static prepareTreeUri(Landroid/net/Uri;)Landroid/net/Uri;
    .registers 2

    invoke-static {p0}, Landroid/provider/DocumentsContract;->getTreeDocumentId(Landroid/net/Uri;)Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Landroid/provider/DocumentsContract;->buildDocumentUriUsingTree(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    return-object v0
.end method

.method public static renameTo(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    .registers 4

    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    invoke-static {v0, p1, p2}, Landroid/provider/DocumentsContract;->renameDocument(Landroid/content/ContentResolver;Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    return-object v0
.end method
