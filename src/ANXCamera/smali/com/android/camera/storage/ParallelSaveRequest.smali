.class public final Lcom/android/camera/storage/ParallelSaveRequest;
.super Lcom/android/camera/storage/AbstractSaveRequest;
.source "ParallelSaveRequest.java"


# static fields
.field private static final TAG:Ljava/lang/String;


# instance fields
.field private mAlgorithmName:Ljava/lang/String;

.field private mContext:Landroid/content/Context;

.field private mData:[B

.field private mInfo:Lcom/xiaomi/camera/core/PictureInfo;

.field private mJpegRotation:I

.field private mLocation:Landroid/location/Location;

.field private mNeedThumbnail:Z

.field private mSavePath:Ljava/lang/String;

.field private mSaverCallback:Lcom/android/camera/storage/SaverCallback;

.field private mSize:I

.field private mTimestamp:J


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-class v0, Lcom/android/camera/storage/ParallelSaveRequest;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Lcom/xiaomi/camera/core/ParallelTaskData;Lcom/android/camera/storage/SaverCallback;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/storage/AbstractSaveRequest;-><init>()V

    iput-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-virtual {p0, p2}, Lcom/android/camera/storage/ParallelSaveRequest;->setSaverCallback(Lcom/android/camera/storage/SaverCallback;)V

    iget-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mParallelTaskData:Lcom/xiaomi/camera/core/ParallelTaskData;

    invoke-virtual {p0, p1}, Lcom/android/camera/storage/ParallelSaveRequest;->calculateMemoryUsed(Lcom/xiaomi/camera/core/ParallelTaskData;)I

    move-result p1

    iput p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSize:I

    return-void
.end method


# virtual methods
.method public getSize()I
    .locals 1

    iget v0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSize:I

    return v0
.end method

.method public isFinal()Z
    .locals 1

    const/4 v0, 0x1

    return v0
.end method

.method public onFinish()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/xiaomi/camera/base/PerformanceTracker;->trackImageSaver(Ljava/lang/Object;I)V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    iget-object v0, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    iget v1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSize:I

    invoke-interface {v0, v1}, Lcom/android/camera/storage/SaverCallback;->onSaveFinish(I)V

    return-void
.end method

.method protected reFillSaveRequest([BJJLandroid/location/Location;ILjava/lang/String;IIZLjava/lang/String;Lcom/xiaomi/camera/core/PictureInfo;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    iput-wide p2, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mTimestamp:J

    iput-wide p4, p0, Lcom/android/camera/storage/ParallelSaveRequest;->date:J

    if-nez p6, :cond_0

    const/4 p1, 0x0

    goto :goto_0

    :cond_0
    new-instance p1, Landroid/location/Location;

    invoke-direct {p1, p6}, Landroid/location/Location;-><init>(Landroid/location/Location;)V

    :goto_0
    iput-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mLocation:Landroid/location/Location;

    iput p7, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mJpegRotation:I

    iput-object p8, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    iput p9, p0, Lcom/android/camera/storage/ParallelSaveRequest;->width:I

    iput p10, p0, Lcom/android/camera/storage/ParallelSaveRequest;->height:I

    iput-boolean p11, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mNeedThumbnail:Z

    iput-object p12, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mAlgorithmName:Ljava/lang/String;

    iput-object p13, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mInfo:Lcom/xiaomi/camera/core/PictureInfo;

    return-void
.end method

.method public run()V
    .locals 0

    invoke-virtual {p0}, Lcom/android/camera/storage/ParallelSaveRequest;->save()V

    invoke-virtual {p0}, Lcom/android/camera/storage/ParallelSaveRequest;->onFinish()V

    return-void
.end method

.method public save()V
    .locals 26

    move-object/from16 v0, p0

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/storage/ParallelSaveRequest;->parserParallelTaskData()V

    sget-object v1, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "save: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " | "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mTimestamp:J

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->intern()Ljava/lang/String;

    move-result-object v1

    monitor-enter v1

    :try_start_0
    invoke-static {}, Lcom/android/camera/db/DbRepository;->dbItemSaveTask()Lcom/android/camera/db/item/DbItemSaveTask;

    move-result-object v2

    iget-object v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v2, v3}, Lcom/android/camera/db/item/DbItemSaveTask;->getItemByPath(Ljava/lang/String;)Lcom/android/camera/db/element/SaveTask;

    move-result-object v2

    if-nez v2, :cond_0

    invoke-static {}, Lcom/android/camera/db/DbRepository;->dbItemSaveTask()Lcom/android/camera/db/item/DbItemSaveTask;

    move-result-object v3

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    invoke-virtual {v3, v4, v5}, Lcom/android/camera/db/item/DbItemSaveTask;->generateItem(J)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/db/element/SaveTask;

    iget-object v4, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v3, v4}, Lcom/android/camera/db/element/SaveTask;->setPath(Ljava/lang/String;)V

    const-wide/16 v4, -0x1

    invoke-static {v4, v5}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v4

    invoke-virtual {v3, v4}, Lcom/android/camera/db/element/SaveTask;->setStartTime(Ljava/lang/Long;)V

    invoke-static {}, Lcom/android/camera/db/DbRepository;->dbItemSaveTask()Lcom/android/camera/db/item/DbItemSaveTask;

    move-result-object v4

    const-wide/16 v5, 0x0

    invoke-virtual {v4, v3, v5, v6}, Lcom/android/camera/db/item/DbItemSaveTask;->endItemAndInsert(Ljava/lang/Object;J)J

    sget-object v3, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "insert full size picture:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v5, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    iget v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->width:I

    iget v4, v0, Lcom/android/camera/storage/ParallelSaveRequest;->height:I

    iget-object v5, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    invoke-static {v5}, Lcom/android/camera/Exif;->getOrientation([B)I

    move-result v5

    iget v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mJpegRotation:I

    add-int/2addr v6, v5

    rem-int/lit16 v6, v6, 0xb4

    if-nez v6, :cond_1

    nop

    goto :goto_0

    :cond_1
    nop

    nop

    move/from16 v25, v4

    move v4, v3

    move/from16 v3, v25

    :goto_0
    const/4 v15, 0x0

    if-eqz v2, :cond_3

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->isValid()Z

    move-result v6

    if-nez v6, :cond_2

    goto/16 :goto_1

    :cond_2
    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v6}, Lcom/android/camera/Util;->getFileTitleFromPath(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->getMediaStoreId()Ljava/lang/Long;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/Long;->longValue()J

    move-result-wide v6

    invoke-static {v6, v7}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->getResultUri(J)Landroid/net/Uri;

    move-result-object v6

    sget-object v7, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "algo mark: uri: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Landroid/net/Uri;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, " | "

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->getPath()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v7, v6}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v6, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "algo mark: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v8, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mJpegRotation:I

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " | "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " | "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " | "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v6, Landroid/provider/MediaStore$Images$Media;->EXTERNAL_CONTENT_URI:Landroid/net/Uri;

    invoke-virtual {v2}, Lcom/android/camera/db/element/SaveTask;->getMediaStoreId()Ljava/lang/Long;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/Long;->longValue()J

    move-result-wide v7

    invoke-static {v6, v7, v8}, Landroid/content/ContentUris;->withAppendedId(Landroid/net/Uri;J)Landroid/net/Uri;

    move-result-object v9

    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    iget-object v7, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    const/4 v8, 0x0

    iget-object v11, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mLocation:Landroid/location/Location;

    const/16 v16, 0x0

    const/16 v17, 0x0

    const/16 v18, 0x0

    iget-object v14, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mAlgorithmName:Ljava/lang/String;

    iget-object v13, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mInfo:Lcom/xiaomi/camera/core/PictureInfo;

    move v12, v5

    move-object v5, v13

    move v13, v3

    move-object v3, v14

    move v14, v4

    move v4, v15

    move-object/from16 v15, v16

    move/from16 v16, v17

    move/from16 v17, v18

    move-object/from16 v18, v3

    move-object/from16 v19, v5

    invoke-static/range {v6 .. v19}, Lcom/android/camera/storage/Storage;->updateImageWithExtraExif(Landroid/content/Context;[BLcom/android/gallery3d/exif/ExifInterface;Landroid/net/Uri;Ljava/lang/String;Landroid/location/Location;IIILjava/lang/String;ZZLjava/lang/String;Lcom/xiaomi/camera/core/PictureInfo;)Z

    iget-object v0, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    invoke-static {v0, v2, v4}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->markTaskFinish(Landroid/content/Context;Lcom/android/camera/db/element/SaveTask;Z)V

    goto/16 :goto_7

    :cond_3
    :goto_1
    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    if-eqz v6, :cond_4

    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v6}, Lcom/android/camera/Util;->getFileTitleFromPath(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    goto :goto_2

    :cond_4
    iget-wide v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->date:J

    invoke-static {v6, v7}, Lcom/android/camera/Util;->createJpegName(J)Ljava/lang/String;

    move-result-object v6

    :goto_2
    move-object v14, v6

    iget-object v6, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    iget-wide v8, v0, Lcom/android/camera/storage/ParallelSaveRequest;->date:J

    iget-object v10, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mLocation:Landroid/location/Location;

    iget-object v12, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    const/16 v16, 0x0

    const/16 v17, 0x0

    const/16 v18, 0x0

    const/16 v19, 0x0

    const/4 v13, 0x1

    if-eqz v2, :cond_5

    move/from16 v20, v13

    goto :goto_3

    :cond_5
    move/from16 v20, v15

    :goto_3
    iget-object v11, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mAlgorithmName:Ljava/lang/String;

    iget-object v7, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mInfo:Lcom/xiaomi/camera/core/PictureInfo;

    move-object/from16 v21, v7

    move-object v7, v14

    move-object/from16 v22, v11

    move v11, v5

    move v13, v3

    move-object/from16 v23, v2

    move-object v2, v14

    move v14, v4

    move-object/from16 v24, v2

    move v2, v15

    move/from16 v15, v16

    move/from16 v16, v17

    move/from16 v17, v18

    move/from16 v18, v19

    move/from16 v19, v20

    move-object/from16 v20, v22

    invoke-static/range {v6 .. v21}, Lcom/android/camera/storage/Storage;->addImage(Landroid/content/Context;Ljava/lang/String;JLandroid/location/Location;I[BIIZZZZZLjava/lang/String;Lcom/xiaomi/camera/core/PictureInfo;)Landroid/net/Uri;

    move-result-object v6

    if-eqz v6, :cond_a

    nop

    iget-boolean v7, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mNeedThumbnail:Z

    const-wide/high16 v8, 0x4080000000000000L    # 512.0

    if-eqz v7, :cond_7

    int-to-double v10, v3

    int-to-double v12, v4

    invoke-static {v10, v11, v12, v13}, Ljava/lang/Math;->max(DD)D

    move-result-wide v10

    div-double/2addr v10, v8

    invoke-static {v10, v11}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v10

    double-to-int v7, v10

    invoke-static {v7}, Ljava/lang/Integer;->highestOneBit(I)I

    move-result v7

    iget-object v10, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    invoke-static {v10, v5, v7, v6, v2}, Lcom/android/camera/Thumbnail;->createThumbnail([BIILandroid/net/Uri;Z)Lcom/android/camera/Thumbnail;

    move-result-object v7

    if-eqz v7, :cond_6

    iget-object v10, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    const/4 v11, 0x1

    invoke-interface {v10, v7, v11}, Lcom/android/camera/storage/SaverCallback;->postUpdateThumbnail(Lcom/android/camera/Thumbnail;Z)V

    nop

    move v7, v11

    goto :goto_5

    :cond_6
    const/4 v11, 0x1

    iget-object v7, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    invoke-interface {v7}, Lcom/android/camera/storage/SaverCallback;->postHideThumbnailProgressing()V

    goto :goto_4

    :cond_7
    const/4 v11, 0x1

    :goto_4
    move v7, v2

    :goto_5
    iget-object v10, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    const/4 v12, 0x2

    move-object/from16 v13, v24

    invoke-interface {v10, v6, v13, v12}, Lcom/android/camera/storage/SaverCallback;->notifyNewMediaData(Landroid/net/Uri;Ljava/lang/String;I)V

    if-eqz v23, :cond_8

    sget-object v3, Lcom/android/camera/storage/ParallelSaveRequest;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "algo mark: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Landroid/net/Uri;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {v6}, Landroid/content/ContentUris;->parseId(Landroid/net/Uri;)J

    move-result-wide v3

    invoke-static {v3, v4}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v3

    move-object/from16 v4, v23

    invoke-virtual {v4, v3}, Lcom/android/camera/db/element/SaveTask;->setMediaStoreId(Ljava/lang/Long;)V

    iget-object v0, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    invoke-static {v0, v4, v2}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->markTaskFinish(Landroid/content/Context;Lcom/android/camera/db/element/SaveTask;Z)V

    goto :goto_6

    :cond_8
    if-nez v7, :cond_9

    int-to-double v12, v3

    int-to-double v3, v4

    invoke-static {v12, v13, v3, v4}, Ljava/lang/Math;->max(DD)D

    move-result-wide v3

    div-double/2addr v3, v8

    invoke-static {v3, v4}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v3

    double-to-int v3, v3

    invoke-static {v3}, Ljava/lang/Integer;->highestOneBit(I)I

    move-result v3

    iget-object v4, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mData:[B

    invoke-static {v4, v5, v3, v6, v2}, Lcom/android/camera/Thumbnail;->createThumbnail([BIILandroid/net/Uri;Z)Lcom/android/camera/Thumbnail;

    move-result-object v2

    if-eqz v2, :cond_9

    iget-object v3, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    invoke-interface {v3, v2, v11}, Lcom/android/camera/storage/SaverCallback;->postUpdateThumbnail(Lcom/android/camera/Thumbnail;Z)V

    :cond_9
    iget-object v2, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    invoke-static {v6}, Landroid/content/ContentUris;->parseId(Landroid/net/Uri;)J

    move-result-wide v3

    iget-object v0, v0, Lcom/android/camera/storage/ParallelSaveRequest;->mSavePath:Ljava/lang/String;

    invoke-static {v2, v3, v4, v0}, Lcom/xiaomi/camera/parallelservice/util/ParallelUtil;->insertImageToParallelService(Landroid/content/Context;JLjava/lang/String;)V

    :cond_a
    :goto_6
    nop

    :goto_7
    monitor-exit v1

    return-void

    :catchall_0
    move-exception v0

    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v0
.end method

.method public setContextAndCallback(Landroid/content/Context;Lcom/android/camera/storage/SaverCallback;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mContext:Landroid/content/Context;

    iput-object p2, p0, Lcom/android/camera/storage/ParallelSaveRequest;->mSaverCallback:Lcom/android/camera/storage/SaverCallback;

    return-void
.end method
