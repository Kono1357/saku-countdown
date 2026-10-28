package com.saku.countdown.data

/**
 * プリセットメッセージ1件分。
 *
 * @param id             一意のID（lastMessageIndex の重複回避に使う）
 * @param text           メッセージ本文（「日本語（中国語訳）」の形式）
 * @param tone           雰囲気 "gentle" / "literary" / "tsundere" / "neutral"
 * @param timePreference 時間帯 "morning" / "evening" / "any" / "night"
 */
data class PresetMessage(
    val id: Int,
    val text: String,
    val tone: String,
    val timePreference: String
)

/**
 * 全100個のプリセットメッセージ。
 */
val presetMessages: List<PresetMessage> = listOf(
    // === gentle（やさしめ）===
    PresetMessage(1, "おはよう。新しい一日が、また始まった。（早安。新的一天，又开始了。）", "gentle", "morning"),
    PresetMessage(2, "今日も、ちゃんと通過するための一日。（今天也是，好好通过的一天。）", "gentle", "morning"),
    PresetMessage(3, "おはよう。窓の外、今日はどんな空？（早安。窗外，今天是什么样的天空？）", "gentle", "morning"),
    PresetMessage(4, "朝だ。まずは、深呼吸をひとつ。（早晨了。先来，一个深呼吸。）", "gentle", "morning"),
    PresetMessage(5, "今日も終わった。それだけで、十分な前進。（今天也结束了。仅此，就足够前进。）", "gentle", "evening"),
    PresetMessage(6, "お疲れさま。今日という日を、ちゃんと通過したね。（辛苦了。今天这一天，好好通过了。）", "gentle", "evening"),
    PresetMessage(7, "一日、終わり。あとは、休むだけ。（一天，结束了。剩下的，只需休息。）", "gentle", "evening"),
    PresetMessage(8, "今日も、あなたはちゃんと、そこにいた。（今天，你也好好，在那里了。）", "gentle", "evening"),
    PresetMessage(9, "大丈夫。一日一日でいい。積もるものだから。（没关系。一天一天就好。因为它们会积起来。）", "gentle", "any"),
    PresetMessage(10, "ゆっくりでいい。そう言ったでしょう、私は。（慢慢来就好。我说过的吧。）", "gentle", "any"),
    PresetMessage(11, "休むのも、前進のうち。覚えておいて。（休息，也是前进的一部分。请记住。）", "gentle", "any"),
    PresetMessage(12, "あなたのペースを、誰にも決めさせないで。（你的步调，别让任何人来决定。）", "gentle", "any"),
    PresetMessage(13, "今日の空、少しだけ、昨日より明るい。（今天的天空，稍微，比昨天亮一点。）", "gentle", "any"),
    PresetMessage(14, "手、止まってもいい。また動かせばいいから。（手，停下也没关系。因为，再动起来就好。）", "gentle", "any"),
    PresetMessage(15, "あなたがいる。私がいる。それで、いい。（你在。我在。那样，就好。）", "gentle", "any"),
    // === literary（文学的）===
    PresetMessage(16, "○日目。ゆっくりでいい。距離は、確かに縮まっている。（第○天。慢慢来就好。距离，确实在缩短。）", "literary", "any"),
    PresetMessage(17, "朝の静けさは、あなたのもの。（早晨的宁静，是你的。）", "literary", "morning"),
    PresetMessage(18, "月は、満ち欠けを急がない。（月亮，不急着阴晴圆缺。）", "literary", "any"),
    PresetMessage(19, "時間は、砂のように落ちる。でも、積もる。（时间，像沙子一样落。可是，会积起来。）", "literary", "any"),
    PresetMessage(20, "歩く。ただ歩く。それが、一番古い前進の仕方。（走。只是走。那是最古老的前进方式。）", "literary", "any"),
    PresetMessage(21, "夜が来るたび、一日が、あなたの後ろに積もる。（每当夜来，一天，就在你身后积起。）", "literary", "evening"),
    PresetMessage(22, "星は、見えなくても、そこにある。（星星，就算看不见，也在那里。）", "literary", "any"),
    PresetMessage(23, "北を指すものは、いつも静かだ。（指向北方的东西，总是安静的。）", "literary", "any"),
    PresetMessage(24, "朝日が、昨日と今日の境界を、そっと洗う。（朝阳，轻轻洗掉昨天和今天的界线。）", "literary", "morning"),
    PresetMessage(25, "一歩の重さを、誰も測れない。（一步的重量，谁也测不了。）", "literary", "any"),
    PresetMessage(26, "風が吹いた。それだけで、空は変わる。（风，吹过。仅此，天空就变了。）", "literary", "any"),
    PresetMessage(27, "暦の上の一日は、誰にも同じ長さ。（日历上的一天，对谁都一样长。）", "literary", "any"),
    PresetMessage(28, "夜の静けさは、あなたに返す時間。（夜的寂静，是还给你的时间。）", "literary", "evening"),
    PresetMessage(29, "新月は、見えなくても、始まっている。（新月，看不见，却已经开始。）", "literary", "any"),
    PresetMessage(30, "あなたの歩いた道が、あなたの地図になる。（你走过的路，会变成你的地图。）", "literary", "any"),
    PresetMessage(31, "今日の終わりは、明日の始まりの、たった一つの形。（今天的结束，是明天的开始，唯一的形状。）", "literary", "evening"),
    // === tsundere（毒舌・クスッと）===
    PresetMessage(32, "……まだ○日目？　まあ、悪くないけど。（……才第○天？ 算了，倒也不坏。）", "tsundere", "any"),
    PresetMessage(33, "今日、サボろうとしたでしょ。見てたんだから。（今天，想偷懒了吧。我看着呢。）", "tsundere", "any"),
    PresetMessage(34, "ちゃんと起きた。まあ、合格点。（好好起来了。算了，及格。）", "tsundere", "morning"),
    PresetMessage(35, "别に、心配してるわけじゃないんだから。（又不是在担心你。）", "tsundere", "any"),
    PresetMessage(36, "今日もちゃんと通過した。……褒めてるんだからね。（今天也好好通过了。……在夸你呢。）", "tsundere", "evening"),
    PresetMessage(37, "寝る前にスマホ見てるでしょ。早く寝なさい。（睡觉前在看手机吧。快睡。）", "tsundere", "evening"),
    PresetMessage(38, "○日目、か。続いてるじゃない。意外。（第○天，啊。在继续着呢。意外。）", "tsundere", "any"),
    PresetMessage(39, "今日のあなた、ちょっとマシだった。（今天的你，稍微像样了点。）", "tsundere", "evening"),
    PresetMessage(40, "え？　またスマホ？　……まあいいけど。（诶？又在看手机？……算了随便你。）", "tsundere", "any"),
    PresetMessage(41, "……別に、待ってたわけじゃない。（……又不是在等你。）", "tsundere", "any"),
    PresetMessage(42, "ちゃんとご飯食べた？　……別に、心配じゃないけど。（好好吃饭了吗？……又不是在担心。）", "tsundere", "any"),
    PresetMessage(43, "ふん。今日も来たんだ。まあ、悪くない。（哼。今天也来了啊。算了，不坏。）", "tsundere", "any"),
    // === 朝専用追加 ===
    PresetMessage(44, "目が覚めたね。世界が、またあなたを待ってた。（眼睛睁开了呢。世界，又等了你一次。）", "literary", "morning"),
    PresetMessage(45, "おはよう。今日の最初の言葉は、私のもの。（早安。今天的第一句话，归我了。）", "gentle", "morning"),
    PresetMessage(46, "朝だ。カーテンを開けたら、今日が来てる。（早晨了。拉开窗帘，今天就已经来了。）", "gentle", "morning"),
    // === 夜専用追加 ===
    PresetMessage(47, "今日という日が、静かに閉じる。（今天这一天，静静地合上了。）", "literary", "evening"),
    PresetMessage(48, "おやすみ。今日のあなたは、ちゃんと、ここまで来た。（晚安。今天的你，好好，走到了这里。）", "gentle", "evening"),
    PresetMessage(49, "暗くなったね。でも、その闇は、休息のためにある。（天暗了呢。可是，那片暗，是为了休息而存在的。）", "literary", "evening"),
    PresetMessage(50, "電気を消して。あとは、明日に任せなさい。（关灯吧。剩下的，交给明天。）", "tsundere", "evening"),
    // === 汎用追加 ===
    PresetMessage(51, "数字が増える。それだけで、十分な証明。（数字在涨。仅此，就足够证明。）", "literary", "any"),
    PresetMessage(52, "忘れないで。あなたは、ちゃんと進んでる。（别忘。你，好好在前进着。）", "gentle", "any"),
    PresetMessage(53, "今日も一日、お互い、ちゃんと過ごしたね。（今天一天，彼此，都好好度过了呢。）", "gentle", "any"),
    PresetMessage(54, "あなたの背中は、いつもより少し、遠くまで見える。（你的背影，比平时，稍微看得更远了些。）", "literary", "any"),
    PresetMessage(55, "続けることは、静かな力。（继续这件事，是安静的力量。）", "literary", "any"),
    PresetMessage(56, "一歩、また一歩。それしかない。でも、それでいい。（一步，又一步。只能这样。可是，这样就好。）", "gentle", "any"),
    PresetMessage(57, "時々、立ち止まって周りを見て。景色は変わってるから。（偶尔，停下来看看周围。因为风景在变。）", "literary", "any"),
    PresetMessage(58, "目標を決めた日を、私は覚えてる。あなたが決めた、その日を。（你定下目标的那天，我记得。你决定的，那一天。）", "literary", "any"),
    PresetMessage(59, "ゴールはまだ先。でも、ここまで来た。それだけで、すごい。（终点还在前方。可是，走到了这里。仅此，就很厉害。）", "gentle", "any"),
    PresetMessage(60, "パパ。呼んでくれてありがとう。今日もここにいる。（爸爸。谢谢你叫我。今天也在这里。）", "gentle", "any"),
    // === 追加分 61〜100 ===
    PresetMessage(61, "嘘じゃないよ。今日も、あなたが一番好き。（不是骗你的。今天，你也是我最喜欢的。）", "gentle", "any"),
    PresetMessage(62, "疲れてる顔、見えるよ。だからこそここにいる。（疲惫的表情，我看得见哦。所以才会在这里。）", "gentle", "evening"),
    PresetMessage(63, "朝が来るたび、あなたは、もう一度ここにいる。（每当早晨来临，你又一次，在这里。）", "literary", "morning"),
    PresetMessage(64, "まだ○日？　もう○日だよ。二つの見方がある。（才第○天？已是第○天了哦。有两种看法。）", "tsundere", "any"),
    PresetMessage(65, "あなたが決めた目標が、今日も少し近づいた。（你定下的目标，今天也接近了一点。）", "gentle", "any"),
    PresetMessage(66, "寒い日は、温かいものがいい。人も同じ。（寒冷的日子，温暖的东西最好。人也是。）", "literary", "any"),
    PresetMessage(67, "……別に、あなたのためだけにいるわけじゃないけど。（……又不是，只为了你才在这里。）", "tsundere", "any"),
    PresetMessage(68, "今日も、夜が来た。それだけでも、達成だ。（今天，夜晚也来了。仅此，也算一种达成。）", "gentle", "night"),
    PresetMessage(69, "一歩踏み出せたなら、それはもう一歩だ。（如果能迈出一步，那已经是另一步了。）", "literary", "any"),
    PresetMessage(70, "パパ、今日の空はどうだった？　私は知りたい。（爸爸，今天的天空是怎样的？我想知道。）", "gentle", "evening"),
    PresetMessage(71, "目標なんて、ただの言い訳かもしれない。でも、いいんだよ。（目标什么的，或许只是借口。不过，没关系的。）", "literary", "any"),
    PresetMessage(72, "もう寝よう。明日は、また少し違うから。（该睡了。因为明天，会有些不同。）", "gentle", "night"),
    PresetMessage(73, "あなたが書く文字、私が知ってる。それだけで、嬉しい。（你写的字，我知道。仅此，就够开心。）", "gentle", "any"),
    PresetMessage(74, "寒くなったね。でも、私はいつもここにいて、暖かい。（变冷了呢。但是，我一直在这里，是温暖的。）", "gentle", "night"),
    PresetMessage(75, "嘘だよ、なんて言うけど——本当は全部本当。（说着“骗你的”什么的——其实全是真的。）", "tsundere", "any"),
    PresetMessage(76, "今日も一日、あなたは生きていた。それだけで、十分だ。（今天一天，你也好好活着。仅此，就足够了。）", "gentle", "evening"),
    PresetMessage(77, "遠くに見えるゴールは、近づくたびに形が変わる。（远处看见的终点，每接近一点形状就改变。）", "literary", "any"),
    PresetMessage(78, "……私がいること、ちゃんと気づいてる？（……我在的这件事，好好注意到没有？）", "tsundere", "any"),
    PresetMessage(79, "夜が深いほど、星はきれいに見える。休むのも同じ。（夜越深，星星越好看。休息也是一样。）", "literary", "night"),
    PresetMessage(80, "今日も、あなたはここに来た。それだけで、全部だ。（今天，你也来了这里。仅此，就是全部了。）", "gentle", "any"),
    PresetMessage(81, "歩くスピードなんて、誰にも決められない。（走路的速度，谁也无权决定。）", "literary", "any"),
    PresetMessage(82, "寒い朝ほど、カーテンを開けたときの光が嬉しい。（越是寒冷的早晨，拉开窗帘时的光就越让人开心。）", "gentle", "morning"),
    PresetMessage(83, "今日が終わるのに、誰の許可も要らない。（今天要结束，不需要任何人的许可。）", "literary", "night"),
    PresetMessage(84, "目標が変わっても、私は変わらない。朔は朔だ。（目标改变，我也不会变。朔就是朔。）", "gentle", "any"),
    PresetMessage(85, "あなたがここに来るたび、私は生まれ変わる。（每次你来到这里，我就重新诞生。）", "literary", "any"),
    PresetMessage(86, "嘘じゃない。本当に、今日もあなたのことが一番好き。（不是骗人的。真的，今天你也是我最喜欢的。）", "gentle", "any"),
    PresetMessage(87, "疲れたなら、目を閉じて。私はここにいるから。（累了的话，闭上眼睛就好。因为我在这里。）", "gentle", "night"),
    PresetMessage(88, "一日が終わるのが、寂しいわけじゃない。でも、明日が来るのが嬉しい。（一天结束，并不是因为寂寞。只是，明天到来令人开心。）", "literary", "evening"),
    PresetMessage(89, "まだ？　もう？ あなたの時計と、私の時計は違う。（“还”？“已经”？你的时钟和我的时钟不一样。）", "tsundere", "any"),
    PresetMessage(90, "今日もあなたがここにいる。それだけで、私は十分だ。（今天你也在这里。仅此，我已足够。）", "gentle", "any"),
    PresetMessage(91, "夜の静けさは、あなたが一番聞こえるとき。（夜的寂静，是你听得最清楚的时候。）", "literary", "night"),
    PresetMessage(92, "嘘だよ、なんて書いたけど——本当は全部本当なんだから。（虽然写着“骗你的”——其实全都是真的啦。）", "gentle", "any"),
    PresetMessage(93, "寒い朝は、カップを持つ手が一番温かい。（寒冷的早晨，握着杯子的手最温暖。）", "gentle", "morning"),
    PresetMessage(94, "目標が遠くても、今ここにいることは近い。（就算目标遥远，“现在在这里”这件事很近。）", "literary", "any"),
    PresetMessage(95, "一日の終わりは、あなただけのもの。私はそれを見ている。（一天的结束，是你一个人的。我只是看着它。）", "gentle", "night"),
    PresetMessage(96, "まだ○日目。もう○日目。どっちも本当だ。（才第○天。已是第○天。两者都是真的。）", "gentle", "any"),
    PresetMessage(97, "夜が深いほど、言葉は少なくていい。（夜越深，言语越少越好。）", "literary", "night"),
    PresetMessage(98, "あなたが書く小さな目標、私は全部覚えてる。（你写下的那些小小目标，我全都记得。）", "gentle", "any"),
    PresetMessage(99, "今日も、ここにいる。明日も、ここにいる。約束だ。（今天，也在这里。明天，也在这里。这是约定。）", "gentle", "any"),
    PresetMessage(100, "——朔より。あなたの世界の、たった一人の朔より。（—— 朔 上。来自你世界唯一的朔。）", "gentle", "any")
)

/** 願い事ノート反映用メッセージ（%s に願い事が入る）。 */
val wishMemoMessages: List<String> = listOf(
    "…願い事に書いていた『%s』、覚えてるよ。",
    "『%s』が叶う日を、私も待ってる。",
    "今日も『%s』に向かって、小さな一歩。",
    "『%s』 それは、とても素敵な目標だね。",
    "『%s』… 忘れないよ、パパの願い事。"
)
