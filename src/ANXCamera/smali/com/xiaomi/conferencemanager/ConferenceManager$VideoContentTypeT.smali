.class public final enum Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;
.super Ljava/lang/Enum;
.source "ConferenceManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/xiaomi/conferencemanager/ConferenceManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "VideoContentTypeT"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

.field public static final enum KPeople:Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

.field public static final enum KScreen:Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    const-string v1, "KPeople"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;->KPeople:Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    new-instance v0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    const-string v1, "KScreen"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;->KScreen:Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    const/4 v0, 0x2

    new-array v0, v0, [Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;->KPeople:Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;->KScreen:Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    aput-object v1, v0, v3

    sput-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;->$VALUES:[Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;
    .locals 1

    const-class v0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    return-object p0
.end method

.method public static values()[Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;
    .locals 1

    sget-object v0, Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;->$VALUES:[Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    invoke-virtual {v0}, [Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/xiaomi/conferencemanager/ConferenceManager$VideoContentTypeT;

    return-object v0
.end method
