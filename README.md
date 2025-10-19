# ウィンドウ移動アプリ Ver1.1 解説

## 1. 概要

このプログラムは、Java Swing を使用して作られた「ウィンドウ移動アプリ」です。ボタン操作により、ウィンドウを上下左右に移動したり、速度を切り替えたり、ランダム移動させることができます。また、現在のウィンドウ座標もリアルタイムで表示されます。

---

## 2. インポートと準備

```java
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
```

* `Color` / `Font` → ボタンやラベルの色・フォント設定
* `ThreadLocalRandom` → ランダム座標生成
* `Swing` → GUI作成用

---

## 3. Swingスレッド

```java
SwingUtilities.invokeLater(() -> { ... });
```

* GUIの初期化をイベントディスパッチスレッド(EDT)上で行うことで、スレッドセーフかつUIが固まらない状態で処理可能

---

## 4. 宣言部

```java
int pos[] = {0,0};       // ウィンドウ座標
int flag[] = {0};        // スピードフラグ
int[] s = {20};          // 移動速度（Thread.sleepの間隔）
int[] x = {10,110,210,310,10,110}; // ボタンX座標
int[] y = {100,100,100,100,150,150}; // ボタンY座標
```

* `pos[]` → 現在のウィンドウ座標
* `flag[]` → 速度切替用
* `s[]` → 移動間隔。小さいほど速く動く
* `x[]` / `y[]` → ボタンの配置座標

---

## 5. JFrame設定

```java
JFrame frame = new JFrame("ウィンドウ移動アプリ Ver1.1");
frame.setSize(450,250);
frame.setLocation(pos[0],pos[1]);
frame.setLayout(null);
frame.setResizable(false);
frame.setVisible(true);
```

* `null` レイアウトで自由な座標配置
* 固定サイズ・初期位置 `(0,0)`

---

## 6. ラベル作成

```java
JLabel title = new JLabel("ボタンを押してください");
JLabel posi = new JLabel("現在位置:");
title.setFont(new Font("", Font.BOLD, 15));
title.setBounds(10,30,200,30);
posi.setBounds(10,50,200,30);
frame.add(title);
frame.add(posi);
```

* `title` → 動作メッセージ表示
* `posi` → 現在の座標表示

---

## 7. ボタン作成と配置

```java
JButton mover = new JButton("右に移動");
JButton movel = new JButton("左に移動");
JButton moveu = new JButton("上に移動");
JButton moved = new JButton("下に移動");
JButton speed = new JButton("早く移動");
JButton runaway = new JButton("ランダム");

JButton[] buttons ={mover,movel,moveu,moved,speed,runaway};
for (JButton b : buttons) {
    frame.add(b);
    b.setBackground(Color.LIGHT_GRAY);
    for(int h=0; h<6; h++){
        buttons[h].setBounds(x[h],y[h],100,30);
    }
}
```

* 配列で一括処理：追加・背景色・座標設定
* ボタンは右・左・上・下・速度・ランダムの6個

---

## 8. 各ボタンの動作

### 8-1. 右移動ボタン

```java
mover.addActionListener(e -> new Thread(() -> {
    if(pos[0]<1450){
        for (int i = 0; i < 20; i++) {
            pos[0] += 5;
            frame.setLocation(pos[0], pos[1]);
            title.setText("右に移動");
            mover.setBackground(Color.GREEN);
            posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
            Thread.sleep(s[0]);
        }
        frame.setTitle("移動完了！");
        title.setText("移動完了！");
    } else {
        frame.setTitle("移動に失敗しました");
        title.setText("移動に失敗しました");
    }
    for (JButton b : buttons) b.setBackground(Color.LIGHT_GRAY);
}).start());
```

* `Thread` で非同期処理 → UIが固まらない
* 20回に分けて5pxずつ右に移動 → 滑らかに見える
* 移動中ボタンを緑にし、終了後に元に戻す

### 8-2. 左・上・下ボタン

* 右移動と同様の構造
* `pos[0]` / `pos[1]` を適切に増減
* 上限・下限チェックで画面外に出ないよう制御

### 8-3. 速度ボタン

```java
speed.addActionListener(e ->{
   if(flag[0] == 0){
         flag[0] = 1;
         s[0] = 10; // 速度アップ
         frame.setTitle("速度が速くなりました！");
         speed.setText("遅く移動");
   } else {
         flag[0] = 0;
         s[0] = 20; // 速度ダウン
         frame.setTitle("速度が遅くなりました！");
         speed.setText("早く移動");
   }
});
```

* `s[0]` の値で Thread.sleep 間隔を調整 → 移動速度変更

### 8-4. ランダムボタン

```java
runaway.addActionListener(e -> new Thread(() -> {
   int rx = ThreadLocalRandom.current().nextInt(0, 1400);
   int ry = ThreadLocalRandom.current().nextInt(0, 800);
   frame.setLocation(rx, ry);
}).start());
```

* ランダムに画面内座標を生成してジャンプ移動

### 8-5. ウィンドウ移動検知

```java
frame.addComponentListener(new java.awt.event.ComponentAdapter() {
    @Override
    public void componentMoved(java.awt.event.ComponentEvent e){
        pos[0] = frame.getX();
        pos[1] = frame.getY();
        posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
    }
});
```

* ウィンドウをマウスで動かした場合も座標表示を更新可能

---

## 9. まとめ

* GUIは `SwingUtilities.invokeLater` で安全に初期化
* ボタン操作は `ActionListener + Thread` で非同期処理
* ウィンドウ移動や速度切替は変数・配列で管理
* `ThreadLocalRandom` でランダム座標を生成
* ラベルでリアルタイム座標表示が可能
* 背景色やフォントで操作状態を視覚的に表示
