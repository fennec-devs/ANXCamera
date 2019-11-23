.class public Lcom/android/camera/module/loader/ActionUpdateLensDirtyDetect;
.super Ljava/lang/Object;
.source "ActionUpdateLensDirtyDetect.java"

# interfaces
.implements Lio/reactivex/functions/Action;


# instance fields
.field private mIsOnCreate:Z

.field private mModuleWeakReference:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/camera/module/BaseModule;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/android/camera/module/BaseModule;Z)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/camera/module/loader/ActionUpdateLensDirtyDetect;->mModuleWeakReference:Ljava/lang/ref/WeakReference;

    iput-boolean p2, p0, Lcom/android/camera/module/loader/ActionUpdateLensDirtyDetect;->mIsOnCreate:Z

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/module/loader/ActionUpdateLensDirtyDetect;->mModuleWeakReference:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/module/BaseModule;

    if-eqz v0, :cond_2

    iget-boolean v1, p0, Lcom/android/camera/module/loader/ActionUpdateLensDirtyDetect;->mIsOnCreate:Z

    const/4 v2, 0x0

    if-eqz v1, :cond_0

    invoke-static {v2}, Lcom/android/camera/CameraSettings;->setLensDirtyDetectEnable(Z)V

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->addLensDirtyDetectedTimes()V

    :goto_0
    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getModuleIndex()I

    move-result v1

    const/16 v3, 0xa3

    const/4 v4, 0x1

    if-ne v1, v3, :cond_1

    new-array v1, v4, [I

    const/16 v3, 0x3d

    aput v3, v1, v2

    invoke-virtual {v0, v1}, Lcom/android/camera/module/BaseModule;->updatePreferenceTrampoline([I)V

    :cond_1
    invoke-virtual {v0, v4}, Lcom/android/camera/module/BaseModule;->updateLensDirtyDetect(Z)V

    :cond_2
    return-void
.end method
