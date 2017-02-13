# 5-2 JavaPathFinder

JPFを使えるようにしないといけないみたいだけど，インストールしてビルドするためにgradle jarを実行

入ってない人はまずここから必要みたいなので，

```
brew install gradle
```

でインストール．

jpfがうまく動かないときは，

```
/net/home/teacher/kono/jpfcore-kono201702.tgz
```

から河野先生が準備してくれたjpfを使おう．

tgzを解答してホーム直下もしくは作業ディレクトリにでも置いておこう．

そのあとの実行方法についてだが，

まずJPF_HOMEを変更する必要があるので，

```
vim jpf-core-new/bin/jpf
```

を変更する．中身の6行目ぐらいに書いてあるはずなので，jpfcoreの置いてあるところに変更する．ホームディレクトリなら，

```
JPF_HOME = /Users/ホームディレクトリ/jpfcore(ry
```

ここを変更したらうまくいくはず．

```
<jpf実行場所> +classpath=build/classes/main/ 
<main下のフォルダをピリオド綴りでclassファイルまで>
```

例

```
~/jpf-core-new/bin/jpf +classpath=build/classes/main jp.ac.uryukyu.ie.interleave.Interleave
```
