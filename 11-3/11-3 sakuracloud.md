# 11-3 sakuracloud

まずはシス管にメールを送ろう．さくらサーバを使いたいと．
というわけで，先生のページを参考にメールを送る．長くて1週間程度でメールが返ってくるはず．

んでもって帰ってくるメールがクソなので，ここで説明するとkey登録をしなきゃならないみたい．

添付されている秘密鍵をダウンロードしてとりあえずデスクトップにでも置いてテキストエディタで開こう．
そのまま.ssh/下においても使えない鍵なので，エディタで中身をコピペしてあたらしくテキスト保存しよう．それを.ssh/下に置こう．

そしたら.ssh/configを変更しよう．

追加するのは以下

```
Host sakura
	HostName メールのHostIP
	IdentityFile ~/.ssh/新しくテキスト保存した秘密鍵
	User	ie-user
```

これを追加したら保存して終了．ターミナル上で```ssh IPv6アドレス```うまく編集できていればターミナルからさくらサーバへいけるはず．

あとはFileWriteをさくらサーバに送信して，学科サーバへのろぐのこせばおしまい．

