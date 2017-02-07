#11-2 WriteSpeed measure on docker/KVM

計測の際にはそれぞれdalmore上にFileWriteを送ろう．KVMに関しては，javaを動かす環境が整っていない可能性があるので，

```
sudo dnf install java
```

をして実行環境を整えよう．

そしたらファイルを送るのだけどそのときに注意しないといけないのが，送るのはFileWrite.classではなくSNAPSHOT.jarの方であることに注意．

jarファイルについてはたぶんbuild/libs/内に入っていると思われ．

KVMの場合にはまず，

```
rsync -auzP SNAPSHOT.jarの場所 学科サーバのVMのkey
```

でFileWriteを送信．11-1で学科サーバのVMには鍵を通した状態で作成しているはずなので送信できるはず．でもネットワークセキュリティの事件以来グローバルアドレスが使えなくなってしまっている可能性がある．

その場合には，新しくdefineし直そう．
VMのファイルであるqcow2については各々11-1で作成してあると思われるので，

```
cd /mnt/whisky/os/students/eXX/eXXXXXX/
cp 01.qcow2 02.qcow2
```
でOSを複製しなおそう．

その後はもういちどdefine．

```
ie-virsh define 02
ie-virsh dumpxml 02 | grep "mac"
```
これでmacアドレスを確認することができるのでAkatsukiにIPアドレスを申請しに行きましょう．

```
<type arch='x86_64' machine='pc-i440fx-rhel7.0.0'>hvm</type>
      <mac address='XX:XX:XX:XX:XX:XX'/>
```

>> https://ie.u-ryukyu.ac.jp/internal/akatsuki/sign_in

流石に場所はわかると思うけど一応．

ここでNewIPから作っていきましょう．
作成ができたら，keyを変更しないといけないので，

```
vim ~/.ssh/config
```

から編集でそれぞれがVMのfedoraで作成したkeyを変更する．

```
HostAddress XXX.XXX.XXX.XXX
```

ここを新しく作成したIPアドレスで書き換えよう．
書き換えが正しくできたら，

```
rsync -auzP SNAPSHOT.jarの場所 学科サーバのVMのkey
```

もう一度送信してみよう．うまく行けばvm上にsnapshotが送信されたと思われる．

そしたらあとは実行して計測しよう．

次はdocker．dockerはそんなに難しくなくて，まずはyomitanのホームディレクトリにsnapshotを送ろう．rsyncでもscpでもなんでも．

そしたらあとはkono先生の課題ページでも見ながらcpして実行して計測．

あとは考察しよう！
