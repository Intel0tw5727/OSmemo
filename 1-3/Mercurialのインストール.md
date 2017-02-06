#1-3(ansible環境構築)
## Mercurialのインストール
```sh
brew install mercurial
```

でMercurialのインストール.

その後インストールしたFedoraの仮想環境でOS13.mdを見ながら仮想環境の環境構築をおこなう．

ローカル上で鍵を生成して，
> http://qiita.com/HamaTech/items/21bb9761f326c4d4aa65

(上記参照)

```
ssh-keygen -t rsa
```
作成した公開鍵は

```
rsync -auvz 作成した鍵.pub -e "ssh -p 2222" ユーザ名@localhost:
```
でサーバ上に転送する

以下はリンク通りに進めるが，fedora内のにある".ssh/"フォルダに対して，

```
chmod 700 .ssh/
```

で権限を変更する必要があるので注意．
これで公開鍵と秘密鍵の設定を終えたので，configの編集．ただしIdentityFileに"~/.ssh/サーバAの鍵"を設置しておかないとエラーが出るかも？mvで移動しておこう．

```
ssh 作成した鍵
```
これで<b>パスワードを求められずに</b>ログインできていれば正しくsshできています．

その後ansibleをインストール(brew install ansibleでだいたい行けるっしょ)

```
hg clone https://ie.u-ryukyu.ac.jp/hg/y12/index.cgi/home/hg/y12/e125721/ansible/  
```

でホームディレクトリにクローン．必要なら好きなフォルダに指定するといいさ．

ターミナル上で

```
grep yum *.yml
``` 

を行うことで.yml内にある"yum"を表示させるので，これに当てはまるファイルの"yum"をすべて'dnf'に変更する．

ansible/tasks 内のinstall-php.ymlとsetting-mariadb.ymlのファイルにsudo権限を追加する．
```
sudo: yes
```

+ コロンとの間にスペースを開けないとエラーを吐くので注意！)
+ ただし，1番上のnameの下に記述すること)

さらにsetting-mariadb.ymlのファイル内の
```
encoding: "utf8"
```
(変更前 "UTF-8")
を変更する．

ターミナル上で

```
grep yum ** /*.yml
``` 

を行うことで.yml内にある"yum"を表示させるので，これに当てはまるファイルの"yum"をすべて'dnf'に変更する．

以上の作業が終わったら，作成したユーザーにsudo権限を付与する必要があるので，
> https://genchan.net/server/5421

をみながら権限を追加すること(rootではできないので注意！)

以上の変更を必ず行ってから，ansibleフォルダにて

```
ansible-playbook -i hosts main.yml -K
```
を実行する．

うまく行ったら，サーバー上で

```
sudo systemctl restart httpd.service
curl -L http://localhost
```
これで日本語見えたらお疲れ様でした．
(課題はここまでだけどこの先必要．)

cloneしてきたフォルダのhgrc
あとでgoodnoteみて


