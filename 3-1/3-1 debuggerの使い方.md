# 3-1 debuggerの使い方
debaggerをつかう準備
-
writeを行うプログラムをkono先生のページからダウンロードする(これで作成済み？)

lldbを使ってみる
-
まずはダウンロードしたwrite.cファイルに対してコンパイルしよう．ただしlldbで使用するため，コンパイルオプションに'-g'をつけ忘れないように注意．

```
gcc -o write -g write.c
```

lldbでの実行結果はlogとして残さないといけないので，コマンド毎に```>>log```って形で外に結果を吐き出すとベストかも．

コンパイルが終了したらターミナル上で

```
lldb write 100 200
```
を実行してみよう．

```
(lldb) target create "write"
Current executable set to 'write' (x86_64).
(lldb) settings set -- target.run-args  "100" "200"
```
って感じで出力されればOK．

引数に100と200を入れているのはlldbにてdebugを実行するためなので，やんなきゃいけない．

これで(1)は完了．

次に(2)をやるのだがそこには

```
(lldb) br set -f write.c -l 27
```
と実行する．最後の数字は行数．ダウンロード後特にいじってなければここになるはず．

```
Breakpoint 1: where = write`f + 16 at write.c:29, address = 0x0000000100000f30
```

Breakpointが設定されていればOK．
runをした際に，Process 97127 stoppedってなってれば正しくbreakpointが設定されているはず．

次に(3)だが，先生のところはframe upだがlldbでは
```
frame variable
```
で変数内部の確認ができるからコマンドは注意ね．

次に(4)はwatchpointを指定しなければ行けない．

> http://qiita.com/theefool/items/8b985ce71dcdccf26abc#breakpoint%E3%81%AE%E8%A8%AD%E5%AE%9A

このサイトを見れば一応分かる感じ．

```
watchpoint set variable m
```
で監視する変数を設定する．

```
watchpoint modify -c 'm==27' 1
```

でm==学籍番号の際にstopするように設定．

```
watchpoint command add -s python 1
```
を実行することでpythonを使って正常にストップしたかをprint使って確認することができる．

んで```watchpoint list```でこれまでの設定が正しくできているかどうかが確認できる．

あとは```c```で実行．testがものすごくどばあっとでるので```>>log```の使い方に注意しよう．

正しくbreakできていればpythonで設定したメッセージが出てくるはず．

(5)はwriteする直前でbreakするようにすれば良いから，単純に

```
br set -n write
br set -f write.c -l 30
```
どちらでもwrite直前でbreakするように設定することができる．

(6)はwrite直前でstopさせてその後に```stepi```コマンドで1命令ずつ吐き出させればよい？

レジスタの値については```register read```を使えば，その時点でのregisterの値を一気に羅列してくれるのでそれをlogに残してあげればよいかと．

(7)はbreakpointを解消するために，
```
b list
```
でbreakpointの数を把握し，これまで設定してきたbreakpointを

```
br delete 番号
```
で削除してあげることで，breakpointをすべて解除することができる．あとは

```
error: Couldn't find thread plan to implement step type.
```
がでるまでひたすら'finish'コマンドを実行していればプログラムは終了してくれるはず．

ここまでをlogとして残そう．gdbの場合も同様．

gdbの場合
---
> http://qiita.com/takahashim/items/204ffa698afe09bd4e28
> http://qiita.com/melos/items/624f5fdc1d05c83a6e5e

この2つをみてgdbを使えるようにすること．taskkillする場合には```sudo pkill```コマンド使っても良いかも．

というわけでまずは実行

```
gdb write
```
で実行．引数指定はコマンドラインではできないのでgdb起動後に行う．

```
set args 100 200
```

これで引数を設定できる．
ブレイクポイントの設定は

```
break write.c:27
```

でうまくいく．
変数の中身の確認は

```
p 変数名
```
にて確認することができる．

watchpointの指定は

```
watch m
```
にて可能．
その後変数に対してbreakポイントを設定しなければいけないので以下のサイトを参考にした．
>http://uguisu.skr.jp/Windows/gdb.html

そうすると以下のコマンドで実行できる．
```

