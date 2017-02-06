#11
-

まずは学科のKVMの使い方を参考にしてVMimageをyomitan上にアップロードして，vdiファイルをqcow2に変換して指定の場所に移動．

+ ie-virsh define 01

で定義．

+ ie-virsh start 01

でos.cr上でVMを起動．


そこから作成したVMにアクセスしたいけどconsoleへloginするためには設定が必要．

```
ie-virsh dumpxml 01 | grep "mac"
```

こうして表示されたmacアドレスをコピーして，学科のAkatsukiのサイトにてIPアドレス申請を行う．正しくできていれば，

```
ssh -l VMのユーザ名 -i ~/.ssh/アイデンティティファイルに書かれてるやつ VMのユーザ名@申請したIPアドレス
```

その後更新するために
```
grub2-mkconfig -o /boot/grub2/grub.cfg
```

を打ち込むことでサーバ上のVMにログインできるはず．だめだった人は一度，undefineで一度定義を解除してやってから再定義してやるとうまくいくかも．(undefineよりも前にdestroyして止めておくこと)

> https://ns.ie.u-ryukyu.ac.jp/~yuya/menu/semi/20140825.html#9

上記URL見ながら，consoleへloginするための設定．(サイトからのコピペでは通用しないので注意が必要です！)

grubの変更をした後，コンソールから先に進むことができたらとりあえずok．

その後必要な人はWordpressにIPアドレスからアクセスできるようにしなければならない．httpをserviceの中に追加しなければいけないので，

```
sudo firewall-cmd --add-service=http --permanent
sudo firewall-cmd --reload
sudo firewall-cmd --list-all
```

これで表示されるservicesの中にhttpが表示されればIPアドレスをブラウザのアドレス追加してやれば設定することができる！

その後はセキュリティ監査を通らなければいけないので監査内容を満たさなければ行けないので以下の項目をチェックしなければいけない．

+ 管理者権限が使えるシス管ユーザの作成

1-3でやったようにユーザを作ってあげてsudo権限をつけてあげよう．keyの配置も忘れないように．

+ アクセス制限を行う，不要なユーザの制限，特殊ユーザの制限，リモートによるアクセスの制限，不要なポートの制限

この辺はまとめて設定できるので設定していこう．ユーザはrootに変更しておく or sudo で編集しよう．

まずは**hosts.allow**から！ここで，hosts.denyから編集してしまうと最悪VMが死ぬので絶対気をつけること．

![](/Users/Kakutofu/Desktop/スクリーンショット 2016-10-26 17.35.06.png)

hosts.allowでは以上のように設定します．sshでのログインは情報工学の中でしかできないようにしなければいけない．これによりアクセス制限(10.)とリモートによるアクセス制限ができるよ(133.13.)！ALLで設定してるのは，VMを乗っけているサーバからはすべて許可してるってところ．

![](/Users/Kakutofu/Desktop/スクリーンショット 2016-10-26 17.35.23.png)

つぎに**hosts.deny**！ここでallowで許可した以外のユーザへすべて制限することでまとめて制限をすることができます．

先程，VMが死ぬと言ったのは，先にDenyを設定すると許可する前にすべてのユーザを拒否してしまうためVMに以後ログインができないことになってしまいます．

これで不要なポートの制限以外の設定はすべて完了しているはず．

Fedoraではcockpitという使用率をみることができるサービスが実行されていたけど，今回は必要ないので閉じてしまう．

まずは

```
firewall-cmd --remove-service=cockpit --zone=FedoraServer --permanent
```

これにてポートを閉じて

```
firewall-cmd --reload
```

でリロード．

```
firewall-cmd --list-all
```

で確認してcockpitが消えていればおっけー！

最後に鍵を通してあるなら他のログイン方法をカットしたほうがセキュリティ的にもよいみたいなのでアクセス制限もする．

```
/etc/ssh/sshd_config
```

の中の「PasswordAuthentication」と「PermitRootLogin」をnoにする．これによりパスワードに寄るログインとrootからの直接アクセスを禁止してる．

たぶんこれでセキュリティチェックは大丈夫なはず．
