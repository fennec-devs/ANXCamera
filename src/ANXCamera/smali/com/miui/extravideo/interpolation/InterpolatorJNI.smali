.class Lcom/miui/extravideo/interpolation/InterpolatorJNI;
.super Ljava/lang/Object;
.source "InterpolatorJNI.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;,
        Lcom/miui/extravideo/interpolation/InterpolatorJNI$Params;
    }
.end annotation


# static fields
.field public static final RGBA8888:I = 0x2

.field public static final YUV420SP:I = 0x0

.field public static final YVU420SP:I = 0x1


# instance fields
.field private final MOTION_LIST_MAX_NUM:I

.field private failureCode:Ljava/lang/String;

.field private handler:J

.field private motionAve:Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;

.field private motionList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-string/jumbo v0, "video_extra_interpolator"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    return-void
.end method

.method constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->motionList:Ljava/util/ArrayList;

    const/16 v0, 0x1e

    iput v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->MOTION_LIST_MAX_NUM:I

    new-instance v0, Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;

    invoke-direct {v0, p0}, Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;-><init>(Lcom/miui/extravideo/interpolation/InterpolatorJNI;)V

    iput-object v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->motionAve:Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;

    return-void
.end method

.method private final native finishJNI(J)I
.end method

.method private final native getDefaultParamsJNI(JLcom/miui/extravideo/interpolation/InterpolatorJNI$Params;)I
.end method

.method private final native getImageBufferJNI(JII)Ljava/nio/ByteBuffer;
.end method

.method private final native getImageIndexJNI(J[I)I
.end method

.method public static final native getVersion()Ljava/lang/String;
.end method

.method private final native initializeJNI(IIIII)J
.end method

.method private final native processJNI(J[BII[I[DZ)I
.end method

.method private final native setDividePositionJNI(JI)I
.end method

.method private final native startJNI(J)I
.end method


# virtual methods
.method public clearMotion()V
    .locals 4

    iget-object v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->motionAve:Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;

    iget-object v1, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->motionAve:Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;

    const-wide/16 v2, 0x0

    iput-wide v2, v1, Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;->y:D

    iput-wide v2, v0, Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;->x:D

    iget-object v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->motionList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    return-void
.end method

.method protected finalize()V
    .locals 4

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_0

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    invoke-direct {p0, v0, v1}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->finishJNI(J)I

    :cond_0
    return-void
.end method

.method public finish()V
    .locals 4

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_0

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    iput-wide v2, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    invoke-direct {p0, v0, v1}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->finishJNI(J)I

    :cond_0
    return-void
.end method

.method public getDefaultParams(Lcom/miui/extravideo/interpolation/InterpolatorJNI$Params;)I
    .locals 2

    const-wide/16 v0, 0x0

    invoke-direct {p0, v0, v1, p1}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->getDefaultParamsJNI(JLcom/miui/extravideo/interpolation/InterpolatorJNI$Params;)I

    move-result p1

    return p1
.end method

.method public getFailureCode()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->failureCode:Ljava/lang/String;

    return-object v0
.end method

.method public getImageBuffer(II)Ljava/nio/ByteBuffer;
    .locals 4

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_0

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    invoke-direct {p0, v0, v1, p1, p2}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->getImageBufferJNI(JII)Ljava/nio/ByteBuffer;

    move-result-object p1

    return-object p1

    :cond_0
    const/4 p1, 0x0

    return-object p1
.end method

.method public getImageIndex([I)I
    .locals 4

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_0

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    invoke-direct {p0, v0, v1, p1}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->getImageIndexJNI(J[I)I

    move-result p1

    return p1

    :cond_0
    const/4 p1, -0x1

    return p1
.end method

.method public getMotionX()D
    .locals 2

    iget-object v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->motionAve:Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;

    iget-wide v0, v0, Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;->x:D

    return-wide v0
.end method

.method public getMotionY()D
    .locals 2

    iget-object v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->motionAve:Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;

    iget-wide v0, v0, Lcom/miui/extravideo/interpolation/InterpolatorJNI$TypicalMotion;->y:D

    return-wide v0
.end method

.method public initialize(IIIII)I
    .locals 0

    nop

    invoke-direct/range {p0 .. p5}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->initializeJNI(IIIII)J

    move-result-wide p1

    iput-wide p1, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    iget-wide p1, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 p3, 0x0

    cmp-long p1, p1, p3

    if-nez p1, :cond_0

    const/high16 p1, -0x80000000

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    :goto_0
    const-string p2, ""

    iput-object p2, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->failureCode:Ljava/lang/String;

    return p1
.end method

.method public process([BIIZ)I
    .locals 13

    move-object v9, p0

    nop

    iget-wide v0, v9, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    new-array v10, v0, [I

    const/4 v0, 0x2

    new-array v7, v0, [D

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v11

    iget-wide v1, v9, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    move-object v0, v9

    move-object v3, p1

    move v4, p2

    move/from16 v5, p3

    move-object v6, v10

    move/from16 v8, p4

    invoke-direct/range {v0 .. v8}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->processJNI(J[BII[I[DZ)I

    move-result v0

    const/4 v1, 0x0

    or-int/2addr v0, v1

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v2

    sub-long/2addr v2, v11

    long-to-double v2, v2

    const-wide v4, 0x412e848000000000L    # 1000000.0

    div-double/2addr v2, v4

    const-string v4, "FN_FF"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "processJNI diff = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v2, v3}, Ljava/lang/StringBuilder;->append(D)Ljava/lang/StringBuilder;

    const-string v2, ", skip_engine_process="

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move/from16 v2, p4

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, ", index="

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move v2, p2

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v4, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    aget v1, v10, v1

    const/4 v2, 0x4

    if-eq v1, v2, :cond_0

    packed-switch v1, :pswitch_data_0

    const-string v1, ""

    iput-object v1, v9, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->failureCode:Ljava/lang/String;

    goto :goto_0

    :pswitch_0
    const-string v1, "Global motion"

    iput-object v1, v9, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->failureCode:Ljava/lang/String;

    goto :goto_0

    :pswitch_1
    const-string v1, "Local motion"

    iput-object v1, v9, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->failureCode:Ljava/lang/String;

    goto :goto_0

    :cond_0
    const-string v1, "Scene change"

    iput-object v1, v9, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->failureCode:Ljava/lang/String;

    nop

    :goto_0
    goto :goto_1

    :cond_1
    const v0, -0x7ffffffe

    :goto_1
    return v0

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public setDividePosition(I)I
    .locals 4

    nop

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_0

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    invoke-direct {p0, v0, v1, p1}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->setDividePositionJNI(JI)I

    move-result p1

    goto :goto_0

    :cond_0
    const p1, -0x7ffffffe

    :goto_0
    return p1
.end method

.method public start()I
    .locals 4

    nop

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    const-wide/16 v2, 0x0

    cmp-long v0, v0, v2

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->clearMotion()V

    iget-wide v0, p0, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->handler:J

    invoke-direct {p0, v0, v1}, Lcom/miui/extravideo/interpolation/InterpolatorJNI;->startJNI(J)I

    move-result v0

    goto :goto_0

    :cond_0
    const v0, -0x7ffffffe

    :goto_0
    return v0
.end method
