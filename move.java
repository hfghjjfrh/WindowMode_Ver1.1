//インポート
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

class move{
    public static void main(String[]args){
       SwingUtilities.invokeLater(() -> {

            //宣言
            int pos[] = {0,0};
            int flag[] = {0};
            int flagr[] = {0};
            int[] s = {20};
            int[] x = {10,110,210,310,10,110};
            int[] y = {100,100,100,100,150,150};
            int h;
            
            //フレーム設定
            JFrame frame = new JFrame("ウィンドウ移動アプリ Ver1.1");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(450,250);
            frame.setLocation(pos[0],pos[1]);
            frame.setLayout(null);
            frame.setVisible(true);
            frame.setResizable(false);
            JLabel title = new JLabel("ボタンを押してください");
            JLabel posi = new JLabel("現在位置:");

            //タイトル＆現在位置ラベル作成
            title.setFont(new Font("", Font.BOLD, 15));
            title.setBounds(10,30,200,30);
            posi.setBounds(10,50,200,30);

            //ボタン作成
            JButton mover = new JButton("右に移動");
            JButton movel = new JButton("左に移動");
            JButton moveu = new JButton("上に移動");
            JButton moved = new JButton("下に移動");
            JButton speed = new JButton("早く移動");
            JButton runaway = new JButton("ランダム");
            //ボタン配置
            JButton[] buttons ={mover,movel,moveu,moved,speed,runaway};
            for (JButton b : buttons) {
                frame.add(b);
                b.setBackground(Color.LIGHT_GRAY);
                for(h=0; h<6; h++){
                    buttons[h].setBounds(x[h],y[h],100,30);
                }
            }

            //タイトル＆現在位置ラベル配置
            frame.add(title);
            frame.add(posi);

            //右ボタンが押された時の動作
            mover.addActionListener(e -> new Thread(() -> {
               if(pos[0]<1450){
                for (int i = 0; i < 20; i++) {
                    if(pos[0]>1450){
                          break;
                    }
                    pos[0] += 5;
                    frame.setLocation(pos[0], pos[1]);
                    title.setText("右に移動");
                    frame.setTitle("右に移動");
                    mover.setBackground(Color.GREEN);
                    posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
                    try { Thread.sleep(s[0]); } catch (InterruptedException ex) {}
                    
                }
                
                frame.setTitle("移動完了！");
                title.setText("移動完了！");
               }
               else{
                    frame.setTitle("移動に失敗しました");
                    title.setText("移動に失敗しました");
                 }
                for (JButton b : buttons) {
                     b.setBackground(Color.LIGHT_GRAY);
                } 
            }).start());

            //左ボタンが押された時の動作
            movel.addActionListener(e -> new Thread(() -> {
                 if(pos[0]>0){
                    for (int i = 20; i > 0; i--) {
                        if(pos[0]<0){
                           break;
                        }
                         pos[0] += -5;
                         frame.setLocation(pos[0], pos[1]);
                         title.setText("左に移動");
                         frame.setTitle("左に移動");
                         movel.setBackground(Color.GREEN);
                         posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
                         try { Thread.sleep(s[0]); } catch (InterruptedException ex) {}
                    }
                   
                    frame.setTitle("移動完了！");
                    title.setText("移動完了！");
                 }
                 else{
                   
                    frame.setTitle("移動に失敗しました");
                    title.setText("移動に失敗しました");
                 }
                for (JButton b : buttons) {
                     b.setBackground(Color.LIGHT_GRAY);
                } 
            }).start());

            //上ボタンが押された時の動作
            moveu.addActionListener(e -> new Thread(() -> {
                if(pos[1]>0){
                    for (int i = 20; i > 0; i--) {
                        if(pos[1]<0){
                           break;
                        }
                        pos[1] += -5;
                        frame.setLocation(pos[0], pos[1]);
                        title.setText("上に移動");
                        frame.setTitle("上に移動");
                        moveu.setBackground(Color.GREEN);
                        posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
                        try { Thread.sleep(s[0]); } catch (InterruptedException ex) {}
                        
                    }
                    frame.setTitle("移動完了！");
                    title.setText("移動完了！");
                }
                else{
                    frame.setTitle("移動に失敗しました");
                    title.setText("移動に失敗しました");
                }
                for (JButton b : buttons) {
                     b.setBackground(Color.LIGHT_GRAY);
                } 
            }).start());

            //下ボタンが押された時の動作
            moved.addActionListener(e -> new Thread(() -> {
            if(pos[1]<800){
                for (int i = 0; i < 20; i++) {
                    if(pos[1]>800){
                        break;
                    }
                    pos[1] += 5;
                    frame.setLocation(pos[0], pos[1]);
                    title.setText("下に移動");
                    frame.setTitle("下に移動");
                    moved.setBackground(Color.GREEN);
                    posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
                            try { Thread.sleep(s[0]); } catch (InterruptedException ex) {}
                    
                }
                frame.setTitle("移動完了！");
                title.setText("移動完了！");
            }
            else{
                frame.setTitle("移動に失敗しました");
                title.setText("移動に失敗しました");
            }
            for (JButton b : buttons) {
                     b.setBackground(Color.LIGHT_GRAY);
                } 
                
            }).start());

            //スピードボタンが押された時の動作
            speed.addActionListener(e ->{
               if(flag[0] == 0){ 
                     flag[0] = 1;
                     s[0] = 10;
                     frame.setTitle("速度が速くなりました！");
                     speed.setText("遅く移動");
                     posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
                     
               } else { 
                     flag[0] = 0;
                     s[0] = 20;
                     frame.setTitle("速度が遅くなりました！");
                     speed.setText("早く移動");
               }
            });

            //ランダムボタンが押された時の動作
            runaway.addActionListener(e -> new Thread(() -> {
               int rx = ThreadLocalRandom.current().nextInt(0, 1400);
               int ry = ThreadLocalRandom.current().nextInt(0, 800);

               frame.setLocation(rx, ry); // ウィンドウを移動
            }).start());

            //フレームが移動したときの動作
            frame.addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentMoved(java.awt.event.ComponentEvent e){
                    pos[0] = frame.getX();
                    pos[1] = frame.getY();
                    posi.setText(String.format("現在位置:(%d,%d)",pos[0],pos[1]));
                }
            });

       });
    }
}