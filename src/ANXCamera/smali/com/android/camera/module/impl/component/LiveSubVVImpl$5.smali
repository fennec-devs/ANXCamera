.class Lcom/android/camera/module/impl/component/LiveSubVVImpl$5;
.super Ljava/lang/Object;
.source "LiveSubVVImpl.java"

# interfaces
.implements Lio/reactivex/Observer;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/module/impl/component/LiveSubVVImpl;->startCountDown(J)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lio/reactivex/Observer<",
        "Ljava/lang/Long;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/impl/component/LiveSubVVImpl;

.field final synthetic val$totalTime:J


# direct methods
.method constructor <init>(Lcom/android/camera/module/impl/component/LiveSubVVImpl;J)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/impl/component/LiveSubVVImpl$5;->this$0:Lcom/android/camera/module/impl/component/LiveSubVVImpl;

    iput-wide p2, p0, Lcom/android/camera/module/impl/component/LiveSubVVImpl$5;->val$totalTime:J

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onComplete()V
    .locals 2

    invoke-static {}, Lcom/android/camera/module/impl/component/LiveSubVVImpl;->access$600()Ljava/lang/String;

    move-result-object v0

    const-string v1, "onFinish"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onError(Ljava/lang/Throwable;)V
    .locals 0

    return-void
.end method

.method public onNext(Ljava/lang/Long;)V
    .locals 7

    iget-object v0, p0, Lcom/android/camera/module/impl/component/LiveSubVVImpl$5;->this$0:Lcom/android/camera/module/impl/component/LiveSubVVImpl;

    iget-wide v1, p0, Lcom/android/camera/module/impl/component/LiveSubVVImpl$5;->val$totalTime:J

    invoke-virtual {p1}, Ljava/lang/Long;->longValue()J

    move-result-wide v3

    const-wide/16 v5, 0x64

    mul-long/2addr v3, v5

    sub-long/2addr v1, v3

    invoke-static {v0, v1, v2}, Lcom/android/camera/module/impl/component/LiveSubVVImpl;->access$500(Lcom/android/camera/module/impl/component/LiveSubVVImpl;J)V

    return-void
.end method

.method public bridge synthetic onNext(Ljava/lang/Object;)V
    .locals 0

    check-cast p1, Ljava/lang/Long;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/impl/component/LiveSubVVImpl$5;->onNext(Ljava/lang/Long;)V

    return-void
.end method

.method public onSubscribe(Lio/reactivex/disposables/Disposable;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/impl/component/LiveSubVVImpl$5;->this$0:Lcom/android/camera/module/impl/component/LiveSubVVImpl;

    invoke-static {v0, p1}, Lcom/android/camera/module/impl/component/LiveSubVVImpl;->access$402(Lcom/android/camera/module/impl/component/LiveSubVVImpl;Lio/reactivex/disposables/Disposable;)Lio/reactivex/disposables/Disposable;

    return-void
.end method
