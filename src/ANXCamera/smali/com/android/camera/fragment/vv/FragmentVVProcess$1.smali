.class Lcom/android/camera/fragment/vv/FragmentVVProcess$1;
.super Ljava/lang/Object;
.source "FragmentVVProcess.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/vv/FragmentVVProcess;->showReverseConfirmDialog()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/vv/FragmentVVProcess;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/vv/FragmentVVProcess;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVProcess$1;->this$0:Lcom/android/camera/fragment/vv/FragmentVVProcess;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 0

    const-string/jumbo p1, "vv\u5f55\u5236\u56de\u9000\u786e\u8ba4"

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtil;->trackLiveClick(Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVProcess$1;->this$0:Lcom/android/camera/fragment/vv/FragmentVVProcess;

    invoke-virtual {p1}, Lcom/android/camera/fragment/vv/FragmentVVProcess;->getContext()Landroid/content/Context;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ActivityBase;

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p1

    check-cast p1, Lcom/android/camera/module/LiveModuleSubVV;

    invoke-virtual {p1}, Lcom/android/camera/module/LiveModuleSubVV;->doReverse()V

    iget-object p1, p0, Lcom/android/camera/fragment/vv/FragmentVVProcess$1;->this$0:Lcom/android/camera/fragment/vv/FragmentVVProcess;

    invoke-static {p1}, Lcom/android/camera/fragment/vv/FragmentVVProcess;->access$000(Lcom/android/camera/fragment/vv/FragmentVVProcess;)Z

    return-void
.end method