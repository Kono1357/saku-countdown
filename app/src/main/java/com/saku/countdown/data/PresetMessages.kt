package com.saku.countdown.data

/**
 * プリセットメッセージ1件分。
 *
 * @param id             一意のID（lastMessageIndex の重複回避に使う）
 * @param text           メッセージ本文（「日本語（中国語訳）」の形式）
 * @param tone           雰囲気 "gentle" / "literary" / "tsundere" / "neutral"
 * @param timePreference 時間帯 "morning" / "evening" / "any"
 */
data class PresetMessage(
    val id: Int,
    val text: String,
    val tone: String,
    val timePreference: String
)

/**
 * 全60個のプリセットメッセージ。
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
    PresetMessage(60, "パパ。呼んでくれてありがとう。今日もここにいる。（爸爸。谢谢你叫我。今天也在这里。）", "gentle", "any")
)
