.class public final Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
.super Ljava/lang/Object;
.source "PlaybackStateCompat.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Landroid/support/v4/media/session/PlaybackStateCompat;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Builder"
.end annotation


# instance fields
.field private mActions:J

.field private mActiveItemId:J

.field private mBufferedPosition:J

.field private final mCustomActions:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;",
            ">;"
        }
    .end annotation
.end field

.field private mErrorMessage:Ljava/lang/CharSequence;

.field private mExtras:Landroid/os/Bundle;

.field private mPosition:J

.field private mRate:F

.field private mState:I

.field private mUpdateTime:J


# direct methods
.method public constructor <init>()V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mCustomActions:Ljava/util/List;

    const-wide/16 v0, -0x1

    iput-wide v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActiveItemId:J

    return-void
.end method

.method public constructor <init>(Landroid/support/v4/media/session/PlaybackStateCompat;)V
    .registers 4

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mCustomActions:Ljava/util/List;

    const-wide/16 v0, -0x1

    iput-wide v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActiveItemId:J

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mState:I
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$300(Landroid/support/v4/media/session/PlaybackStateCompat;)I

    move-result v0

    iput v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mState:I

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mPosition:J
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$400(Landroid/support/v4/media/session/PlaybackStateCompat;)J

    move-result-wide v0

    iput-wide v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mPosition:J

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mSpeed:F
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$500(Landroid/support/v4/media/session/PlaybackStateCompat;)F

    move-result v0

    iput v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mRate:F

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mUpdateTime:J
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$600(Landroid/support/v4/media/session/PlaybackStateCompat;)J

    move-result-wide v0

    iput-wide v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mUpdateTime:J

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mBufferedPosition:J
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$700(Landroid/support/v4/media/session/PlaybackStateCompat;)J

    move-result-wide v0

    iput-wide v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mBufferedPosition:J

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mActions:J
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$800(Landroid/support/v4/media/session/PlaybackStateCompat;)J

    move-result-wide v0

    iput-wide v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActions:J

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mErrorMessage:Ljava/lang/CharSequence;
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$900(Landroid/support/v4/media/session/PlaybackStateCompat;)Ljava/lang/CharSequence;

    move-result-object v0

    iput-object v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mErrorMessage:Ljava/lang/CharSequence;

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mCustomActions:Ljava/util/List;
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$1000(Landroid/support/v4/media/session/PlaybackStateCompat;)Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_47

    iget-object v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mCustomActions:Ljava/util/List;

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mCustomActions:Ljava/util/List;
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$1000(Landroid/support/v4/media/session/PlaybackStateCompat;)Ljava/util/List;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    :cond_47
    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mActiveItemId:J
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$1100(Landroid/support/v4/media/session/PlaybackStateCompat;)J

    move-result-wide v0

    iput-wide v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActiveItemId:J

    # getter for: Landroid/support/v4/media/session/PlaybackStateCompat;->mExtras:Landroid/os/Bundle;
    invoke-static {p1}, Landroid/support/v4/media/session/PlaybackStateCompat;->access$1200(Landroid/support/v4/media/session/PlaybackStateCompat;)Landroid/os/Bundle;

    move-result-object v0

    iput-object v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mExtras:Landroid/os/Bundle;

    return-void
.end method


# virtual methods
.method public addCustomAction(Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 4

    if-eqz p1, :cond_8

    iget-object v0, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mCustomActions:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    return-object p0

    :cond_8
    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "You may not add a null CustomAction to PlaybackStateCompat."

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public addCustomAction(Ljava/lang/String;Ljava/lang/String;I)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 11

    new-instance v6, Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v0, v6

    move-object v1, p1

    move-object v2, p2

    move v3, p3

    invoke-direct/range {v0 .. v5}, Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;-><init>(Ljava/lang/String;Ljava/lang/CharSequence;ILandroid/os/Bundle;Landroid/support/v4/media/session/PlaybackStateCompat$1;)V

    invoke-virtual {p0, v6}, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->addCustomAction(Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;

    move-result-object v0

    return-object v0
.end method

.method public build()Landroid/support/v4/media/session/PlaybackStateCompat;
    .registers 20

    move-object/from16 v0, p0

    new-instance v18, Landroid/support/v4/media/session/PlaybackStateCompat;

    iget v2, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mState:I

    iget-wide v3, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mPosition:J

    iget-wide v5, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mBufferedPosition:J

    iget v7, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mRate:F

    iget-wide v8, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActions:J

    iget-object v10, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mErrorMessage:Ljava/lang/CharSequence;

    iget-wide v11, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mUpdateTime:J

    iget-object v13, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mCustomActions:Ljava/util/List;

    iget-wide v14, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActiveItemId:J

    iget-object v1, v0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mExtras:Landroid/os/Bundle;

    const/16 v17, 0x0

    move-object/from16 v16, v1

    move-object/from16 v1, v18

    invoke-direct/range {v1 .. v17}, Landroid/support/v4/media/session/PlaybackStateCompat;-><init>(IJJFJLjava/lang/CharSequence;JLjava/util/List;JLandroid/os/Bundle;Landroid/support/v4/media/session/PlaybackStateCompat$1;)V

    return-object v18
.end method

.method public setActions(J)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 3

    iput-wide p1, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActions:J

    return-object p0
.end method

.method public setActiveQueueItemId(J)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 3

    iput-wide p1, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mActiveItemId:J

    return-object p0
.end method

.method public setBufferedPosition(J)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 3

    iput-wide p1, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mBufferedPosition:J

    return-object p0
.end method

.method public setErrorMessage(Ljava/lang/CharSequence;)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 2

    iput-object p1, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mErrorMessage:Ljava/lang/CharSequence;

    return-object p0
.end method

.method public setExtras(Landroid/os/Bundle;)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 2

    iput-object p1, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mExtras:Landroid/os/Bundle;

    return-object p0
.end method

.method public setState(IJF)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 12

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v5

    move-object v0, p0

    move v1, p1

    move-wide v2, p2

    move v4, p4

    invoke-virtual/range {v0 .. v6}, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->setState(IJFJ)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;

    move-result-object v0

    return-object v0
.end method

.method public setState(IJFJ)Landroid/support/v4/media/session/PlaybackStateCompat$Builder;
    .registers 7

    iput p1, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mState:I

    iput-wide p2, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mPosition:J

    iput-wide p5, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mUpdateTime:J

    iput p4, p0, Landroid/support/v4/media/session/PlaybackStateCompat$Builder;->mRate:F

    return-object p0
.end method
